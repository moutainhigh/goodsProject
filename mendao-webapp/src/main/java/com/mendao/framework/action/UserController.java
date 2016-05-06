package com.mendao.framework.action;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.UserInfo;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/user")
@SessionAttributes(types = UserInfo.class)
public class UserController extends BaseController{
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		
		return "user/user_list";
	}
	
}
