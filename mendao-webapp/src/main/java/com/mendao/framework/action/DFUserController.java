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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FShowProduct;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.FShowProductService;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/df/user")
@SessionAttributes(types = UserUtil.class)
public class DFUserController extends BaseController {
	
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	DFUserRelationService dFUserRelationService;
	
	@Autowired
	FShowProductService fShowProductService;
	
	@Autowired
	ProductService productService;
	/**
	 * 获取当前代理下的所有分销商
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<DFUserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		UserUtil userUtil = super.getSessionUser(request.getSession());
		pageEntity.getParams().put("parent.id", userUtil.getId());
		pageEntity.getParams().put("status", 2);
		pageEntity =  this.dFUserRelationService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/user_list";
	}
	/**
	 * 获取未添加分销列表
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAllFenxiao")
	public String getAllF(Model model, HttpServletRequest request) throws Exception {
		PageEntity<ShopUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		List<Long> list = new ArrayList<Long>();
		UserUtil userUtil = super.getSessionUser(request.getSession());
		//获取已经添加的分销商的ID列表
		list = this.dFUserRelationService.getChildId(userUtil.getId());
		if(list.size() > 0){
			pageEntity.getParams().put("id_notin", list);
		}
		pageEntity.getParams().put("role.id", (long)3);
		pageEntity =  this.shopUserService.getFenxiaoPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/add_user_list";
	}
	/**
	 * 添加分销商
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addUserToProxy")
	public Map<String,Object> addUserToProxy(Model model, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String username = request.getParameter("username");
			List<ShopUser> list = shopUserService.getUserByUserNameAndRole(username,(long) 3);
			if(list.size() == 0){
				result.put("status", 0);
				result.put("msg", "业务账号输入有误，请重新输入。");
			}else{
				UserUtil userUtil = super.getSessionUser(request.getSession());
				List<DFUserRelation> dfList = dFUserRelationService.getListByProperty(userUtil.getId(),list.get(0).getId());
				if(dfList.size() > 0){
					result.put("status", 2);
					result.put("msg", "对不起，您已经添加此业务。");
				}else{
					dFUserRelationService.addUserToProxy(userUtil.getId(),list.get(0));
					result.put("status", 1);
					result.put("msg", "添加成功。");
				}
			}
		}catch(Exception e){
			result.put("status", -1);
			result.put("msg", "添加失败。");
		}
		return result;
	}
	/**
	 * 删除分销商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id) throws Exception {
		dFUserRelationService.deleteById(id);
		return "redirect:/df/user/list";
	}
	/**
	 * 查看分销商可见商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowProject/{queryId}", method = RequestMethod.GET)
	public String getShowProject(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception {
		PageEntity<FShowProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("user.id", id);
		pageEntity =  this.fShowProductService.getProductPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		model.addAttribute("proxyId", id);
		return "/df/product_list";
	}
	
	/**
	 * 获取未添加产品列表
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAllDproduct/{proxyId}")
	public String getAllDproduct(@PathVariable("proxyId") Long proxyId,Model model, HttpServletRequest request) throws Exception {
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		//获得已经添加的product的IDlist
		List<Long> list = fShowProductService.getDProductByUserId(proxyId);
		if(list.size() > 0){
			params.put("id_notin", list);
		}
		params.put("status", 0);
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		pageEntity.setParams(params);
		pageEntity =  this.productService.getDProductPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		model.addAttribute("proxyId", proxyId);
		return "df/add_product_list";
	}
	
	/**
	 * 添加分销商可见商品
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addProductToProxy")
	public Map<String,Object> addProductToProxy(Model model, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			UserUtil userUtil = super.getSessionUser(request.getSession());
			ShopUser dUser = shopUserService.findById(userUtil.getId());
			String ids = request.getParameter("ids");
			String proxyId = request.getParameter("proxyId");
			ShopUser proxyUser = shopUserService.findById(Long.valueOf(proxyId));
			fShowProductService.addProductToProxy(dUser,proxyUser,ids);
			result.put("msg", 1);
		}catch(Exception e){
			result.put("msg", -1);
		}
		return result;
	}
	
	/**
	 * 删除分销商可见产品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDProduct/{queryId}/{fid}", method = RequestMethod.GET)
	public String deleteDProduct(@PathVariable("queryId") Long id,@PathVariable("fid") Long fid) throws Exception {
		fShowProductService.deleteById(id);
		return "redirect:/df/user/getShowProject/"+fid;
	}
	
}
