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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Advert;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.Topic;
import com.mendao.business.entity.TopicCollection;
import com.mendao.business.entity.TopicComment;
import com.mendao.business.entity.TopicSticker;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.AdvertService;
import com.mendao.business.service.StickerService;
import com.mendao.business.service.TopicCollectionService;
import com.mendao.business.service.TopicCommentService;
import com.mendao.business.service.TopicService;
import com.mendao.business.service.TopicStickerService;
import com.mendao.common.util.StringUtil;
import com.mendao.entity.util.TopicCommentUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.util.ContentUtil;
import com.mendao.util.MobileUtil;
import com.mendao.util.PropertiesUtil;

/**
 * 登录Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("front")
@SuppressWarnings("unchecked")
public class TopicController extends BaseController{
	
	private final String CDN = PropertiesUtil.getProperty("service.cdn");
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private StickerService stickerService;
	
	@Autowired
	private TopicCommentService topicCommentService;
	
	@Autowired
	private TopicCollectionService topicCollectionService;
	
	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private TopicStickerService topicStickerService;
	/**
	 * 获取活动列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/topic/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		
		String orderType = StringUtil.defaultIfBlank(request.getParameter("orderType"));
		String sticker = request.getParameter("sticker");
		pageEntity.getParams().put("sticker", Long.valueOf(StringUtil.defaultIfBlank(sticker, "0")));
		switch (orderType) {
		case "1":
			pageEntity.setOrderBy(" order by t.create_date desc ");
			break;
		case "2":
			pageEntity.setOrderBy(" order by t.views desc ");
			break;
		default:
			pageEntity.getParams().put("keywords", StringUtil.defaultIfBlank(request.getParameter("q_subject")));
			String s = StringUtil.defaultIfBlank(sticker, "0");
			pageEntity.getParams().put("sticker", Long.valueOf(s));
			pageEntity.setOrderBy("");
			break;
		}
		pageEntity.setProcedure("{call search_topics(?,?,?,?)}");
		//根据标签搜索
//		String sticker = request.getParameter("sticker");
//		if(sticker != null && !sticker.equals("")){
//			List<Long> idList = new ArrayList<Long>();
//			List<TopicSticker> tsList = topicStickerService.findByStickerId(Long.valueOf(sticker));
//			if(tsList != null && tsList.size() > 0){
//				for(TopicSticker ts:tsList){
//					idList.add(ts.getTopic().getId());
//				}
//			}
//			if(idList.size() == 0){
//				idList.add((long) 0);
//			}
//			pageEntity.getParams().put("id_in", idList);
//		}
		model.addAttribute("orderType", orderType);
		model.addAttribute("sticker", sticker);
		pageEntity = topicService.getPageUsingProcedure(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		//获取话题标签
		List<Sticker> list = stickerService.getStickerByCode("TOPIC_STICKER");
		model.addAttribute("stickerList", list);
		
		//获取广告
		List<Advert> advertList = advertService.getListByPlace("TOPIC_LIST");
		model.addAttribute("advertList",advertList);
		return "front/topic/list";
	}
	/**
	 * 话题搜索结果页面（手机）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/topic/search", method = RequestMethod.POST)
	public String search(Model model, HttpServletRequest request) {
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
		
		String orderType = StringUtil.defaultIfBlank(request.getParameter("orderType"));
		String sticker = request.getParameter("sticker");
		pageEntity.getParams().put("sticker", Long.valueOf(StringUtil.defaultIfBlank(sticker, "0")));
		switch (orderType) {
		case "1":
			pageEntity.setOrderBy(" order by t.create_date desc ");
			break;
		case "2":
			pageEntity.setOrderBy(" order by t.views desc ");
			break;
		default:
			pageEntity.getParams().put("keywords", StringUtil.defaultIfBlank(request.getParameter("keywords")));
			String s = StringUtil.defaultIfBlank(sticker, "0");
			pageEntity.getParams().put("sticker", Long.valueOf(s));
			pageEntity.setOrderBy("");
			break;
		}
		pageEntity.setProcedure("{call search_topics(?,?,?,?)}");
		
		model.addAttribute("orderType", orderType);
		model.addAttribute("sticker", sticker);
		pageEntity = topicService.getPageUsingProcedure(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		return "front/topic/search";
	}
	/**
	 * 获取活动详情
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/topic/topicDetail/{id}", method = RequestMethod.GET)
	public String topicDetail(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取活动的信息
		Topic topic = topicService.findById(id);
		model.addAttribute("topic", topic);
		//检测用户是否收藏此话题
		UserProfile userProfile = super.getSessionUser(request.getSession());
		if(userProfile != null){
			List<TopicCollection> list = topicCollectionService.findByAttributes(userProfile.getUserInfo().getId(),topic.getId());
			if(list.size() >0){
				model.addAttribute("collectionStatus", 0);
			}else{
				model.addAttribute("collectionStatus", 1);
			}
			model.addAttribute("loginStatus", 1);
		}else{
			model.addAttribute("collectionStatus", 1);
			model.addAttribute("loginStatus", 0);
		}
		//获取相关话题
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("topicId", topic.getId());
		List<Topic> list = topicService.findRelationTopic(pageEntity);
		model.addAttribute("list",list);
		
		//每访问一次话题的访问次数加一
		topic.setViews(topic.getViews() + 1);
		topicService.save(topic);
		
		//获取广告
		List<Advert> advertList = advertService.getListByPlace("TOPIC_DETAIL");
		model.addAttribute("advertList",advertList);
		return "front/topic/detail";
	}
	
	@RequestMapping(value = "/topic/topicComment/{id}", method = RequestMethod.GET)
	public String topicComment(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取该活动的评论
		PageEntity<TopicComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("topic.id", id);
		pageEntity.getParams().put("referLever", 1);
		pageEntity = topicCommentService.getPage(pageEntity);
		//获取评论的子评论
		List<TopicCommentUtil> utilList = new ArrayList<TopicCommentUtil>();
		for(TopicComment list:pageEntity.getResult()){
			TopicCommentUtil commentUtil = new TopicCommentUtil();
			commentUtil.setComment(list);
			List<TopicComment> childList = topicCommentService.getCommentByParentId(list.getId());
			commentUtil.setChildComment(childList);
			utilList.add(commentUtil);
		}
		model.addAttribute("commentList", utilList);
		model.addAttribute("pageBean", pageEntity);
		return "front/topic/topic_comment";
	}
	/**
	 * 话题点赞
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/topic/topicUps")
	public Map<String,Object> topicUps(Model model, HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
		Topic topic = topicService.findById(id);
		topic.setUps(topic.getUps()+1);
		topicService.save(topic);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("up",topic.getUps());
		map.put("msg", 1);
		return map;
	}
	/**
	 * 用户收藏话题
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/m/topic/topicCollection")
	public Map<String,Object> topicCollection(Model model, HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
		Map<String,Object> map = new HashMap<String, Object>();
		//检测用户是否登陆
		UserProfile userProfile = super.getSessionUser(request.getSession());
		if(userProfile != null){
			try{
				TopicCollection tc = new TopicCollection();
				tc.setTopic(topicService.findById(id));
				tc.setAuthor(userProfile.getUserInfo());
				tc.setStatus(0);
				tc.setCreatedDate(new Date());
				topicCollectionService.save(tc);
				map.put("msg", 1);
			}catch(Exception e){
				map.put("msg", 0);
			}
		}else{
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 提交评论
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/submitComment")
	public String submitComment(Model model, HttpServletRequest request,@ModelAttribute TopicComment topicComment) {
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			topicComment.setAuthor(userInfo);
			topicComment.setCreatedDate(new Date());
			topicComment.setReferLever(1);
			topicCommentService.save(topicComment);
			//评论成功之后将话题的评论数加一
			Topic topic = topicService.findById(topicComment.getTopic().getId());
			topic.setCommentNumber(topic.getCommentNumber() + 1);
			topicService.save(topic);
			
			return "redirect:/front/topic/topicDetail/"+topicComment.getTopic().getId();
		}else{
			return "redirect:/login";
		}
	}
	/**
	 * 提交对评论的回复
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/m/topic/submitChildCommit")
	public Map<String,Object> submitChildCommit(Model model, HttpServletRequest request,@ModelAttribute TopicComment topicComment) {
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前登陆的用户
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			topicComment.setAuthor(userInfo);
			topicComment.setCreatedDate(new Date());
			topicComment.setReferLever(2);
			topicCommentService.save(topicComment);
			//评论成功之后将话题的评论数加一
			Topic topic = topicService.findById(topicComment.getTopic().getId());
			topic.setCommentNumber(topic.getCommentNumber() + 1);
			topicService.save(topic);
			
			map.put("msg", 1);
		}else{
			map.put("msg", -1);
		}
		
		return map;
	}
	
	/**
	 * 获取登陆用户发布的话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/myTopic", method = RequestMethod.GET)
	public String myTopic(Model model, HttpServletRequest request) {
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("author.id", super.getSessionUser(request.getSession()).getUserInfo().getId());
		pageEntity.getParams().put("examined", 1);
		pageEntity.getParams().put("status", 0);
		pageEntity = topicService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		model.addAttribute("LoginAccount", super.getSessionUser(request.getSession()));
		
		//获取广告
		List<Advert> advertList = advertService.getListByPlace("MY_TOPIC");
		model.addAttribute("advertList",advertList);
		
		return "front/topic/my_topic";
	}
	/**
	 * 获取登录用户参与的话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/myJoinTopic", method = RequestMethod.GET)
	public String myJoinTopic(Model model, HttpServletRequest request) {
		PageEntity<TopicComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("author.id", super.getSessionUser(request.getSession()).getUserInfo().getId());
		pageEntity.setGroupBy(" group by topic.id ");
		pageEntity = topicCommentService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		model.addAttribute("LoginAccount", super.getSessionUser(request.getSession()));
		//获取广告
		List<Advert> advertList = advertService.getListByPlace("MY_TOPIC");
		model.addAttribute("advertList",advertList);
		
		return "front/topic/my_join_topic";
	}
	/**
	 * 获取登录用户收藏的话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/myCollection", method = RequestMethod.GET)
	public String myCollection(Model model, HttpServletRequest request) {
		PageEntity<TopicCollection> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
		pageEntity.getParams().put("author.id", super.getSessionUser(request.getSession()).getUserInfo().getId());
		pageEntity = topicCollectionService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		model.addAttribute("LoginAccount", super.getSessionUser(request.getSession()));
		
		//获取广告
		List<Advert> advertList = advertService.getListByPlace("MY_TOPIC");
		model.addAttribute("advertList",advertList);
		return "front/topic/my_collection_topic";
	}
	
	/**
	 * 发布话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/publishTopic", method = RequestMethod.GET)
	public String publishTopic(Model model, HttpServletRequest request) {
		
        
		List<Sticker> list = stickerService.getStickerByCode("TOPIC_STICKER");
		model.addAttribute("stickerList", list);
		return "front/topic/topic_publish";
	}
	
	/**
	 * 发布话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/publishTopic", method = RequestMethod.POST)
	public String publishTopicPost(Model model, HttpServletRequest request, @ModelAttribute Topic topic) {
//		String[] sticker = request.getParameterValues("sticker");
//		StringBuffer stickerValue = new StringBuffer();
//		for(int i=0; i<sticker.length; i++){
//			if(i == 0){
//				stickerValue.append(sticker[i]);
//			}else{
//				stickerValue.append(","+sticker[i]+",");
//			}
//        }
		
		//********************************************
		//** 将临时图片文件移入指定文件夹
		//********************************************
		String content = topic.getContent();
		topic.setContent(super.moveContentImageToRealPath(content));
		
		if(topic.getId() != null && topic.getId() >0){
			Topic updateTopic = topicService.findById(topic.getId());
			updateTopic.setSubject(topic.getSubject());
			updateTopic.setContent(topic.getContent());
			updateTopic.setTopicImage(super.moveImageToRealPath(ContentUtil.getFirstImg(topic.getContent(),CDN)));
			//updateTopic.setSticker(stickerValue.toString());
			//话题默认审核通过
			updateTopic.setExamined(1);
			topicService.save(updateTopic);
		}else{
			topic.setTopicImage(super.moveImageToRealPath(ContentUtil.getFirstImg(topic.getContent(),CDN)));
			topic.setAuthor(super.getSessionUser(request.getSession()).getUserInfo());
			//topic.setSticker(stickerValue.toString());
			topic.setCreateDate(new Date());
			//话题默认审核通过
			topic.setExamined(1);
			topicService.save(topic);
		}
		//删除话题选择的标签
		topicStickerService.deleteByTopicId(topic.getId());
		//获取话题选择的标签
		String stickerString = request.getParameter("sticker");
		if(stickerString != null && !stickerString.equals("")){
			String[] sticker = stickerString.split(",");
			if(sticker != null){
				for(int i=0; i<sticker.length; i++){
					TopicSticker ts = new TopicSticker();
					ts.setTopic(topic);
					ts.setSticker(stickerService.findById(Long.valueOf(sticker[i])));
					ts.setCreatedTime(new Date());
					topicStickerService.save(ts);
				}
			}
			
		}
		return "redirect:/front/m/topic/myTopic";
	}
	
	/**
	 * 修改话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/editTopic/{id}", method = RequestMethod.GET)
	public String editTopic(Model model, HttpServletRequest request ,@PathVariable("id") Long id) {
		//判断访问设备
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }
        boolean isFromMobile = MobileUtil.check(userAgent);
        //判断是否为移动端访问  
        if (!isFromMobile) {
        	model.addAttribute("equipment", "pc");
        }else{
        	model.addAttribute("equipment", "mobile");
        }
        
		List<Sticker> list = stickerService.getStickerByCode("TOPIC_STICKER");
		model.addAttribute("stickerList", list);
		
		Topic topic = topicService.findById(id);
		model.addAttribute("topic", topic);
		
		//获取话题选择的标签
		List<TopicSticker> tsList = topicStickerService.findByTopicId(id);
		StringBuffer topicStickerString = new StringBuffer();
		if(tsList != null && tsList.size() > 0){
			for(TopicSticker ts:tsList){
				topicStickerString.append(ts.getSticker().getId()).append(",");
			}
		}
		model.addAttribute("topicSticker", topicStickerString);
		return "front/topic/topic_edit";
	}
	
	/**
	 * 删除话题
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m/topic/deleteTopic/{id}", method = RequestMethod.GET)
	public String deleteTopic(Model model, HttpServletRequest request ,@PathVariable("id") Long id) {
		Topic topic = topicService.findById(id);
		topic.setStatus(-1);
		topicService.save(topic);
		return "redirect:/front/m/topic/myTopic";
	}
}
