package com.mendao.framework.action;




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

import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.Topic;
import com.mendao.business.entity.TopicSticker;
import com.mendao.business.service.StickerService;
import com.mendao.business.service.TopicService;
import com.mendao.business.service.TopicStickerService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;


/**
 * 话题Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("/backend/topic")
@SuppressWarnings("unchecked")
public class BackendTopicController extends BaseController{
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private StickerService stickerService;
	
	@Autowired
	private TopicStickerService topicStickerService;
	/**
	 * 话题列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("status", 0);
		pageEntity = topicService.getPage(pageEntity);
		
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		return "backend/topic/topic_list";
	}
	/**
	 * 话题编辑
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		Topic topic = topicService.findById(id);
		model.addAttribute("topic",topic);
		//获取活动标签
		List<Sticker> list = stickerService.getStickerByCode("TOPIC_STICKER");
		model.addAttribute("stickerList", list);
		
		//获取话题选择的标签
		List<TopicSticker> tsList = topicStickerService.findByTopicId(id);
		StringBuffer topicStickerString = new StringBuffer();
		if(tsList != null && tsList.size() > 0){
			for(TopicSticker ts:tsList){
				topicStickerString.append(ts.getSticker().getId()).append(",");
			}
		}
		model.addAttribute("topicSticker", topicStickerString);
		
		return "backend/topic/topic_edit";
	}
	/**
	 * 话题审核通过
	 * @param model
	 * @param request
	 * @param topic
	 * @return
	 */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public String pass(Model model, HttpServletRequest request, @ModelAttribute Topic topic) {
		Topic updateTopic = topicService.findById(topic.getId());
		updateTopic.setSubject(topic.getSubject());
		updateTopic.setContent(topic.getContent());
		updateTopic.setExamined(1);
		topicService.save(updateTopic);
		
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
		return "redirect:/backend/topic/list";
	}
	/**
	 * 话题审核驳回
	 * @param model
	 * @param request
	 * @param topic
	 * @return
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public String reject(Model model, HttpServletRequest request, @ModelAttribute Topic topic) {
		Topic updateTopic = topicService.findById(topic.getId());
		updateTopic.setSubject(topic.getSubject());
		updateTopic.setContent(topic.getContent());
		updateTopic.setExamined(0);
		topicService.save(updateTopic);
		
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
		return "redirect:/backend/topic/list";
	}
	/**
	 * 话题审核通过（批量）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchPass", method = RequestMethod.GET)
	public String batchPass(HttpServletRequest request) throws Exception {
	   String[] ids = request.getParameterValues("id");
	   List<Topic> topics = topicService.findListByIds(ids);
	   if(topics != null && topics.size() > 0){
		   for(Topic topic:topics){
			   topic.setExamined(1);
			   topicService.save(topic);
		   }
	   }
	   return "redirect:/backend/topic/list";
	}
	/**
	 * 话题审核驳回（批量）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReject", method = RequestMethod.GET)
	public String batchReject(HttpServletRequest request) throws Exception {
	   String[] ids = request.getParameterValues("id");
	   List<Topic> topics = topicService.findListByIds(ids);
	   if(topics != null && topics.size() > 0){
		   for(Topic topic:topics){
			   topic.setExamined(0);
			   topicService.save(topic);
		   }
	   }
	   return "redirect:/backend/topic/list";
	}
	/**
	 * 话题删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) throws Exception {
	   String[] ids = request.getParameterValues("id");
	   List<Topic> topics = topicService.findListByIds(ids);
	   if(topics != null && topics.size() > 0){
		   for(Topic topic:topics){
			   topic.setStatus(-1);
			   topicService.save(topic);
		   }
	   }
	   return "redirect:/backend/topic/list";
	}
}
