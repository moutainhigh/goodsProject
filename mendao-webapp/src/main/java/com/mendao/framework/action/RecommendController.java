package com.mendao.framework.action;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.Activity;
import com.mendao.business.entity.Category;
import com.mendao.business.entity.Recommend;
import com.mendao.business.entity.School;
import com.mendao.business.entity.Topic;
import com.mendao.business.service.ActivityService;
import com.mendao.business.service.CategoryService;
import com.mendao.business.service.RecommendService;
import com.mendao.business.service.SchoolService;
import com.mendao.business.service.TopicService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;
/**
 * 推荐Controller
 * @author warden
 *
 */

@Controller
@RequestMapping("/backend/recommend")
@SuppressWarnings("unchecked")
@SessionAttributes(types = Recommend.class)
public class RecommendController extends BaseController{
	

	@Autowired
	private RecommendService recommendService;

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 *  获取推荐列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String query(Model model, HttpServletRequest request) {
		PageEntity<Recommend> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity =  this.recommendService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/recommend/recommend_list";
	}
	/**
	 * 显示添加话题窗口
	 */
	@RequestMapping(value = "/showTopic", method = RequestMethod.GET)
	public String showTopic(Model model, HttpServletRequest request) {
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		List<Recommend> list = recommendService.getIdListByType(1);
		if(list != null && list.size() > 0){
			List<Long> idList = new ArrayList<Long>();
			for(Recommend l:list){
				idList.add(l.getTopic().getId());
			}
			pageEntity.getParams().put("id_notin", idList);
		}
		pageEntity =  this.topicService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/recommend/topic_add";
	}
	/**
	 * 话题推荐添加
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addTopic", method = RequestMethod.GET)
	public Map<String,Object> addTopic(Model model, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] ids = request.getParameterValues("id");
			List<Topic> topics = topicService.findListByIds(ids);
			if(topics != null && topics.size() > 0){
				for(Topic topic:topics){
					Recommend recommend = new Recommend();
					recommend.setTopic(topic);
					recommend.setType(1);
					recommend.setStatus(1);
					recommend.setCreatedDate(new Date());
					recommendService.save(recommend);
				}
			}
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", 0);
		}
		return map;
	}
	/**
	 * 显示添加活动窗口
	 */
	@RequestMapping(value = "/showActivity", method = RequestMethod.GET)
	public String showActivity(Model model, HttpServletRequest request) {
		PageEntity<Activity> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		List<Recommend> list = recommendService.getIdListByType(2);
		if(list != null && list.size() > 0){
			List<Long> idList = new ArrayList<Long>();
			for(Recommend l:list){
				idList.add(l.getActivity().getId());
			}
			pageEntity.getParams().put("id_notin", idList);
		}
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		pageEntity =  this.activityService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/recommend/activity_add";
	}
	/**
	 * 活动推荐添加
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addActivity", method = RequestMethod.GET)
	public Map<String,Object> addActivity(Model model, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] ids = request.getParameterValues("id");
			List<Activity> activitys = activityService.findListByIds(ids);
			if(activitys != null && activitys.size() > 0){
				for(Activity activity:activitys){
					Recommend recommend = new Recommend();
					recommend.setActivity(activity);
					recommend.setType(2);
					recommend.setStatus(1);
					recommend.setCreatedDate(new Date());
					recommendService.save(recommend);
				}
			}
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", 0);
		}
		return map;
	}
	/**
	 * 显示推荐机构
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showSchool", method = RequestMethod.GET)
	public String showSchool(Model model, HttpServletRequest request) {
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		List<Recommend> list = recommendService.getIdListByType(3);
		if(list != null && list.size() > 0){
			List<Long> idList = new ArrayList<Long>();
			for(Recommend l:list){
				idList.add(l.getSchool().getId());
			}
			pageEntity.getParams().put("id_notin", idList);
		}
		pageEntity =  this.schoolService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/recommend/school_add";
	}
	/**
	 * 增加热门推荐机构
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSchool", method = RequestMethod.GET)
	public Map<String,Object> addSchool(Model model, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] ids = request.getParameterValues("id");
			List<School> schools = schoolService.findListByIds(ids);
			if(schools != null && schools.size() > 0){
				for(School school:schools){
					Recommend recommend = new Recommend();
					recommend.setSchool(school);
					recommend.setType(3);
					recommend.setStatus(1);
					recommend.setCreatedDate(new Date());
					recommendService.save(recommend);
				}
			}
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", 0);
		}
		return map;
	}
	/**
	 * 显示推荐机构
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAllSchool", method = RequestMethod.GET)
	public String showAllSchool(Model model, HttpServletRequest request) {
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity =  this.schoolService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		//获取机构分类列表
		List<Category> categoryList = categoryService.getListByAttributes("STICKER",1,"SCHOOL_STICKER");
		model.addAttribute("categoryList", categoryList);
		return "backend/recommend/school_cat_add";
	}
	/**
	 * 增加分类下的机构推荐
	 * @param model
	 * @param request
	 * @param recommend
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addCatSchool")
	public Map<String,Object> addCatSchool(Model model, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String schoolId = request.getParameter("school.id");
			String categoryId = request.getParameter("category.id");
			Recommend recommend = new Recommend();
			recommend.setSchool(schoolService.findOne(Long.valueOf(schoolId)));
			recommend.setCategory(categoryService.findOne(Long.valueOf(categoryId)));
			recommend.setType(4);
			recommend.setStatus(1);
			recommend.setCreatedDate(new Date());
			recommendService.save(recommend);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", 0);
		}
		return map;
	}
	/**
	 * 推荐删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) throws Exception {
	   String[] ids = request.getParameterValues("id");
	   List<Recommend> recommends = recommendService.findListByIds(ids);
	   if(recommends != null && recommends.size() > 0){
		   recommendService.deleteAll(recommends);
	   }
	   return "redirect:/backend/recommend/list";
	}
}
