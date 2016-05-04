package com.mendao.framework.action;



import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mendao.business.entity.Activity;
import com.mendao.business.entity.ActivitySticker;
import com.mendao.business.entity.ActivityUser;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.ActivityCommentService;
import com.mendao.business.service.ActivityService;
import com.mendao.business.service.ActivityStickerService;
import com.mendao.business.service.ActivityUserService;
import com.mendao.business.service.StickerService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;
import com.mendao.util.PropertiesUtil;


/**
 * 活动Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("/backend/activity")
@SuppressWarnings("unchecked")
public class BackendActivityController extends BaseController{
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private StickerService stickerService;
	
	@Autowired
	private ActivityCommentService activityCommentService;
	
	@Autowired
	private ActivityUserService activityUserService;
	
	@Autowired
	private ActivityStickerService activityStickerService; 
	
	private String UPLOAD_PATH = PropertiesUtil.getProperty("service.upload_path");
	
	/**
	 * 我的活动列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		PageEntity<Activity> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("status", 0);
		pageEntity = activityService.getPage(pageEntity);
		
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		return "backend/activity/activity_list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute(new Activity());
		//获取活动标签
		List<Sticker> list = stickerService.getStickerByCode("ACTIVITY_STICKER");
		model.addAttribute("stickerList", list);
		return "backend/activity/activity_add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(Model model, HttpServletRequest request, @ModelAttribute Activity activity) {
//		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
//		if(userInfo != null){
//			String stratTime = request.getParameter("startTimeStr");
//			String endTime = request.getParameter("endTimeStr");
//			//如果活动为新增活动，设置活动的发布时间
//			if(activity.getId() == null || activity.getId() <= 0){
//				activity.setPubTime(new Date());
//			}
//			activity.setStartTime(Timestamp.valueOf(stratTime+":00"));
//			activity.setEndTime(Timestamp.valueOf(endTime+":00"));
//			activity.setAuthor(userInfo);
//			//activity.setSticker(stickerValue.toString());
//			//默认发布的活动为已通过审核的
//			activity.setExamined(1);
//			
//			//********************************************
//			//** 将临时图片文件移入指定文件夹
//			//********************************************
//			activity.setActivityImage(moveImageToRealPath(activity.getActivityImage()));
//			activity.setContent(moveContentImageToRealPath(activity.getContent()));
//			
//			
//			activityService.save(activity);
//			//删除活动标签
//			activityStickerService.deleteByActivityId(activity.getId());
//			//获取活动选择的标签
//			String stickerString = request.getParameter("sticker");
//			if(stickerString != null && !stickerString.equals("")){
//				String[] sticker = stickerString.split(",");
//				if(sticker != null){
//					for(int i=0; i<sticker.length; i++){
//						ActivitySticker as = new ActivitySticker();
//						as.setActivity(activity);
//						as.setSticker(stickerService.findById(Long.valueOf(sticker[i])));
//						as.setCreatedTime(new Date());
//						activityStickerService.save(as);
//					}
//				}
//				
//			}
//			
//			return "redirect:/backend/activity/list";
//		}else{
//			return "/login";
//		}
		return null;
	}
	
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		Activity activity = activityService.findById(id);
		model.addAttribute("activity",activity);
		//获取活动标签
		List<Sticker> list = stickerService.getStickerByCode("ACTIVITY_STICKER");
		model.addAttribute("stickerList", list);
		
		//获取活动选择的标签
		List<ActivitySticker> asList = activityStickerService.findByActivityId(id);
		StringBuffer activityStickerString = new StringBuffer();
		if(asList != null && asList.size() > 0){
			for(ActivitySticker as:asList){
				activityStickerString.append(as.getSticker().getId()).append(",");
			}
		}
		model.addAttribute("activitySticker", activityStickerString.toString());
		return "backend/activity/activity_edit";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) throws Exception {
	   String[] ids = request.getParameterValues("id");
	   List<Activity> activitys = activityService.findListByIds(ids);
	   if(activitys != null && activitys.size() > 0){
		   for(Activity activity:activitys){
			   activity.setStatus(-1);
			   activityService.save(activity);
		   }
	   }
	   return "redirect:/backend/activity/list";
	}
	@RequestMapping(value = "/activityUser/{queryId}", method = RequestMethod.GET)
	public String activityUser(Model model, HttpServletRequest request,@PathVariable("queryId") Long id) throws Exception {
		PageEntity<ActivityUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("activity.id", id);
		pageEntity = activityUserService.getPage(pageEntity);
		
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "/backend/activity/activity_user";
	}
}
