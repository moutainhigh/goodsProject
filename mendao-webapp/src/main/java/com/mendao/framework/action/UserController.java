package com.mendao.framework.action;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.Role;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/user")
@SessionAttributes(types = UserUtil.class)
public class UserController extends BaseController{
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<ShopUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("status", 1);
		pageEntity =  this.shopUserService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
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
		String endDate = request.getParameter("endDateStr");
		
		shopUser.setCreateDate(new Date());
		shopUser.setEndDate(Timestamp.valueOf(endDate+" 23:59:59"));
		shopUser.setStatus(1);
		shopUser.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		shopUserService.addUser(shopUser);
		return "redirect:/back/user/list";
	}
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		ShopUser shopUser = shopUserService.findById(id);
		model.addAttribute("user",shopUser);
		
		List<Role> list = roleService.getAllRole();
		model.addAttribute("roleList", list);
		return "user/user_edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPost(Model model, HttpServletRequest request, @ModelAttribute ShopUser shopUser) {
		ShopUser updateUser = shopUserService.findById(shopUser.getId());
		
		String endDate = request.getParameter("endDateStr");
		
		updateUser.setUserName(shopUser.getUserName());
		updateUser.setNickName(shopUser.getNickName());
		updateUser.setEmail(shopUser.getEmail());
		updateUser.setPhone(shopUser.getPhone());
		updateUser.setEndDate(Timestamp.valueOf(endDate+" 23:59:59"));
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
}
