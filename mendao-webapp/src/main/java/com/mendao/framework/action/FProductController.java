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
import com.mendao.business.entity.DProduct;
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
		List<PKind> kindList = productService.queryAllByYewuId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("kindList", kindList);
		//获取未编辑的产品数目
		int count = productService.getNotChangeProduct(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("count", count);
		return "f/new_product_list";
	}
	
	@RequestMapping(value = "getItem")
	public String getItem(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		String price = request.getParameter("price");
		String kind = request.getParameter("kind");
		String status = request.getParameter("status");
		if(null != price && "" != price && !"0".equals(price)){
			if("1".equals(price)){
				params.put("price_e", Integer.parseInt("1000"));
			}else if("2".equals(price)){
				params.put("price_s", Integer.parseInt("1000"));
				params.put("price_e", Integer.parseInt("1500"));
			}else if("3".equals(price)){
				params.put("price_s", Integer.parseInt("1600"));
				params.put("price_e", Integer.parseInt("2000"));
			}else if("4".equals(price)){
				params.put("price_s", Integer.parseInt("2100"));
				params.put("price_e", Integer.parseInt("2500"));
			}else if("5".equals(price)){
				params.put("price_s", Integer.parseInt("2600"));
				params.put("price_e", Integer.parseInt("3000"));
			}else if("6".equals(price)){
				params.put("price_s", Integer.parseInt("3100"));
				params.put("price_e", Integer.parseInt("5000"));
			}else if("7".equals(price)){
				params.put("price_s", Integer.parseInt("5001"));
			}
		}
		if(null != status && "" != status && !"2".equals(status)){
			params.put("status", Long.parseLong(status));
		}
		if(null != kind && "" != kind && !"0".equals(kind)){
			params.put("kindId", Integer.parseInt(kind));
		}
		params.put("deleteFlag", 0);
		params.put("type", 0);
		params.put("changeFlag", 1);
		params.put("modifyUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("dProduct.deleteFlag", 0);
		params.put("createUserId.endDate_s", new Date());
		pageEntity.setParams(params);
		pageEntity.setOrderBy(" order by o.createTime desc ");
		List<ShopUser> dailiList = this.productService.getAllDaiLiByCurrentUserId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("dailiList", dailiList);
		pageEntity =  this.productService.getFProductPage(pageEntity);
		List<FProductUtil> fpuList = new ArrayList<FProductUtil>();
		for(FProduct fp : pageEntity.getResult()){
			FProductUtil fProductUtil  = new FProductUtil();
			BeanUtils.copyProperties(fp, fProductUtil);
			List<DFUserRelation> dfList = dFUserRelationService.getByProperty(fp.getCreateUserId().getId(),fp.getModifyUserId().getId());
			if(dfList != null && dfList.size() > 0){
				if(dfList.get(0).getDesc() != null && !"".equals(dfList.get(0).getDesc())){
					fProductUtil.setParentDesc(dfList.get(0).getDesc());
				}else{
					fProductUtil.setParentDesc(fp.getCreateUserId().getUserName());
				}
			}
			if(fp.getdProduct().getDownTime() != null && fp.getdProduct().getDownTime().getTime() > 0){
				fProductUtil.setDownTime(fp.getdProduct().getDownTime().getTime());
			}
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByFProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("fpuList", fpuList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "f/product_item";
	}
	
	@RequestMapping(value = "noChange")
	public String noChange(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 1000);
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("deleteFlag", 0);
		params.put("type", 0);
		params.put("changeFlag", 0);
		params.put("modifyUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("dProduct.deleteFlag", 0);
		params.put("createUserId.endDate_s", new Date());
		pageEntity.setParams(params);
		pageEntity.setOrderBy(" order by o.createTime desc ");
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
			if(fp.getdProduct().getDownTime() != null && fp.getdProduct().getDownTime().getTime() > 0){
				fProductUtil.setDownTime(fp.getdProduct().getDownTime().getTime());
			}
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByFProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("fpuList", fpuList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "f/product_nochange";
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
		String requestUrl = request.getHeader("Referer");  
		if(requestUrl != null){
			return "redirect:"+requestUrl;
		}else{
			return "redirect:/fproduct/list";
		}
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
		FProduct fProduct = this.productService.getFProductById(id);
		model.addAttribute("fProduct", fProduct);
		//获取分销编辑后产品的图片
		List<ProductPic> fPicList = productPicService.getPicByFProductId(fProduct.getId());
		StringBuffer sb = new StringBuffer();
		String firstImage = "";
		if(fPicList != null && fPicList.size() > 0){
			for(int i=0;i<fPicList.size();i++){
				if(i==0){
					firstImage = fPicList.get(i).getImageUrl()+",";
				}else{
					sb.append(fPicList.get(i).getImageUrl());
					sb.append(",");
				}
			}
		}
		model.addAttribute("firstPicString", firstImage);
		model.addAttribute("fPicString", sb.toString());
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(fProduct.getCreateUserId().getId());
		model.addAttribute("pageBean", kindList);
		//获取产品的图片
		List<ProductPic> picList = productPicService.getPicByDProductId(fProduct.getdProduct().getId());
		model.addAttribute("picList", picList);
		String requestUrl = request.getHeader("Referer");  
		model.addAttribute("requestUrl", requestUrl);
		return "f/update_product";
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
		String type = request.getParameter("priviewType");
		if(type != null && "1".equals(type)){
			String kindId = request.getParameter("kindId");
			if(kindId != null){
				String showKind = "";
				showKind = productService.findById(Long.valueOf(kindId)).getKindName(); 
				fProduct.setKindId(Long.valueOf(kindId));
				fProduct.setShowKind(showKind);
			}
			String createUserId = request.getParameter("updatecreateUserId");
			fProduct.setModifyUserId(super.getSessionUser(request.getSession()).getShopUser());
			fProduct.setCreateUserId(shopUserService.findById(Long.parseLong(createUserId)));
			fProduct.setCreateTime(new Date());
			fProduct.setDeleteFlag(0);
			fProduct.setChangeFlag(1);
			fProduct.setOnSale(fProduct.getStatus());
			fProduct.setType(0);
			String parentDproductId = request.getParameter("dProductId");
			fProduct.setdProduct(this.productService.findDProductById(Long.parseLong(parentDproductId)));
			
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(fProduct, fProductUtil);
			String[] productImage = request.getParameterValues("productImage");
			if(productImage != null && productImage.length > 0){
				fProductUtil.setFirstImage(productImage[0]);
				List<ProductPic> list = new ArrayList<ProductPic>();
				for(int i=0;i<productImage.length;i++){
					ProductPic pp = new ProductPic();
					pp.setFproduct(fProduct);
					pp.setImageUrl(productImage[i]);
					pp.setThumbUrl(productImage[i]);
					pp.setCreateDate(new Date());
					list.add(pp);
				}
				fProductUtil.setImageList(list);
			}
			//重置产品的视频
			if(fProduct.getdProduct().getVideoUrl() !=null && !"".equals(fProduct.getdProduct().getVideoUrl())){
				fProductUtil.setVideoUrl(fProduct.getVideoUrl());
			}else{
				fProductUtil.setVideoUrl("");
			}
			model.addAttribute("fProduct", fProductUtil);
			return "f/preview";
		}else{
			String kindId = request.getParameter("kindId");
			if(kindId != null){
				String showKind = "";
				showKind = productService.findById(Long.valueOf(kindId)).getKindName(); 
				fProduct.setKindId(Long.valueOf(kindId));
				fProduct.setShowKind(showKind);
			}
			String createUserId = request.getParameter("updatecreateUserId");
			fProduct.setModifyUserId(super.getSessionUser(request.getSession()).getShopUser());
			fProduct.setCreateUserId(shopUserService.findById(Long.parseLong(createUserId)));
			fProduct.setCreateTime(new Date());
			fProduct.setDeleteFlag(0);
			fProduct.setChangeFlag(1);
			fProduct.setOnSale(fProduct.getStatus());
			fProduct.setType(0);
			String parentDproductId = request.getParameter("dProductId");
			fProduct.setdProduct(this.productService.findDProductById(Long.parseLong(parentDproductId)));
			productService.updateFProduct(fProduct);
			
			List<ProductPic> list = new ArrayList<ProductPic>();
			//获取首图
			String firstImage = request.getParameter("firstImage");
			if(firstImage != null && !firstImage.equals("")){
				ProductPic pp = new ProductPic();
				pp.setFproduct(fProduct);
				pp.setImageUrl(firstImage);
				pp.setThumbUrl(firstImage);
				pp.setCreateDate(new Date());
				list.add(pp);
			}
			//获取产品添加是上传的图片
			String[] productImage = request.getParameterValues("imagesUrls");
			if(productImage != null && productImage.length > 0){
				for(int i=0;i<productImage.length;i++){
					if(!productImage[i].equals(firstImage)){
						ProductPic pp = new ProductPic();
						pp.setFproduct(fProduct);
						pp.setImageUrl(productImage[i]);
						pp.setThumbUrl(productImage[i]);
						pp.setCreateDate(new Date());
						list.add(pp);
					}
				}
			}
			if(list.size() > 0){
				productPicService.addFProductPic(list);
			}
			String requestUrl = request.getParameter("requestUrl");
			if(requestUrl != null){
				return "redirect:"+requestUrl;
			}else{
				return "redirect:/fproduct/list";
			}
		}
	}
	
	@RequestMapping(value = "previewFProduct/{id}")
	public String previewFProduct(@PathVariable("id") Long id, Model model, HttpServletRequest request){
		FProduct fProduct = this.productService.getFProductById(id);
		FProductUtil fProductUtil = new FProductUtil();
		BeanUtils.copyProperties(fProduct, fProductUtil);
		List<ProductPic> picList = new ArrayList<ProductPic>();
		picList = productPicService.getPicByFProductId(fProduct.getId());
		if(picList != null && picList.size() > 0){
			fProductUtil.setImageList(picList);
			fProductUtil.setFirstImage(picList.get(0).getImageUrl());
		}
		//获取产品的标签
//		List<PKind> kindList = productService.getKindByIds(fProduct.getKindId());
//		StringBuffer sb = new StringBuffer();
//		if(kindList != null && kindList.size()>0){
//			for(PKind kl:kindList){
//				sb.append(kl.getKindName());
//				sb.append(",");
//			}
//		}
//		if(sb.toString().length() >= 1){
//			fProductUtil.setKindString(sb.toString().substring(0, sb.toString().length()-1));
//		}
		//重置产品的视频
		if(fProductUtil.getdProduct().getVideoUrl() !=null && !"".equals(fProductUtil.getdProduct().getVideoUrl())){
			fProductUtil.setVideoUrl(fProductUtil.getdProduct().getVideoUrl());
		}else{
			fProductUtil.setVideoUrl("");
		}
		
		model.addAttribute("fProduct", fProductUtil);
		
		//获取业务的代理标签
		List<DFUserRelation>  dfuserRelationList = dFUserRelationService.getByProperty(fProduct.getCreateUserId().getId(), fProduct.getModifyUserId().getId());
		if(dfuserRelationList != null && dfuserRelationList.size()>0){
			model.addAttribute("dailiDesc", dfuserRelationList.get(0).getDesc());
		}
		return "f/preview";
	}
	
	/**
	 * 
	 * @Title: search 
	 * @Description: search产品列表
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "search")
	public String search(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("selectedByDaili");
		String onSale = request.getParameter("selectedByOnSale");
		String deleteFalg = request.getParameter("selectedDeleteFalg");
		String pName = request.getParameter("pName");
		if(null != userId && "" != userId){
			params.put("createUserId.id", Long.parseLong(userId));
			model.addAttribute("userId", Long.parseLong(userId));
		}
		if(null != pName && "" != pName){
			params.put("pName", pName);
			model.addAttribute("pName", pName);
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
		params.put("dProduct.deleteFlag", 0);
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
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByFProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("fpuList", fpuList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "f/search_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "fCheckUploadNum")
	public Map<String, Object> checkUploadNum(Model model, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		boolean flag = true;
		ShopUser shopUser = super.getSessionUser(request.getSession()).getShopUser();
		int count = productService.getOnSoleProductNum(shopUser.getId());
		String isOnsale = request.getParameter("isOnsale");
		if(isOnsale != null && "1".equals(isOnsale) ){
			count = count+1;
		}
//		if(Integer.valueOf(shopUser.getUploadNum()) - count > 0){
//			flag = true;
//		}else{
//			flag = false;
//		}
//		if(flag){
//			result.put("status", true);
//		}else{
//			result.put("status", false);
//		}
		return result;
	}
	
	/**
	 * 批量上架
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "upProduct")
	public Map<String, Object> upProduct(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			for(String proId:id){
				FProduct fProduct = productService.getFProductById(Long.valueOf(proId));
				fProduct.setStatus(1);
				fProduct.setOnSale(1);
				productService.updateFProduct(fProduct);
			}
			result.put("status", 1);
			result.put("message", "删除成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("message", "删除失败");
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "downProduct")
	public Map<String, Object> downProduct(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			for(String proId:id){
				FProduct fProduct = productService.getFProductById(Long.valueOf(proId));
				fProduct.setStatus(0);
				fProduct.setOnSale(0);
				productService.updateFProduct(fProduct);
			}
			result.put("status", 1);
			result.put("message", "删除成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("message", "删除失败");
		}
		return result;
	}
}
