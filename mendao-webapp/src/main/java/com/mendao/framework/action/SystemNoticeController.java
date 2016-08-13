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

import com.mendao.business.entity.SystemNotice;
import com.mendao.business.service.SystemNoticeService;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/system/")
@SessionAttributes(types = UserUtil.class)
public class SystemNoticeController extends BaseController{
	@Autowired
	SystemNoticeService systemNoticeService;
	
	@RequestMapping(value = "notice")
	public String query(Model model, HttpServletRequest request) throws Exception {
		List<SystemNotice> list = systemNoticeService.getAll();
		if(list.size() > 0){
			model.addAttribute("systemNotice", list.get(0));
		}else{
			model.addAttribute("systemNotice", new SystemNotice());
		}
		return "system/notice";
	}
	
	@RequestMapping(value = "/noticeSave", method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request, @ModelAttribute SystemNotice systemNotice) {
		
		systemNotice = systemNoticeService.save(systemNotice);
		return "redirect:/back/system/notice";
	}
	
	
}
