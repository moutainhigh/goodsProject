package com.mendao.framework.show;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mendao.business.entity.Activity;
import com.mendao.business.entity.School;
import com.mendao.business.entity.Topic;
import com.mendao.business.service.ActivityService;
import com.mendao.business.service.SchoolService;
import com.mendao.business.service.TopicService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;

/**
 * 搜索Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("front/search")
@SuppressWarnings("unchecked")
public class SearchController extends BaseController{
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private SchoolService schoolService;
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String search(Model model, HttpServletRequest request) {
		String path = request.getHeader("Referer");
		String searchKeywords = request.getParameter("searchKeywords");
		if(path.indexOf("topic")>0){
			request.getSession().setAttribute("searchKeywords", searchKeywords);
			return "redirect:/front/search/topic";
		}else if(path.indexOf("activity") > 0){
			request.getSession().setAttribute("searchKeywords", searchKeywords);
			return "redirect:/front/search/activity";
		}else if(path.indexOf("school") > 0){
			request.getSession().setAttribute("searchKeywords", searchKeywords);
			return "redirect:/front/search/school";
		}else{
			request.getSession().setAttribute("searchKeywords", searchKeywords);
			return "redirect:/front/search/school";
		}
	}
	
	@RequestMapping(value = "/topic")
	public String topic(Model model, HttpServletRequest request) {
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request,15);
		String searchKeywords = (String) request.getSession().getAttribute("searchKeywords");
		if(searchKeywords != null){
			pageEntity.getParams().put("subject", searchKeywords);
		}
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		pageEntity = topicService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("searchKeywords", searchKeywords);
		request.getSession().removeAttribute("searchKeywords");
		
		return "front/search/topic";
	}
	
	@RequestMapping(value = "/activity")
	public String activity(Model model, HttpServletRequest request) {
		PageEntity<Activity> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 6);
		String searchKeywords = (String) request.getSession().getAttribute("searchKeywords");
		if(searchKeywords != null){
			pageEntity.getParams().put("name", searchKeywords);
		}
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		pageEntity = activityService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("searchKeywords", searchKeywords);
		request.getSession().removeAttribute("searchKeywords");
		
		return "front/search/activity";
	}
	
	@RequestMapping(value = "/school")
	public String school(Model model, HttpServletRequest request) {
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request,15);
		String searchKeywords = (String) request.getSession().getAttribute("searchKeywords");
		if(searchKeywords != null){
			pageEntity.getParams().put("schoolName", searchKeywords);
		}
		pageEntity = schoolService.getSchoolPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("searchKeywords", searchKeywords);
		request.getSession().removeAttribute("searchKeywords");
		
		return "front/search/school";
	}
}
