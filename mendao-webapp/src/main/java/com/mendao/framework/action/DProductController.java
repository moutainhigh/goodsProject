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
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.FShowProductService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.entity.util.FProductUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

/**
 * 
 * @ClassName: ProductController 
 * @Description: TODO 代理产品controller
 * @author TianMeifeng
 * @date 2016年5月8日 上午1:23:55 
 *
 */
@Controller
@RequestMapping("/dproduct")
@SessionAttributes(types = UserUtil.class)
public class DProductController extends BaseController {
	
	@Autowired
	ProductService productService;
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	ProductPicService productPicService;
	
	@Autowired
	DFUserRelationService dFUserRelationService ;
	
	@Autowired
	FShowProductService fShowProductService;
	
	@Autowired
	private FProductRepository fProductRepository;
	
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
	@RequestMapping(value = "list/{condition}")
	public String query(@PathVariable("condition") Integer status, Model model, HttpServletRequest request) throws Exception {
		String pName = request.getParameter("pName");
		if(pName == null){
			model.addAttribute("pName", "");
		}else{
			model.addAttribute("pName", pName);
		}
		return "p/new_product_list";
	}
	
	@RequestMapping(value = "getItem")
	public String getItem(Model model, HttpServletRequest request) throws Exception {
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		String status = request.getParameter("status");
		if(status != null && !"".equals("")){
			params.put("status", Integer.valueOf(status));
		}
		String pName = request.getParameter("pName");
		if(null != pName && "" != pName.trim()){
			params.put("pName", pName);
		}
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		pageEntity.setParams(params);
		pageEntity.setOrderBy(" order by o.createTime desc ");
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(super.getSessionUser(request.getSession()).getShopUser().getId());
		Map<Long, String> kindMap = new HashMap<Long, String>();
		if(kindList.size() > 0){
			for(PKind pKind : kindList){
				kindMap.put(pKind.getId(), pKind.getKindName());
			}
		}
		pageEntity =  this.productService.getDProductPage(pageEntity);
		List<DProduct> products = pageEntity.getResult();
		List<FProductUtil> dProductList = new ArrayList<FProductUtil>();
		
		if((kindMap.size() > 0) && (null != products) && (products.size() > 0)){
			for(DProduct product : products){
				FProductUtil fProductUtil = new FProductUtil();
				BeanUtils.copyProperties(product, fProductUtil);
				if(fProductUtil.getpName().length() > 10){
					fProductUtil.setpName(fProductUtil.getpName().substring(0, 10)+"...");
				}
				List<ProductPic> picList = new ArrayList<ProductPic>();
				picList = productPicService.getPicByDProductId(product.getId());
				if(picList != null && picList.size() > 0){
					fProductUtil.setImageList(picList);
					fProductUtil.setFirstImage(picList.get(0).getImageUrl());
				}
				if(product.getDownTime() != null){
					fProductUtil.setDownTime(product.getDownTime().getTime());
				}
				dProductList.add(fProductUtil);
			}
		}
		
		
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("list", dProductList);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "p/product_item";
	}
	
