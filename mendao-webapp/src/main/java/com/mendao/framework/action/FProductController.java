package com.mendao.framework.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.entity.util.FProductUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

/**
 * 
 * @ClassName: ProductController 
 * @Description: TODO 分销产品controller
 * @author TianMeifeng
 * @date 2016年5月8日 上午1:23:55 
 *
 */
@Controller
@RequestMapping("/fproduct")
@SessionAttributes(types = UserUtil.class)
public class FProductController extends BaseController {
	
	@Autowired
	ProductService productService;
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	ProductPicService productPicService;
	
	@Autowired
	DFUserRelationService dFUserRelationService;
	
	/**
	 * 
	 * @Title: query 
	 * @Description: 产品列表
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("selectedByDaili");
		String onSale = request.getParameter("selectedByOnSale");
		String deleteFalg = request.getParameter("selectedDeleteFalg");
		if(null != userId && "" != userId){
			params.put("createUserId.id", Long.parseLong(userId));
			model.addAttribute("userId", Long.parseLong(userId));
		}
		if(null != onSale && "" != onSale){
			params.put("onSale", Integer.parseInt(onSale));
			model.addAttribute("onSale", Integer.parseInt(onSale));
		}
		if(null != deleteFalg && "" != deleteFalg){
			params.put("deleteFlag", Integer.parseInt(deleteFalg));
			model.addAttribute("deleteFlag", Integer.parseInt(deleteFalg));
		}else{
			params.put("deleteFlag", 0);
			model.addAttribute("deleteFlag", 0);
		}
		params.put("modifyUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("createUserId.endDate_s", new Date());
		pageEntity.setParams(params);
		List<ShopUser> dailiList = this.productService.getAllDaiLiByCurrentUserId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("dailiList", dailiList);
		pageEntity =  this.productService.getFProductPage(pageEntity);
		List<FProductUtil> fpuList = new ArrayList<FProductUtil>();
		for(FProduct fp : pageEntity.getResult()){
			FProductUtil fProductUtil  = new FProductUtil();
			BeanUtils.copyProperties(fp, fProductUtil);
			List<DFUserRelation> dfList = dFUserRelationService.getByProperty(fp.getCreateUserId().getId(),fp.getModifyUserId().getId());
			if(dfList != null && dfList.size() > 0){
				if(dfList.get(0).getDesc() != null){
					fProductUtil.setParentDesc(dfList.get(0).getDesc());
				}
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("fpuList", fpuList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "f/product_list";
	}
	
	/**
	 * 批量修改分销产品上下架状态
	 * @Title: updateFProductOnSale 
	 * @Description: TODO
	 * @param @param onSale
	 * @param @param ids
	 * @param @param request
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "updateOnSale")
	public Map<String, Object> updateFProductOnSale(Model model, HttpServletRequest request){
		String onSale = request.getParameter("onSale");
		String ids = request.getParameter("ids");
		Map<String,Object> result = new HashMap<String, Object>();
		boolean flag = productService.updateFProductOnSale(Integer.parseInt(onSale), ids);
		if(flag){
			result.put("status", true);
			result.put("msg", "更新成功。");
		}else{
			result.put("status", false);
			result.put("msg", "操作失败，请重试。");
		}
		return result;
	}
	
	/**
	 * 删除分销产品
	 */
	@RequestMapping(value = "deleteFDProduct/{id}")
	public String deleteFProductById(@PathVariable("id") Long id, Model model, HttpServletRequest request){
		productService.deleteFProductById(id);
		return "redirect:/fproduct/list";
	}
	
	/**
	 * 根据分销查看代理
	 */
	@RequestMapping(value = "mydl")
	public String queryDByCurrentUser(Model model, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		PageEntity<DFUserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		UserUtil userUtil = super.getSessionUser(request.getSession());
		pageEntity.getParams().put("child.id", userUtil.getId());
		pageEntity.getParams().put("status", 2);
		pageEntity =  this.dFUserRelationService.getPage(pageEntity);
		List<DFUserRelation> dfList = pageEntity.getResult();//当页的代理实体
		if(dfList.size() > 0){
			List<Long> daiLiIds = new ArrayList<Long>();
			for (DFUserRelation df : dfList){
				daiLiIds.add(df.getParent().getId());
				//查询到可见该代理下的产品总数
				int hasCount = this.dFUserRelationService.queryHasFProductById(userUtil.getId(), df.getParent().getId());
				df.setHasProductCount(hasCount);
			}
			//通过代理的ids 查询到每个代理下的商品数量
			List<Object> allCountList = this.dFUserRelationService.queryAllDProductByIds(daiLiIds);
			Map<Long, Integer> allCountMap = new HashMap<Long, Integer>();
			if(allCountList.size() > 0){
				for (int i = 0; i < allCountList.size(); i++){
					Object o = allCountList.get(i);
					Object[] oArray = (Object[])o;
					allCountMap.put(Long.parseLong(String.valueOf(oArray[1])), Integer.parseInt(String.valueOf(oArray[0])));
				}
			}
			
			for(DFUserRelation df : dfList){
				if(allCountMap.get(df.getParent().getId()) != null){
					df.setAllProductCount(allCountMap.get(df.getParent().getId()));
				}
			}
		}
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "/f/mydl";
	}
	@ResponseBody
	@RequestMapping(value = "updateUserRelationDesc")
	public Map<String, Object> updateUserRelationDesc(Model model, HttpServletRequest request){
		String message = request.getParameter("message");
		Long id = Long.parseLong(request.getParameter("id"));
		Map<String,Object> result = new HashMap<String, Object>();
		boolean flag = dFUserRelationService.updateDesc(message, id);
		if(flag){
			result.put("status", true);
			result.put("msg", "修改成功。");
		}else{
			result.put("status", false);
			result.put("msg", "操作失败，请重试。");
		}
		return result;
	}
	
