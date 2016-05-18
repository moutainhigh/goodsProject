package com.mendao.framework.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
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
		@SuppressWarnings("unchecked")
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		if(-1 != status){
			params.put("status", status);
		}
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		pageEntity.setParams(params);
		List<PKind> kindList = productService.queryAllPropertiesByCreateId(super.getSessionUser(request.getSession()).getShopUser().getId());
		Map<Long, String> kindMap = new HashMap<Long, String>();
		if(kindList.size() > 0){
			for(PKind pKind : kindList){
				kindMap.put(pKind.getId(), pKind.getKindName());
			}
		}
		pageEntity =  this.productService.getDProductPage(pageEntity);
		List<DProduct> products = pageEntity.getResult();
		if((kindMap.size() > 0) && (null != products) && (products.size() > 0)){
			for(DProduct product : products){
				String ids = product.getKindId();
				if(null != ids){
					String[] kindIds = ids.split(",");
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < kindIds.length; i++){
						sb.append(kindMap.get(Long.parseLong(kindIds[i]))).append(",");
					}
					sb.setLength(sb.length() - 1);
					product.setComment(sb.toString());
				}
			}
		}
		
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "p/product_list";
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
		return "p/addProduct";
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
		String[] kindIds = request.getParameterValues("kindId");
		if(null != kindIds && kindIds.length > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < kindIds.length; i++){
				sb.append(kindIds[i]).append(",");
			}
			sb.setLength(sb.length() - 1);
			dProduct.setKindId(sb.toString());
		}
		dProduct.setCreateUserId(super.getSessionUser(request.getSession()).getShopUser());
		dProduct.setCreateTime(new Date());
		dProduct.setDeleteFlag(0);
		productService.addDProduct(dProduct);
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
		return "redirect:/dproduct/list/-1";
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
		return "p/updateProduct";
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
		String[] kindIds = request.getParameterValues("kindId");
		if(kindIds.length > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < kindIds.length; i++){
				sb.append(kindIds[i]).append(",");
			}
			sb.setLength(sb.length() - 1);
			dProduct.setKindId(sb.toString());
		}
		String createUserId = request.getParameter("updatecreateUserId");
		String createTime = request.getParameter("updatecreateTime");
		dProduct.setModifyUserId(super.getSessionUser(request.getSession()).getShopUser());
		dProduct.setDeleteFlag(0);
		dProduct.setCreateUserId(shopUserService.findById(Long.parseLong(createUserId)));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		dProduct.setCreateTime(sdf.parse(createTime));
		productService.updateDProduct(dProduct);
		
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
		return "redirect:/dproduct/list/-1";
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
		productService.updateProductStatus(Integer.parseInt(status), ids);
		return "redirect:/dproduct/list/-1";
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
}