	/**
	 * @Title: initAdd 
	 * @Description: 初始化添加产品页面
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "initAddProduct", method = RequestMethod.GET)
	public String initAdd(Model model, HttpServletRequest request) throws Exception {
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("pageBean", kindList);
		return "p/add_product";
	}
	
	/**
	 * 
	 * @Title: addProduct 
	 * @Description: 添加产品
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(Model model, HttpServletRequest request, @ModelAttribute DProduct dProduct) throws Exception {
		String type = request.getParameter("priviewType");
		if(type != null && "1".equals(type)){
			String kindId = request.getParameter("kindId");
			if(kindId != null){
				String showKind = "";
				showKind = productService.findById(Long.valueOf(kindId)).getKindName(); 
				dProduct.setKindId(Long.valueOf(kindId));
				dProduct.setShowKind(showKind);
			}
			dProduct.setCreateUserId(super.getSessionUser(request.getSession()).getShopUser());
			dProduct.setCreateTime(new Date());
			dProduct.setDownTime(new Date());
			if(dProduct.getStatus() == -1){
				dProduct.setDeleteFlag(-1);
			}else{
				dProduct.setDeleteFlag(0);
			}
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(dProduct, fProductUtil);
			String[] productImage = request.getParameterValues("productImage");
			if(productImage != null && productImage.length > 0){
				fProductUtil.setFirstImage(productImage[0]);
				List<ProductPic> list = new ArrayList<ProductPic>();
				for(int i=0;i<productImage.length;i++){
					ProductPic pp = new ProductPic();
					pp.setDproduct(dProduct);
					pp.setImageUrl(productImage[i]);
					pp.setThumbUrl(productImage[i]);
					pp.setCreateDate(new Date());
					list.add(pp);
				}
				fProductUtil.setImageList(list);
			}
			//重置产品的视频
			if(dProduct.getVideoUrl() !=null && !"".equals(dProduct.getVideoUrl())){
				fProductUtil.setVideoUrl(dProduct.getVideoUrl());
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
				dProduct.setKindId(Long.valueOf(kindId));
				dProduct.setShowKind(showKind);
			}
			dProduct.setCreateUserId(super.getSessionUser(request.getSession()).getShopUser());
			dProduct.setCreateTime(new Date());
			dProduct.setDownTime(new Date());
			if(dProduct.getStatus() == -1){
				dProduct.setDeleteFlag(-1);
			}else{
				dProduct.setDeleteFlag(0);
			}
			dProduct = productService.addDProduct(dProduct);
			
			//获取产品添加是上传的图片
			String[] productImage = request.getParameterValues("productImage");
			if(productImage != null && productImage.length > 0){
				List<ProductPic> list = new ArrayList<ProductPic>();
				for(int i=0;i<productImage.length;i++){
					ProductPic pp = new ProductPic();
					pp.setDproduct(dProduct);
					pp.setImageUrl(productImage[i]);
					pp.setThumbUrl(productImage[i]);
					pp.setCreateDate(new Date());
					list.add(pp);
				}
				if(list.size() > 0){
					productPicService.addProductPic(list);
				}
			}
			//如果该产品为上架产品
			if(dProduct.getStatus() == 1){
				//获取该会员的所有好友
				List<DFUserRelation> relationList = dFUserRelationService.getByParentId(dProduct.getCreateUserId().getId());
				if(relationList != null && relationList.size() > 0){
					for(DFUserRelation relation:relationList){
						//将新添加的产品
						fShowProductService.addProductToAllProxy(relation.getChild(),dProduct);
					}
				}
			}
			//将自己产品添加至fproduct
			fShowProductService.addMyProduct(super.getSessionUser(request.getSession()).getShopUser(),dProduct);
			
			return "redirect:/dproduct/list/-1";
		}
	}
	
	/**
	 * 初始化产品修改页面
	 * @Title: initUpdateDProduct 
	 * @Description: TODO
	 * @param @param id
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "initUpdateProduct/{queryId}", method = RequestMethod.GET)
	public String initUpdateDProduct(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception{
		DProduct dProduct = this.productService.findDProductById(id);
		model.addAttribute("dProduct", dProduct);
		
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("pageBean", kindList);
		//获取产品的图片
		List<ProductPic> picList = productPicService.getPicByDProductId(dProduct.getId());
		model.addAttribute("picList", picList);
		String firstImage = "";
		if(picList != null && picList.size() > 0){
			for(int i=0;i<picList.size();i++){
				if(i==0){
					firstImage = picList.get(i).getImageUrl()+",";
				}
			}
		}
		model.addAttribute("firstPicString", firstImage);
		String requestUrl = request.getHeader("Referer");  
		model.addAttribute("requestUrl", requestUrl);
		return "p/update_product";
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
	public String updateDProduct(Model model, HttpServletRequest request, @ModelAttribute DProduct dProduct) throws ParseException{
		String type = request.getParameter("priviewType");
		if(type != null && "1".equals(type)){
			String kindId = request.getParameter("kindId");
			if(kindId != null){
				String showKind = "";
				showKind = productService.findById(Long.valueOf(kindId)).getKindName(); 
				dProduct.setKindId(Long.valueOf(kindId));
				dProduct.setShowKind(showKind);
			}
			dProduct.setCreateUserId(super.getSessionUser(request.getSession()).getShopUser());
			dProduct.setCreateTime(new Date());
			dProduct.setDownTime(new Date());
			if(dProduct.getStatus() == -1){
				dProduct.setDeleteFlag(-1);
			}else{
				dProduct.setDeleteFlag(0);
			}
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(dProduct, fProductUtil);
			String[] productImage = request.getParameterValues("productImage");
			if(productImage != null && productImage.length > 0){
				fProductUtil.setFirstImage(productImage[0]);
				List<ProductPic> list = new ArrayList<ProductPic>();
				for(int i=0;i<productImage.length;i++){
					ProductPic pp = new ProductPic();
					pp.setDproduct(dProduct);
					pp.setImageUrl(productImage[i]);
					pp.setThumbUrl(productImage[i]);
					pp.setCreateDate(new Date());
					list.add(pp);
				}
				fProductUtil.setImageList(list);
			}
			//重置产品的视频
			if(dProduct.getVideoUrl() !=null && !"".equals(dProduct.getVideoUrl())){
				fProductUtil.setVideoUrl(dProduct.getVideoUrl());
			}else{
				fProductUtil.setVideoUrl("");
			}
			model.addAttribute("fProduct", fProductUtil);
			
			return "f/preview";
		}else{
			DProduct oldProduct = productService.findDProductById(dProduct.getId());
			//产品由上架改为下架
			boolean xiajiaFlag =false;
			if(oldProduct.getStatus() == 1 && dProduct.getStatus() == 0){
				xiajiaFlag = true;
			}
			//产品由下架改为上架
			boolean shangjiaFlag =false;
			if(oldProduct.getStatus() == 0 && dProduct.getStatus() == 1){
				shangjiaFlag =true;
			}
			String kindId = request.getParameter("kindId");
			String other = request.getParameter("other");
			if(kindId != null){
				String showKind = "";
				showKind = productService.findById(Long.valueOf(kindId)).getKindName(); 
				dProduct.setKindId(Long.valueOf(kindId));
				dProduct.setShowKind(showKind);
			}
			if(other != null){
				dProduct.setOther(other);
			}
			
			String createUserId = request.getParameter("updatecreateUserId");
			String createTime = request.getParameter("updatecreateTime");
			dProduct.setModifyUserId(super.getSessionUser(request.getSession()).getShopUser());
			if(dProduct.getStatus() == -1){
				dProduct.setDeleteFlag(-1);
				productService.deleteDProductById(dProduct.getId());
				return "redirect:/dproduct/list/-1";
			}else{
				dProduct.setDeleteFlag(0);
			}
			dProduct.setCreateUserId(shopUserService.findById(Long.parseLong(createUserId)));
			dProduct.setCreateTime(new Date());
			productService.updateDProduct(dProduct);
			
			List<ProductPic> list = new ArrayList<ProductPic>();
			//获取首图
			String firstImage = request.getParameter("firstImage");
			if(firstImage != null && !firstImage.equals("")){
				ProductPic pp = new ProductPic();
				pp.setDproduct(dProduct);
				pp.setImageUrl(firstImage);
				pp.setThumbUrl(firstImage);
				pp.setCreateDate(new Date());
				list.add(pp);
			}
			
			//获取产品添加是上传的图片
			String[] productImage = request.getParameterValues("productImage");
			if(productImage != null && productImage.length > 0){
				for(int i=0;i<productImage.length;i++){
					if(!productImage[i].equals(firstImage)){
						ProductPic pp = new ProductPic();
						pp.setDproduct(dProduct);
						pp.setImageUrl(productImage[i]);
						pp.setThumbUrl(productImage[i]);
						pp.setCreateDate(new Date());
						list.add(pp);
					}
				}
			}
			if(list != null && list.size() > 0){
				productPicService.addProductPic(list);
			}
			//修改自有产品的信息
			fShowProductService.updateMyProduct(super.getSessionUser(request.getSession()).getShopUser(),dProduct);
			
			//产品由上架改为下架
			if(xiajiaFlag){
				productService.xiajiaProduct(dProduct.getId());
			}
			//产品由下架改为上架
			if(shangjiaFlag){
				//获取该会员的所有好友
				List<DFUserRelation> relationList = dFUserRelationService.getByParentId(dProduct.getCreateUserId().getId());
				if(relationList != null && relationList.size() > 0){
					for(DFUserRelation relation:relationList){
						//将新添加的产品
						fShowProductService.addProductToAllProxy(relation.getChild(),dProduct);
					}
				}
			}
			
			
			String requestUrl = request.getParameter("requestUrl");
			if(requestUrl != null){
				return "redirect:"+requestUrl;
			}else{
				return "redirect:/dproduct/list/-1";
			}
		}
	}
	
	/**
	 * 代理 批量修改产品状态
	 * @Title: updateSaleDProduct 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "updateSaleDProduct/{queryIds}/{status}", method = RequestMethod.GET)
	public String updateSaleDProduct(@PathVariable("queryIds") String ids, @PathVariable("status") String status, Model model, HttpServletRequest request){
		String requestUrl = request.getHeader("Referer");  
		productService.updateProductStatus(Integer.parseInt(status), ids);
		if(requestUrl != null){
			return "redirect:"+requestUrl;
		}else{
			return "redirect:/dproduct/list/-1";
		}
	}
	/**
	 * 产品搜索
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchGoods")
	public String searchGoods(Model model, HttpServletRequest request){
		return "p/search_product";
	}
	/**
	 * @Title: queryProerpties 
	 * @Description: 属性列表
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "properties/list")
	public String queryProerpties(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createId", super.getSessionUser(request.getSession()).getShopUser());
		@SuppressWarnings("unchecked")
		PageEntity<PKind> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.setParams(params);
		pageEntity =  this.productService.getPKindPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "properties/list";
	}
	
	/**
	 * 初始化新增页面
	 * @Title: initAddPKind 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "initAddKind", method = RequestMethod.GET)
	public String initAddPKind(Model model, HttpServletRequest request){
		//查询出历史添加的地区
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createId", super.getSessionUser(request.getSession()).getShopUser());
		@SuppressWarnings("unchecked")
		PageEntity<PKind> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 100);
		pageEntity.setParams(params);
		pageEntity =  this.productService.getPKindPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		
		return "properties/add";
	}
	
	/**
	 * 
	 * @Title: addPKind 
	 * @Description: 新增
	 * @param @param model
	 * @param @param request
	 * @param @param pKind
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "addPKind", method = RequestMethod.POST)
	public String addPKind(Model model, HttpServletRequest request, @ModelAttribute PKind pKind){
		pKind.setCreateId(super.getSessionUser(request.getSession()).getShopUser());
		this.productService.addPKind(pKind);
		//跳转到列表页面
		return "redirect:/dproduct/properties/list";
	}
	
	/**
	 * 初始化修改页面
	 * @Title: initUpdatePKind 
	 * @Description: TODO
	 * @param @param id
	 * @param @param model
	 * @param @param request
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "initUpdateKind/{queryId}", method = RequestMethod.GET)
	public String initUpdatePKind(@PathVariable("queryId") Long id, Model model){
		PKind pKind = productService.findById(id);
		model.addAttribute("pKind", pKind);
		return "properties/update";
	}
	
	/**
	 * 修改
	 * @Title: updatePKind 
	 * @Description: TODO
	 * @param @param model
	 * @param @param request
	 * @param @param pKind
	 * @param @return    
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "updatePKind", method = RequestMethod.POST)
	public String updatePKind(Model model, HttpServletRequest request, @ModelAttribute PKind pKind){
		this.productService.update(pKind);
		//跳转到列表页面
		return "redirect:/dproduct/properties/list";
	}
	
	@RequestMapping(value = "deletePKind/{queryId}", method = RequestMethod.GET)
	public String deletePKind(@PathVariable("queryId") Long id, Model model, HttpServletRequest request){
		productService.deletePKindById(id);
		//跳转到列表页面
		return "redirect:/dproduct/properties/list";
	}
	
	@RequestMapping(value = "deleteDProduct/{queryId}", method = RequestMethod.GET)
	public String deleteDProduct(@PathVariable("queryId") Long id, Model model, HttpServletRequest request){
		productService.deleteDProductById(id);
		//跳转到列表页面
		return "redirect:/dproduct/list/-1";
	}
	/**
	 * 异步增加属性
	 * @param model
	 * @param request
	 * @param pKind
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addPKindAjax")
	public Map<String, Object> addPKindAjax(Model model, HttpServletRequest request, @ModelAttribute PKind pKind){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			pKind.setCreateId(super.getSessionUser(request.getSession()).getShopUser());
			pKind = this.productService.addPKind(pKind);
			result.put("status", 1);
    		result.put("name", pKind.getKindName());
    		result.put("id", pKind.getId());
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "添加失败");
		}
		return result;
	}
	/**
	 * 异步修改
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePKindAjax")
	public Map<String, Object> updatePKindAjax(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String id = request.getParameter("id");
			String kindName = request.getParameter("kindName");
			PKind pKind = productService.findById(Long.valueOf(id));
			pKind.setKindName(kindName);
			productService.update(pKind);
			result.put("status", 1);
			result.put("msg", "修改成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "修改失败");
		}
		return result;
	}
	/**
	 * 异步删除
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deletePKindAjax")
	public Map<String, Object> deletePKindAjax(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String id = request.getParameter("id");
			productService.deletePKindById(Long.valueOf(id));
			result.put("status", 1);
    		result.put("msg", "删除成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "删除失败");
		}
		return result;
	}
	/**
	 * 改变产品的倒计时时间
	 * @param model
	 * @param request
	 * @param pKind
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeDownTime")
	public Map<String, Object> changeDownTime(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String id = request.getParameter("id");
			String value = request.getParameter("value");
			DProduct product = productService.findDProductById(Long.valueOf(id));
			long times = new Date().getTime()+Long.valueOf(value)*60*1000;
			product.setDownTime(new Date(times));
			productService.updateDProduct(product);
			result.put("status", 1);
			result.put("msg", "修改成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "修改失败");
		}
		return result;
	}
	/**
	 * 
	 * @param model
	 * @param request
	 * @param pKind
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteByIds")
	public Map<String, Object> deleteByIds(Model model, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			for(String proId:id){
				productService.deleteDProductById(Long.valueOf(proId));
			}
			result.put("status", 1);
			result.put("message", "删除成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("message", "删除失败");
		}
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
				DProduct dProduct = productService.findDProductById(Long.valueOf(proId));
				if(dProduct.getStatus() == 1){
					
				}else{
					//获取该会员的所有好友
					List<DFUserRelation> relationList = dFUserRelationService.getByParentId(dProduct.getCreateUserId().getId());
					if(relationList != null && relationList.size() > 0){
						for(DFUserRelation relation:relationList){
							//将新添加的产品
							fShowProductService.addProductToAllProxy(relation.getChild(),dProduct);
						}
					}
					//将自己产品添加至fproduct
					fShowProductService.addMyProduct(super.getSessionUser(request.getSession()).getShopUser(),dProduct);
					dProduct.setStatus(1);
					productService.updateDProduct(dProduct);
				}
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
				DProduct dProduct = productService.findDProductById(Long.valueOf(proId));
				if(dProduct.getStatus() == 1){
					productService.xiajiaProduct(dProduct.getId());
					dProduct.setStatus(0);
					productService.updateDProduct(dProduct);
				}else{
					
				}
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
