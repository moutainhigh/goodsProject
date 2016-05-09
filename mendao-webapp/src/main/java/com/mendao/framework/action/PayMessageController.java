package com.mendao.framework.action;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.PayMessage;
import com.mendao.business.service.PayMessageService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/pay/")
@SessionAttributes(types = UserUtil.class)
public class PayMessageController extends BaseController{
	
	@Autowired
	PayMessageService payMessageService; 
	
	@RequestMapping(value = "message")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<PayMessage> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("status", 1);
		pageEntity =  this.payMessageService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		return "message/list";
	}
	
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id) throws Exception {
		payMessageService.deleteById(id);
		return "redirect:/back/pay/message";
	}
}
