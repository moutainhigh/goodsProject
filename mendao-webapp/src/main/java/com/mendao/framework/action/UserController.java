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

import com.mendao.entity.util.ShopUserUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.Role;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;
import com.mendao.util.EncryptService;

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
	public String edit(@PathVariable("queryId") Long id, Model model) {
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
		shopUserService.updateUser(updateUser);
		return "redirect:/back/user/list";
	}
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id) throws Exception {
		shopUserService.deleteById(id);
		return "redirect:/back/user/list";
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
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = getDateAfter(new Date(),Integer.valueOf(day));
			shopUserService.changeEndDate(ids,format.format(endDate)+" 23:59:59");
			result.put("status", 1);
			result.put("msg", "设置成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "设置失败");
		}
		return result;
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
