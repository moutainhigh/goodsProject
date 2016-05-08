package com.mendao.framework.action;



import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/pay/")
@SessionAttributes(types = UserUtil.class)
public class PayImageController extends BaseController{
	
	@RequestMapping(value = "image")
	public String query(Model model, HttpServletRequest request) throws Exception {
		
		
		return "pay/pay_image";
	}
}
