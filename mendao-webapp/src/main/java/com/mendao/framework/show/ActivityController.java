package com.mendao.framework.show;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Activity;
import com.mendao.business.entity.ActivityComment;
import com.mendao.business.entity.ActivitySticker;
import com.mendao.business.entity.ActivityUser;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.ActivityCommentService;
import com.mendao.business.service.ActivityService;
import com.mendao.business.service.ActivityStickerService;
import com.mendao.business.service.ActivityUserService;
import com.mendao.business.service.StickerService;
import com.mendao.entity.util.ActivityCommentUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;


/**
 * 活动Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("front")
@SuppressWarnings("unchecked")
public class ActivityController extends BaseController{
	
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
	/**
	 * 活动列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		PageEntity<Activity> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 6);
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		String orderType = request.getParameter("orderType");
		if(orderType != null && !orderType.equals("")){
			if(orderType.equals("1")){
				pageEntity.setOrderBy(" order by o.pubTime desc ");
			}else if(orderType.equals("2")){
				pageEntity.setOrderBy(" order by o.views desc ");
			}else if(orderType.equals("3")){
				pageEntity.setOrderBy(" order by o.startTime desc ");
			}
		}
		model.addAttribute("orderType", orderType);
		//根据标签搜索
		String sticker = request.getParameter("sticker");
		if(sticker != null && !sticker.equals("")){
			List<Long> idList = new ArrayList<Long>();
			List<ActivitySticker> asList = activityStickerService.findByStickerId(Long.valueOf(sticker));
			if(asList != null && asList.size() > 0){
				for(ActivitySticker as:asList){
					idList.add(as.getActivity().getId());
				}
			}
			if(idList.size() == 0){
				idList.add((long) 0);
			}
			pageEntity.getParams().put("id_in", idList);
		}
		model.addAttribute("sticker", sticker);
		pageEntity = activityService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		//获取活动标签
		List<Sticker> list = stickerService.getStickerByCode("ACTIVITY_STICKER");
		model.addAttribute("stickerList", list);
		
		return "front/activity/list";
	}
	/**
	 * 活动搜索（手机）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity/search")
	public String search(Model model, HttpServletRequest request) {
		
		PageEntity<Activity> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 6);
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		String orderType = request.getParameter("orderType");
		if(orderType != null && !orderType.equals("")){
			if(orderType.equals("1")){
				pageEntity.setOrderBy(" order by o.pubTime desc ");
			}else if(orderType.equals("2")){
				pageEntity.setOrderBy(" order by o.views desc ");
			}else if(orderType.equals("3")){
				pageEntity.setOrderBy(" order by o.startTime desc ");
			}
		}
		model.addAttribute("orderType", orderType);
		//根据标签搜索
		String sticker = request.getParameter("sticker");
		if(sticker != null && !sticker.equals("")){
			List<Long> idList = new ArrayList<Long>();
			List<ActivitySticker> asList = activityStickerService.findByStickerId(Long.valueOf(sticker));
			if(asList != null && asList.size() > 0){
				for(ActivitySticker as:asList){
					idList.add(as.getActivity().getId());
				}
			}
			if(idList.size() == 0){
				idList.add((long) 0);
			}
			pageEntity.getParams().put("id_in", idList);
		}
		model.addAttribute("sticker", sticker);
		pageEntity = activityService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		return "front/activity/search";
	}
	
	/**
	 * 活动详情
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/activity/detail/{id}", method = RequestMethod.GET)
	public String topicDetail(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取活动的信息
		Activity activity = activityService.findById(id);
		model.addAttribute("activity", activity);
		//检测用户是否登陆
		UserProfile userProfile = super.getSessionUser(request.getSession());
		if(userProfile != null){
			//检测用户是否已报名此活动
			List<ActivityUser> list = activityUserService.findByAttributes(userProfile.getUserInfo().getId(),id);
			if(list != null && list.size() > 0){
				model.addAttribute("joinStatus", 1);
			}else{
				model.addAttribute("joinStatus", 0);
			}
			model.addAttribute("loginStatus", 1);
		}else{
			model.addAttribute("loginStatus", 0);
		}
		//每访问一次话题的访问次数加一
		activity.setViews(activity.getViews() + 1);
		activityService.save(activity);
		return "front/activity/detail";
	}
	
	/**
	 * 提交评论
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/activity/submitComment")
	public String submitComment(Model model, HttpServletRequest request,@ModelAttribute ActivityComment activityComment) {
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			activityComment.setAuthor(userInfo);
			activityComment.setCreatedDate(new Date());
			activityComment.setReferLever(1);
			activityCommentService.save(activityComment);
			//评论成功之后将话题的评论数加一
			Activity activity = activityService.findById(activityComment.getActivity().getId());
			activity.setCommentNumber(activity.getCommentNumber() + 1);
			activityService.save(activity);
			
			return "redirect:/front/activity/detail/"+activityComment.getActivity().getId();
		}else{
			return "redirect:/login";
		}
	}
	/**
	 * 获取活动的评论
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/activity/activityComment/{id}", method = RequestMethod.GET)
	public String topicComment(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取该活动的评论
		PageEntity<ActivityComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("activity.id", id);
		pageEntity.getParams().put("referLever", 1);
		pageEntity = activityCommentService.getPage(pageEntity);
		//获取评论的子评论
		List<ActivityCommentUtil> utilList = new ArrayList<ActivityCommentUtil>();
		for(ActivityComment list:pageEntity.getResult()){
			ActivityCommentUtil commentUtil = new ActivityCommentUtil();
			commentUtil.setComment(list);
			List<ActivityComment> childList = activityCommentService.getCommentByParentId(list.getId());
			commentUtil.setChildComment(childList);
			utilList.add(commentUtil);
		}
		model.addAttribute("commentList", utilList);
		model.addAttribute("pageBean", pageEntity);
		return "front/activity/activity_comment";
	}
	/**
	 * 对活动评论的回复
	 * @param model
	 * @param request
	 * @param activityComment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/m/activity/submitChildCommit")
	public Map<String,Object> submitChildCommit(Model model, HttpServletRequest request,@ModelAttribute ActivityComment activityComment) {
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			activityComment.setAuthor(userInfo);
			activityComment.setCreatedDate(new Date());
			activityComment.setReferLever(2);
			activityCommentService.save(activityComment);
			//评论成功之后将话题的评论数加一
			Activity activity = activityService.findById(activityComment.getActivity().getId());
			activity.setCommentNumber(activity.getCommentNumber() + 1);
			activityService.save(activity);
			
			map.put("msg", 1);
		}else{
			map.put("msg", -1);
		}
		
		return map;
	}
	/**
	 * 获取该活动的参与的用户
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/activity/activityUser/{id}", method = RequestMethod.GET)
	public String activityUser(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		
		PageEntity<ActivityUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("activity.id", id);
		pageEntity = activityUserService.getPage(pageEntity);
		model.addAttribute("activityUser", pageEntity);
		return "front/activity/activity_user";
	}
	/**
	 * 参与活动
	 * @param model
	 * @param request
	 * @param activityUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/m/activity/joinActivity")
	public Map<String,Object> joinActivity(Model model, HttpServletRequest request,@ModelAttribute ActivityUser activityUser) {
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			List<ActivityUser> list = activityUserService.findByAttributes(userInfo.getId(),activityUser.getActivity().getId());
			if(list != null && list.size() > 0){
				map.put("msg", 3);
			}else{
				try{
					activityUser.setUser(userInfo);
					activityUser.setCreatedDate(new Date());
					activityUserService.save(activityUser);
					//参与成功之后将活动报名人数加一
					Activity activity = activityService.findById(activityUser.getActivity().getId());
					activity.setJoinNumber(activity.getJoinNumber() + 1);
					activityService.save(activity);
					map.put("msg", 1);
				}catch(Exception e){
					map.put("msg", -1);
				}
			}
		}else{
			map.put("msg", 2);
		}
		return map;
	}
	/**
	 * 我参与的活动
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/activity/myJoin", method = RequestMethod.GET)
	public String myJoin(Model model, HttpServletRequest request) {
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			PageEntity<ActivityUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 6);
			pageEntity.getParams().put("user.id", userInfo.getId());
			pageEntity = activityUserService.getPage(pageEntity);
			model.addAttribute("pageBean", pageEntity);
			model.addAttribute("userInfo", userInfo);
			ParamsUtil.addAttributeModle(model, pageEntity);
			return "front/activity/my_join";
		}else{
			return "redirect:/login";
		}
	}
}
