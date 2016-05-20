package com.mendao.framework.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.FProduct;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
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
		if(null != userId && "" != userId){
			params.put("createUserId.id", Long.parseLong(userId));
			model.addAttribute("userId", Long.parseLong(userId));
		}
		if(null != onSale && "" != onSale){
			params.put("onSale", Integer.parseInt(onSale));
			model.addAttribute("onSale", Integer.parseInt(onSale));
		}
		params.put("deleteFlag", 0);
		params.put("modifyUserId", super.getSessionUser(request.getSession()).getShopUser());
		pageEntity.setParams(params);
		List<ShopUser> dailiList = this.productService.getAllDaiLiByCurrentUserId(super.getSessionUser(request.getSession()).getShopUser().getId());
		model.addAttribute("dailiList", dailiList);
		pageEntity =  this.productService.getFProductPage(pageEntity);
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
			//查询到可见该代理下的产品总数
			List<Object> hasCountList = this.dFUserRelationService.queryHasFProductByIds(daiLiIds);
			Map<Long, Integer> hasCountMap = new HashMap<Long, Integer>();
			if(hasCountList.size() > 0){
				for (int i = 0; i < hasCountList.size(); i++){
					Object o = hasCountList.get(i);
					Object[] oArray = (Object[])o;
					hasCountMap.put(Long.parseLong(String.valueOf(oArray[1])), Integer.parseInt(String.valueOf(oArray[0])));
				}
			}
			
			for(DFUserRelation df : dfList){
				df.setAllProductCount(allCountMap.get(df.getParent().getId()));
				df.setHasProductCount(hasCountMap.get(df.getParent().getId()));
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
	
}