	/**
	 * 初始化代理产品修改页面
	 * @Title: initUpdateDProduct 
	 * @Description: TODO
	 * @param @param id
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "initUpdateFProduct/{queryId}", method = RequestMethod.GET)
	public String initUpdateFProduct(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception{
		FProduct fProduct = this.productService.getDProductById(id);
		model.addAttribute("fProduct", fProduct);
		//获取分销编辑后产品的图片
		List<ProductPic> fPicList = productPicService.getPicByFProductId(fProduct.getId());
		StringBuffer sb = new StringBuffer();
		if(fPicList != null && fPicList.size() > 0){
			for(ProductPic pic:fPicList){
				sb.append(pic.getImageUrl());
				sb.append(",");
			}
		}
		model.addAttribute("fPicString", sb.toString());
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(fProduct.getCreateUserId().getId());
		model.addAttribute("pageBean", kindList);
		//获取产品的图片
		List<ProductPic> picList = productPicService.getPicByDProductId(fProduct.getdProduct().getId());
		model.addAttribute("picList", picList);
		return "f/updateProduct";
	}
	/**
	 * @throws ParseException 
	 * 修改产品
	 * @Title: updateDProduct 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @param dProduct
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateDProduct(Model model, HttpServletRequest request, @ModelAttribute FProduct fProduct) throws ParseException{
		String[] kindIds = request.getParameterValues("kindId");
		if(kindIds != null && kindIds.length > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < kindIds.length; i++){
				sb.append(kindIds[i]).append(",");
			}
			sb.setLength(sb.length() - 1);
			fProduct.setKindId(sb.toString());
			List<PKind> pkList = productService.getKindByIds(sb.toString());
			if(pkList != null && pkList.size()>0){
				fProduct.setShowKind(pkList.get(0).getKindName());
			}
		}
		String createUserId = request.getParameter("updatecreateUserId");
		String createTime = request.getParameter("updatecreateTime");
		fProduct.setModifyUserId(super.getSessionUser(request.getSession()).getShopUser());
		if(fProduct.getOnSale() == -1){
			fProduct.setDeleteFlag(-1);
		}else{
			fProduct.setDeleteFlag(0);
		}
		fProduct.setCreateUserId(shopUserService.findById(Long.parseLong(createUserId)));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		fProduct.setCreateTime(sdf.parse(createTime));
		
		String parentDproductId = request.getParameter("dProductId");
		fProduct.setdProduct(this.productService.findDProductById(Long.parseLong(parentDproductId)));
		productService.updateFProduct(fProduct);
		
		//获取产品添加是上传的图片
		String[] productImage = request.getParameter("imagesUrls").split(",");
		if(productImage != null && productImage.length > 0){
			List<ProductPic> list = new ArrayList<ProductPic>();
			for(int i=0;i<productImage.length;i++){
				ProductPic pp = new ProductPic();
				pp.setFproduct(fProduct);
				pp.setImageUrl(productImage[i]);
				pp.setThumbUrl(productImage[i]);
				pp.setCreateDate(new Date());
				list.add(pp);
			}
			if(list.size() > 0){
				productPicService.addFProductPic(list);
			}
		}
		return "redirect:/fproduct/list";
	}
	
	@RequestMapping(value = "previewFProduct/{id}")
	public String previewFProduct(@PathVariable("id") Long id, Model model, HttpServletRequest request){
		FProduct fProduct = this.productService.getDProductById(id);
		FProductUtil fProductUtil = new FProductUtil();
		BeanUtils.copyProperties(fProduct, fProductUtil);
		List<ProductPic> picList = new ArrayList<ProductPic>();
		picList = productPicService.getPicByFProductId(fProduct.getId());
		if(picList != null && picList.size() > 0){
			fProductUtil.setImageList(picList);
			fProductUtil.setFirstImage(picList.get(0).getImageUrl());
		}
		//获取产品的标签
		List<PKind> kindList = productService.getKindByIds(fProduct.getKindId());
		StringBuffer sb = new StringBuffer();
		if(kindList != null && kindList.size()>0){
			for(PKind kl:kindList){
				sb.append(kl.getKindName());
				sb.append(",");
			}
		}
		if(sb.toString().length() >= 1){
			fProductUtil.setKindString(sb.toString().substring(0, sb.toString().length()-1));
		}
		model.addAttribute("fProduct", fProductUtil);
		
		//获取业务的代理标签
		List<DFUserRelation>  dfuserRelationList = dFUserRelationService.getByProperty(fProduct.getCreateUserId().getId(), fProduct.getModifyUserId().getId());
		if(dfuserRelationList != null && dfuserRelationList.size()>0){
			model.addAttribute("dailiDesc", dfuserRelationList.get(0).getDesc());
		}
		return "f/preview";
	}
}
