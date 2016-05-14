package com.mendao.framework.action;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.RegisterLink;
import com.mendao.business.service.RegisterLinkService;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.repository.ShopUserRepository;
import com.mendao.framework.show.BaseController;
import com.mendao.util.PropertiesUtil;

@Controller
@RequestMapping("/back/register")
@SessionAttributes(types = UserUtil.class)
public class RegisterLinkController extends BaseController {
	
	@Autowired
	RegisterLinkService registerLinkService;
	
	@Autowired
	ShopUserRepository shopUserRepository;
	
	@RequestMapping(value = "link")
	public String query(Model model, HttpServletRequest request) throws Exception {
		//获取当前登录的用户session
		UserUtil userUtil = super.getSessionUser(request.getSession());
		List<RegisterLink> rlList =  registerLinkService.getLinkByUserId(userUtil.getId());
		if(rlList.size() > 0){
			model.addAttribute("registerLink", rlList.get(0).getLinkUrl());
		}
		return "register_link";
	}
	@ResponseBody
	@RequestMapping(value = "create")
	public Map<String,Object> create(Model model, HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		//获取当前登录的用户session
		UserUtil userUtil = super.getSessionUser(request.getSession());
		if(userUtil.getId() > 0){
			try{
				PropertiesUtil.getProperty("service.cdn");
				RegisterLink rl = new RegisterLink();
				rl.setCreateDate(new Date());
				rl.setUser(shopUserRepository.findOne(userUtil.getId()));
				rl.setLinkUrl(PropertiesUtil.getProperty("service.cdn")+"?uuid="+userUtil.getUuid());
				registerLinkService.save(rl);
				result.put("status", 1);
				result.put("url", rl.getLinkUrl());
				result.put("msg", "生成成功");
			}catch(Exception e){
				result.put("status", 0);
				result.put("msg", "生成失败");
			}
		}else{
			result.put("status", 0);
			result.put("msg", "用户失效，请重新登录");
		}
		return result;
	}
}
