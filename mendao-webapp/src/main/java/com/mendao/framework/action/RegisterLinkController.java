package com.mendao.framework.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.RegisterLink;
import com.mendao.business.service.RegisterLinkService;
import com.mendao.entity.util.UserRelationUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.UserRelation;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.repository.ShopUserRepository;
import com.mendao.framework.service.RecommendService;
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
	
	@Autowired
	RecommendService recommendService;
	
	@RequestMapping(value = "link")
	public String query(Model model, HttpServletRequest request) throws Exception {
		//获取当前登录的用户session
		UserUtil userUtil = super.getSessionUser(request.getSession());
		List<RegisterLink> rlList =  registerLinkService.getLinkByUserId(userUtil.getId());
		if(rlList.size() > 0){
			model.addAttribute("registerLink", rlList.get(0).getLinkUrl());
		}
		
		PageEntity<UserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("parent.id", userUtil.getId());
		pageEntity =  this.recommendService.getPage(pageEntity);
		List<UserRelationUtil> urList = new ArrayList<UserRelationUtil>();
		for(UserRelation ur:pageEntity.getResult()){
			UserRelationUtil userRelationUtil = new UserRelationUtil();
			BeanUtils.copyProperties(ur,userRelationUtil);
			int day = (int) (((new Date()).getTime()-ur.getCurrentUser().getCreateDate().getTime())/1000/60/60/24);
			if(day > 0){
				userRelationUtil.setUseDay(day);
			}else{
				userRelationUtil.setUseDay(0);
			}
			//用户名＊号替代
			int start = userRelationUtil.getCurrentUser().getUserName().length() / 3;
			int end = (userRelationUtil.getCurrentUser().getUserName().length() / 3)*2;
			if(start >0 && end > 0){
				StringBuffer re = new StringBuffer();
				for(int i=0;i<end-start;i++){
					re.append("*");
				}
				userRelationUtil.getCurrentUser().setUserName(userRelationUtil.getCurrentUser().getUserName().replaceAll(userRelationUtil.getCurrentUser().getUserName().substring(start, end), re.toString()));
			}
			urList.add(userRelationUtil);
		}
		model.addAttribute("urList", urList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
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
				rl.setLinkUrl(PropertiesUtil.getProperty("service.cdn")+"/register?uuid="+userUtil.getUuid());
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
