package com.mendao.framework.action;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.PayImage;
import com.mendao.business.entity.PayMessage;
import com.mendao.business.service.PayImageService;
import com.mendao.business.service.PayMessageService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/pay/")
@SessionAttributes(types = UserUtil.class)
public class PayMessageController extends BaseController{
	
	@Autowired
	PayMessageService payMessageService; 
	
	@Autowired
	PayImageService payImageService;
	
	@Autowired
	ShopUserService shopUserService;
	
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
	/**
	 * 付款页面
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "agentPay")
	public String agentPay(Model model, HttpServletRequest request) throws Exception {
		List<PayImage> list = payImageService.getAll();
		if(list.size() > 0){
			model.addAttribute("payImage", list.get(0));
		}else{
			model.addAttribute("payImage", new PayImage());
		}
		return "message/agent_pay";
	}
	/**
	 * 保存付款留言
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "savePayMessage", method = RequestMethod.POST)
	public Map<String,Object> savePayMessage(Model model, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		String message = request.getParameter("message");
		//获取当前登录的用户session
		UserUtil userUtil = super.getSessionUser(request.getSession());
		if(message != null && userUtil.getId()>0){
			try{
				PayMessage payMessage = new PayMessage();
				payMessage.setContent(message);
				payMessage.setCreateDate(new Date());
				payMessage.setStatus(1);
				payMessage.setUser(shopUserService.findById(userUtil.getId()));
				payMessageService.saveMessage(payMessage);
				result.put("status", 1);
				result.put("msg", "保存成功");
			}catch(Exception e){
				result.put("status", 0);
				result.put("msg", "保存失败");
			}
		}else{
			result.put("status", 0);
			result.put("msg", "提交信息不全");
		}
		return result;
	}
}
