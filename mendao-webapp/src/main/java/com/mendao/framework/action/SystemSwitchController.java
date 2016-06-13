package com.mendao.framework.action;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.SystemSwitch;
import com.mendao.business.service.SystemSwitchService;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/system/")
@SessionAttributes(types = UserUtil.class)
public class SystemSwitchController extends BaseController{
	@Autowired
	SystemSwitchService systemSwitchService;
	
	@RequestMapping(value = "switch")
	public String query(Model model, HttpServletRequest request) throws Exception {
		List<SystemSwitch> list = systemSwitchService.getAll();
		if(list.size() > 0){
			model.addAttribute("systemSwitch", list.get(0));
		}else{
			model.addAttribute("systemSwitch", new SystemSwitch());
		}
		return "system/switch";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request, @ModelAttribute SystemSwitch systemSwitch) {
		systemSwitch.setCreateDate(new Date());
		systemSwitch = systemSwitchService.save(systemSwitch);
		return "redirect:/back/system/switch";
	}
	
	
}
