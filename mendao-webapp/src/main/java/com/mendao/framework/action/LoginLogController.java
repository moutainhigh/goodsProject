package com.mendao.framework.action;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.LoginLog;
import com.mendao.business.service.LoginLogService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/login")
@SessionAttributes(types = UserUtil.class)
public class LoginLogController extends BaseController {
	
	@Autowired
	LoginLogService loginLogService;
	
	
	@RequestMapping(value = "log")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<LoginLog> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity =  this.loginLogService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "login_list";
	}
	
}
