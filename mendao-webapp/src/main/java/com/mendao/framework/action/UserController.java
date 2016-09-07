package com.mendao.framework.action;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.mendao.business.entity.LoginLog;
import com.mendao.business.entity.PKind;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.entity.RegisterLink;
import com.mendao.business.entity.ShopMessage;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.LoginLogService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.business.service.ShopMessageService;
import com.mendao.entity.util.FProductUtil;
import com.mendao.entity.util.QRCode;
import com.mendao.entity.util.ShopUserUtil;
import com.mendao.entity.util.UserRelationUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.Role;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.entity.UserRelation;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.RecommendService;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;
import com.mendao.util.EncryptService;
import com.mendao.util.PropertiesUtil;

@Controller
@RequestMapping("/back/user")
@SessionAttributes(types = UserUtil.class)
public class UserController extends BaseController{
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	RoleService roleService;
	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	RecommendService recommendService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductPicService productPicService;
	
	@Autowired
	DFUserRelationService dFUserRelationService;
	
	@Autowired
	ShopMessageService shopMessageService;
	
	@Autowired
	LoginLogService loginLogService;
	
	private static String requestUrl = null;
	
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<ShopUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("status", 1);
		pageEntity =  this.shopUserService.getPage(pageEntity);
		
		List<ShopUserUtil> list = new ArrayList<ShopUserUtil>();
		for(ShopUser user:pageEntity.getResult()){
			ShopUserUtil suu =new ShopUserUtil();
			BeanUtils.copyProperties(user, suu);
			//获取用户已到期时间XXX天
			int day = (int) ((user.getEndDate().getTime()-(new Date()).getTime())/1000/60/60/24);
			if(day > 0){
				suu.setSurplusDay(day);
			}else{
				suu.setSurplusDay(0);
			}
			suu.setPassword(encryptService.decrypt(user.getPassword()));
			list.add(suu);
		}
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("list", list);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "user/user_list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model){
		List<Role> list = roleService.getAllRole();
		model.addAttribute("roleList", list);
		return "user/user_add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(Model model, HttpServletRequest request, @ModelAttribute ShopUser shopUser) {
		String endDateStr = request.getParameter("endDateStr");
		//计算出用户的有效时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = getDateAfter(new Date(),Integer.valueOf(endDateStr));
				
