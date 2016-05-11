package com.mendao.framework.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
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
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity =  this.productService.getDProductPage(pageEntity);
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
	@RequestMapping(value = "initAdd")
	public String initAdd(Model model, HttpServletRequest request) throws Exception {
		return "p/addAndUpdProduct";
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
		@SuppressWarnings("unchecked")
		PageEntity<PKind> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
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
	
}
