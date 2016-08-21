package com.mendao.framework.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.FShowProduct;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.FShowProductService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.common.util.ArrayUtil;
import com.mendao.entity.util.FProductUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;
import com.mysql.fabric.xmlrpc.base.Array;

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
	FProductRepository fProductRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductPicService productPicService;
	/**
	 * 我的好友列表
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		
		return "df/friend_list";
	}
	/**
	 * 我的好友列表
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItem")
	public String getItem(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<DFUserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		UserUtil userUtil = super.getSessionUser(request.getSession());
		pageEntity.getParams().put("parent.id", userUtil.getId());
		pageEntity.getParams().put("status", 2);
		pageEntity =  this.dFUserRelationService.getPage(pageEntity);
		//遍历获取好友的产品数
		List<DFUserRelation> list = new ArrayList<DFUserRelation>();
		for(DFUserRelation dfur:pageEntity.getResult()){
			int count = productService.getProductCountByUserId(dfur.getChild().getId());
			dfur.setAllProductCount(count);
			int hascount = productService.getDownTimeProductCountByUserId(dfur.getChild().getId());
			dfur.setHasProductCount(hascount);
			list.add(dfur);
		}
		pageEntity.setResult(list);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/friend_item";
	}
	/**
	 * 获取申请添加我为好友的记录
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "apply")
	public String apply(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<DFUserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		UserUtil userUtil = super.getSessionUser(request.getSession());
		pageEntity.getParams().put("parent.id", userUtil.getId());
		pageEntity.getParams().put("status", 1);
		pageEntity.getParams().put("type", 1);
		pageEntity =  this.dFUserRelationService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/new_user_apply";
	}
	/**
	 * 经纪人详情页
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "friendDetail/{queryId}")
	public String friendDetail(@PathVariable("queryId") Long id,Model model, HttpServletRequest request) throws Exception {
		DFUserRelation dfur = dFUserRelationService.getById(id);
		int count = productService.getProductCountByUserId(dfur.getChild().getId());
		dfur.setAllProductCount(count);
		int hascount = productService.getDownTimeProductCountByUserId(dfur.getChild().getId());
		dfur.setHasProductCount(hascount);
		model.addAttribute("dfur", dfur);
		return "df/friend_detail";
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
	 * 跳转到添加好友界面
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "add")
	public String add(Model model, HttpServletRequest request) throws Exception {
		
		return "df/add_friend";
	}
	/**
	 * 查找好友
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUser")
	public String getUser(Model model, HttpServletRequest request) throws Exception {
		PageEntity<ShopUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 100);
		pageEntity.getParams().put("status", 1);
		pageEntity.getParams().put("role.id", (long)2);
		pageEntity =  this.shopUserService.getPage(pageEntity);
		
		model.addAttribute("list", pageEntity.getResult());
		return "df/search_item";
	}
	/**
	 * 添加好友
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
			String ids = request.getParameter("ids");
			String[] idList = ids.split(",");
			for(String id:idList){
				UserUtil userUtil = super.getSessionUser(request.getSession());
				List<DFUserRelation> dfList = dFUserRelationService.getListByProperty(userUtil.getId(),Long.valueOf(id));
				if(dfList != null && dfList.size() > 0){
					DFUserRelation dfur = dfList.get(0);
					if(dfur.getStatus() == 1){
						result.put("status", 2);
						result.put("msg", "对不起，你已申请添加该用户。");
					}else if(dfur.getStatus() == 2){
						result.put("status", 2);
						result.put("msg", "对不起，你已添加该用户。");
					}
				}else{
					if(userUtil.getId() == Long.valueOf(id)){
						result.put("status", 2);
						result.put("msg", "对不起，不能添加自己为好友。");
					}else{
						//好友添加方法
						dFUserRelationService.addUserToProxy(userUtil.getId(),shopUserService.findById(Long.valueOf(id)));
						result.put("status", 1);
						result.put("msg", "申请添加好友成功。");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
	@ResponseBody
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String ids = request.getParameter("id");
			String[] idList = ids.split(",");
			for(String id:idList){
				dFUserRelationService.deleteById(Long.valueOf(id));
			}
			result.put("status", 1);
			result.put("msg", "忽略成功");
		}catch(Exception e){
			result.put("status", -1);
			result.put("msg", "请求失败");
		}
		return result;
	}
	/**
	 * 查看好友可见商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllProject/{queryId}", method = RequestMethod.GET)
	public String getAllProject(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception {
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 1000);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("status", 1);
		pageEntity.setParams(params);
		pageEntity =  this.productService.getDProductPage(pageEntity);
		List<FProductUtil> fpuList = new ArrayList<FProductUtil>();
		for(DProduct fp : pageEntity.getResult()){
			FProductUtil fProductUtil  = new FProductUtil();
			BeanUtils.copyProperties(fp, fProductUtil);
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByDProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("list", fpuList);
		List<Long> list = fShowProductService.getDProductByUserId(id);
		String checkedId = StringUtils.join(list.toArray(),",");
		model.addAttribute("checkedId", checkedId+",");
		
		model.addAttribute("proxyId", id);
		return "/df/all_product_list";
	}
	
	/**
	 * 查看好友可见商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowProject/{queryId}", method = RequestMethod.GET)
	public String getShowProject(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception {
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 1000);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("status", 1);
		List<Long> list = fShowProductService.getDProductByUserId(id);
		if(list != null && list.size()>0){
			params.put("id_in", list);
		}else{
			list.add(0l);
			params.put("id_in", list);
		}
		pageEntity.setParams(params);
		pageEntity =  this.productService.getDProductPage(pageEntity);
		List<FProductUtil> fpuList = new ArrayList<FProductUtil>();
		for(DProduct fp : pageEntity.getResult()){
			FProductUtil fProductUtil  = new FProductUtil();
			BeanUtils.copyProperties(fp, fProductUtil);
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByDProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("list", fpuList);
		
		model.addAttribute("proxyId", id);
		return "/df/show_product_list";
	}
	
	/**
	 * 查看好友不可见商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotShowProject/{queryId}", method = RequestMethod.GET)
	public String getNotShowProject(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) throws Exception {
		PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 1000);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", 0);
		params.put("createUserId", super.getSessionUser(request.getSession()).getShopUser());
		params.put("status", 1);
		List<Long> list = fShowProductService.getDProductByUserId(id);
		if(list != null && list.size()>0){
			params.put("id_notin", list);
		}
		pageEntity.setParams(params);
		pageEntity =  this.productService.getDProductPage(pageEntity);
		List<FProductUtil> fpuList = new ArrayList<FProductUtil>();
		for(DProduct fp : pageEntity.getResult()){
			FProductUtil fProductUtil  = new FProductUtil();
			BeanUtils.copyProperties(fp, fProductUtil);
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByDProductId(fp.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fpuList.add(fProductUtil);
		}
		model.addAttribute("list", fpuList);
		
		model.addAttribute("proxyId", id);
		return "/df/notshow_product_list";
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
	 * 批量添加好友可见商品
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
	 * 批量删除好友可见商品
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteProductToProxy")
	public Map<String,Object> deleteProductToProxy(Model model, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			UserUtil userUtil = super.getSessionUser(request.getSession());
			ShopUser dUser = shopUserService.findById(userUtil.getId());
			String ids = request.getParameter("ids");
			String proxyId = request.getParameter("proxyId");
			ShopUser proxyUser = shopUserService.findById(Long.valueOf(proxyId));
			fShowProductService.deleteProductToProxy(dUser,proxyUser,ids);
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
	public String deleteDProduct(@PathVariable("queryId") Long id,@PathVariable("fid") Long fid, HttpServletRequest request) {
		UserUtil userUtil = super.getSessionUser(request.getSession());
		fShowProductService.deleteById(userUtil.getId(), id);
		return "redirect:/df/user/getShowProject/"+fid;
	}
	/**
	 * 修改业务的编号
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateDesc")
	public Map<String,Object> updateDesc(Model model, HttpServletRequest request) throws Exception {
		String ywDesc = request.getParameter("ywDesc");
		String id = request.getParameter("id");
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			boolean flag = dFUserRelationService.updateYwDesc(ywDesc,Long.valueOf(id));
			if(flag){
				result.put("status", 1);
				result.put("msg", "修改成功。");
			}else{
				result.put("status", 0);
				result.put("msg", "修改失败。");
			}
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "修改失败。");
		}
		return result;
	}
	/**
	 * 好友申请同意
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "agreeApply")
	public Map<String,Object> agreeApply(Model model, HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			boolean flag = dFUserRelationService.agreeApply(Long.valueOf(id));
			if(flag){
				result.put("status", 1);
				result.put("msg", "修改成功。");
			}else{
				result.put("status", 0);
				result.put("msg", "修改失败。");
			}
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "修改失败。");
		}
		return result;
	}
}