		shopUser.setCreateDate(new Date());
		shopUser.setEndDate(Timestamp.valueOf(format.format(endDate)+" 23:59:59"));
		shopUser.setStatus(1);
		shopUser.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		shopUser.setPassword(encryptService.encrypt(shopUser.getPassword()));
		shopUserService.addUser(shopUser);
		return "redirect:/back/user/list";
	}
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) {
		ShopUser shopUser = shopUserService.findById(id);
		ShopUserUtil suu =new ShopUserUtil();
		BeanUtils.copyProperties(shopUser, suu);
		//获取用户已到期时间XXX天
		int day = (int) ((shopUser.getEndDate().getTime()-(new Date()).getTime())/1000/60/60/24);
		if(day > 0){
			suu.setSurplusDay(day);
		}else{
			suu.setSurplusDay(0);
		}
		
		model.addAttribute("user",suu);
		
		List<Role> list = roleService.getAllRole();
		model.addAttribute("roleList", list);
		
		String requestUrl = request.getHeader("Referer");  
		model.addAttribute("requestUrl", requestUrl);
		
		return "user/user_edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPost(Model model, HttpServletRequest request, @ModelAttribute ShopUser shopUser) {
		ShopUser updateUser = shopUserService.findById(shopUser.getId());
		String endDateStr = request.getParameter("endDateStr");
		//计算出用户的有效时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = getDateAfter(new Date(),Integer.valueOf(endDateStr));
		
		updateUser.setUserName(shopUser.getUserName());
		updateUser.setNickName(shopUser.getNickName());
		updateUser.setEmail(shopUser.getEmail());
		updateUser.setPhone(shopUser.getPhone());
		updateUser.setEndDate(Timestamp.valueOf(format.format(endDate)+" 23:59:59"));
		updateUser.setRemark(shopUser.getRemark());
		updateUser.setFriendNum(shopUser.getFriendNum());
		updateUser.setRole(shopUser.getRole());
		
		shopUserService.updateUser(updateUser);
		String requestUrl = request.getParameter("requestUrl");
		if(requestUrl != null){
			return "redirect:"+requestUrl;
		}else{
			return "redirect:/back/user/list";
		}
	}
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(HttpServletRequest request,@PathVariable("queryId") Long id) throws Exception {
		shopUserService.deleteById(id);
		String requestUrl = request.getHeader("Referer");  
		if(requestUrl != null){
			return "redirect:"+requestUrl;
		}else{
			return "redirect:/back/user/list";
		}
	}
	@RequestMapping(value = "/resetPassword/{queryId}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable("queryId") Long id) throws Exception {
		shopUserService.resetPasswordById(id,"111111");
		return "redirect:/back/user/list";
	}
	@ResponseBody
	@RequestMapping(value = "/changeEndDate", method = RequestMethod.POST)
	public Map<String,Object> changeEndDate(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String ids = request.getParameter("ids");
			String day = request.getParameter("day");
			
			shopUserService.changeNewEndDate(ids,day);
			result.put("status", 1);
			result.put("msg", "设置成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "设置失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/down/{queryId}", method = RequestMethod.GET)
	public String down(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) {
		
		PageEntity<UserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("parent.id", id);
		pageEntity =  this.recommendService.getPage(pageEntity);
		List<UserRelationUtil> urList = new ArrayList<UserRelationUtil>();
		for(UserRelation ur:pageEntity.getResult()){
			UserRelationUtil userRelationUtil = new UserRelationUtil();
			BeanUtils.copyProperties(ur,userRelationUtil);
			int day = (int) (((new Date()).getTime()-ur.getCurrentUser().getCreateDate().getTime())/1000/60/60/24);
			if(day > 0){
				userRelationUtil.setUseDay(day);
			}else{
				userRelationUtil.setUseDay(0);
			}
			//用户名＊号替代
			int start = userRelationUtil.getCurrentUser().getUserName().length() / 3;
			int end = (userRelationUtil.getCurrentUser().getUserName().length() / 3)*2;
			if(start >0 && end > 0){
				StringBuffer re = new StringBuffer();
				for(int i=0;i<end-start;i++){
					re.append("*");
				}
				userRelationUtil.getCurrentUser().setUserName(userRelationUtil.getCurrentUser().getUserName().replaceAll(userRelationUtil.getCurrentUser().getUserName().substring(start, end), re.toString()));
			}
			urList.add(userRelationUtil);
		}
		model.addAttribute("urList", urList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		String url = request.getHeader("Referer");  
		if(url != null && url.indexOf("down") < 0){
			requestUrl = url;
		}
		model.addAttribute("requestUrl", requestUrl);
		return "user/down_list";
	}
	
	@RequestMapping(value = "/upuser/{queryId}", method = RequestMethod.GET)
	public String upuser(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) {
		
		PageEntity<UserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("currentUser.id", id);
		pageEntity =  this.recommendService.getPage(pageEntity);
		List<UserRelationUtil> urList = new ArrayList<UserRelationUtil>();
		for(UserRelation ur:pageEntity.getResult()){
			UserRelationUtil userRelationUtil = new UserRelationUtil();
			BeanUtils.copyProperties(ur,userRelationUtil);
			int day = (int) (((new Date()).getTime()-ur.getParent().getCreateDate().getTime())/1000/60/60/24);
			if(day > 0){
				userRelationUtil.setUseDay(day);
			}else{
				userRelationUtil.setUseDay(0);
			}
			//用户名＊号替代
			int start = userRelationUtil.getCurrentUser().getUserName().length() / 3;
			int end = (userRelationUtil.getCurrentUser().getUserName().length() / 3)*2;
			if(start >0 && end > 0){
				StringBuffer re = new StringBuffer();
				for(int i=0;i<end-start;i++){
					re.append("*");
				}
				userRelationUtil.getCurrentUser().setUserName(userRelationUtil.getCurrentUser().getUserName().replaceAll(userRelationUtil.getCurrentUser().getUserName().substring(start, end), re.toString()));
			}
			urList.add(userRelationUtil);
		}
		model.addAttribute("urList", urList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		String url = request.getHeader("Referer");  
		if(url != null && url.indexOf("down") < 0){
			requestUrl = url;
		}
		model.addAttribute("requestUrl", requestUrl);
		return "user/upuser_list";
	}
	@RequestMapping(value = "/upProduct/{queryId}/{type}", method = RequestMethod.GET)
	public String upProduct(@PathVariable("queryId") Long id,@PathVariable("type") Long type, Model model, HttpServletRequest request) {
		if(type == 2){
			PageEntity<DProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", 1);
			params.put("deleteFlag", 0);
			params.put("createUserId", shopUserService.findById(id));
			pageEntity.setParams(params);
			List<PKind> kindList = productService.queryAllPropertiesByCreateId(id);
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
//					String ids = product.getKindId();
//					if(null != ids){
//						String[] kindIds = ids.split(",");
//						StringBuffer sb = new StringBuffer();
//						for (int i = 0; i < kindIds.length; i++){
//							sb.append(kindMap.get(Long.parseLong(kindIds[i]))).append(",");
//						}
//						sb.setLength(sb.length() - 1);
//						product.setComment(sb.toString());
//					}
					
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
					dProductList.add(fProductUtil);
				}
			}
			
			
			model.addAttribute("pageBean", pageEntity);
			model.addAttribute("puList", dProductList);
			ParamsUtil.addAttributeModle(model, pageEntity);
			String url = request.getHeader("Referer");  
			if(url != null && url.indexOf("upProduct") < 0){
				requestUrl = url;
			}
			model.addAttribute("requestUrl", requestUrl);
			return "user/dproduct_list";
		}else{
			PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("onSale", 1);
			params.put("deleteFlag", 0);
			params.put("modifyUserId", shopUserService.findById(id));
			params.put("dProduct.deleteFlag", 0);
			params.put("dProduct.status", 1);
			params.put("createUserId.endDate_s", new Date());
			pageEntity.setParams(params);
			List<ShopUser> dailiList = this.productService.getAllDaiLiByCurrentUserId(id);
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
			model.addAttribute("puList", fpuList);
			model.addAttribute("pageBean", pageEntity);
			ParamsUtil.addAttributeModle(model, pageEntity);
			String url = request.getHeader("Referer");  
			if(url != null && url.indexOf("upProduct") < 0){
				requestUrl = url;
			}
			model.addAttribute("requestUrl", requestUrl);
			return "user/fproduct_list";
		}
	}
	
	@RequestMapping(value = "/showshop/{queryId}", method = RequestMethod.GET)
	public String showshop(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) {
		try {
			ShopMessage shopMessage = shopMessageService.findByUserId(id);
			//如果店铺链接不存在，自动生成
			if(shopMessage == null){
				ShopMessage sm = new ShopMessage();
				sm.setUser(shopUserService.findById(id));
				sm.setShopUrl(PropertiesUtil.getProperty("service.cdn")+"/f/"+id);
				
					sm.setQrcodeUrl(PropertiesUtil.getProperty("service.cdn")+QRCode.createQRcode(sm.getShopUrl()));
				
				sm.setShopPwd("111111");
				sm.setCreateDate(new Date());
				sm = shopMessageService.save(sm);
				model.addAttribute("shopMessage", sm);
			}else{
				model.addAttribute("shopMessage", shopMessage);
			}
			String url = request.getHeader("Referer");  
			model.addAttribute("requestUrl", url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/user/shop";
	}
	
	@RequestMapping(value = "/loginlog/{queryId}", method = RequestMethod.GET)
	public String loginlog(@PathVariable("queryId") Long id, Model model, HttpServletRequest request) {
		PageEntity<LoginLog> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", shopUserService.findById(id));
		pageEntity.setParams(params);
		pageEntity =  this.loginLogService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		String url = request.getHeader("Referer");  
		model.addAttribute("requestUrl", url);
		return "/user/loginlog";
	}
	
	@RequestMapping(value = "/mymessage", method = RequestMethod.GET)
	public String loginlog(Model model, HttpServletRequest request) {
		ShopUser user = shopUserService.findById(super.getSessionUser(request.getSession()).getId());
		if(user.getPhone() == null){
			user.setPhone("无");
		}
		model.addAttribute("user", user);
		return "/mymessage";
	}
	
	 /** 
	   * 得到几天后的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	public static Date getDateAfter(Date d,int day){  
		Calendar now =Calendar.getInstance();  
		now.setTime(d);  
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
		return now.getTime();  
	}  
}
