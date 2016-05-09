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

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.Role;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/role")
@SessionAttributes(types = UserUtil.class)
public class RoleController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<Role> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("status", 0);
		pageEntity =  this.roleService.findPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "role/role_list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("role",new Role());
		return "role/role_edit";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(Model model, HttpServletRequest request, @ModelAttribute Role role) {
		if(role.getId() != null && role.getId() > 0){
			Role updateRole = roleService.findById(role.getId());
			updateRole.setRoleName(role.getRoleName());
			role = roleService.add(updateRole);
		}else{
			role = roleService.add(role);
		}
		return "redirect:/back/role/list";
	}
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role",role);
		return "role/role_edit";
	}
	
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id) throws Exception {
		roleService.deleteById(id);
		return "redirect:/back/role/list";
	}
	
}
