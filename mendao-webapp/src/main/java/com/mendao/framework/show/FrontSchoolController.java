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
import com.mendao.business.entity.BranchSchool;
import com.mendao.business.entity.Category;
import com.mendao.business.entity.School;
import com.mendao.business.entity.SchoolAttachment;
import com.mendao.business.entity.SchoolComment;
import com.mendao.business.entity.SchoolProduct;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.CategoryService;
import com.mendao.business.service.SchoolAttachmentService;
import com.mendao.business.service.SchoolBranchService;
import com.mendao.business.service.SchoolCommentService;
import com.mendao.business.service.SchoolProductService;
import com.mendao.business.service.SchoolService;
import com.mendao.business.service.StickerService;
import com.mendao.common.util.StringUtil;
import com.mendao.entity.util.CategroyChildUtil;
import com.mendao.entity.util.CategroyUtil;
import com.mendao.entity.util.SchoolCommentUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.util.PropertiesUtil;

/**
 * 机构Controller
 * @author warden
 *
 */
@Controller
@RequestMapping("front")
@SuppressWarnings("unchecked")
public class FrontSchoolController extends BaseController{
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SchoolProductService schoolProductService;
	
	@Autowired
	private SchoolAttachmentService schoolAttachmentService;
	
	@Autowired
	private SchoolCommentService schoolCommentService;
	
	@Autowired
	private SchoolBranchService schoolBranchService;
	
	@Autowired
	private StickerService stickerService; 
	/**
	 * 获取机构列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/school/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		String categoryId = request.getParameter("categoryId");
		String schoolName = request.getParameter("schoolName");
		String orderType = request.getParameter("orderType");
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 16);
		if(categoryId != null && !categoryId.equals("")){
			pageEntity.getParams().put("categoryId", categoryId);
		}
		if(schoolName != null && !schoolName.equals("")){
			pageEntity.getParams().put("schoolName", schoolName);
		}
		if(orderType != null && !orderType.equals("")){
			pageEntity.getParams().put("orderType", orderType);
		}
		pageEntity = schoolService.getSchoolPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		
		//获取分类标签
		List<Category> categoryList = categoryService.getListByAttributes("STICKER", 1, "SCHOOL_STICKER");
		List<CategroyUtil> list = new ArrayList<CategroyUtil>();
		if(categoryList != null && categoryList.size() > 0){
			for(Category category:categoryList){
				CategroyUtil categroyUtil = new CategroyUtil();
				categroyUtil.setCategory(category);
				List<Category> childrenList = categoryService.getListByLevelAndParent(category.getId(),2,"SCHOOL_STICKER");
				List<CategroyChildUtil> categroyChildUtil = new ArrayList<CategroyChildUtil>();
				if(childrenList != null && childrenList.size() > 0){
					for(Category child:childrenList){
						CategroyChildUtil childUtil = new CategroyChildUtil();
						childUtil.setCategory(child);
						childUtil.setGrendSon(categoryService.getListByLevelAndParent(child.getId(),3,"SCHOOL_STICKER"));
						categroyChildUtil.add(childUtil);
					}
				}
				categroyUtil.setChildren(categroyChildUtil);
				list.add(categroyUtil);
			}
		}
		
		model.addAttribute("categoryList", list);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("schoolName", schoolName);
		model.addAttribute("orderType", orderType);
		if(this.checkEquipment(request).equals("pc")){
			return "front/school/list";
		}else{
			//获取机构的热门
			PageEntity<Sticker> stickerEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
			stickerEntity.getParams().put("code", "SCHOOL_STICKER");
			stickerEntity.setOrderBy(" order by o.sortSeq asc ");
			stickerEntity = stickerService.getPage(stickerEntity);
			model.addAttribute("stickerList", stickerEntity.getResult());
			return "front/school/list_mobile";
		}
	}
	/**
	 * 机构搜索（手机）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/school/search")
	public String search(Model model, HttpServletRequest request) {
		String categoryId = request.getParameter("categoryId");
		String schoolName = request.getParameter("schoolName");
		
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 16);
		if(categoryId != null && !categoryId.equals("")){
			pageEntity.getParams().put("categoryId", categoryId);
		}
		if(schoolName != null && !schoolName.equals("")){
			pageEntity.getParams().put("schoolName", schoolName);
		}
		
		pageEntity = schoolService.getSchoolPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "front/school/search";
	}
	
	/**
	 * 手机选择分类页面
	 * @param model
	 * @param request
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/school/categoryChoose/{categoryId}", method = RequestMethod.GET)
	public String category(Model model, HttpServletRequest request, @PathVariable("categoryId") String categoryId) {
		//获取分类标签
		List<Category> categoryList = categoryService.getListByAttributes("STICKER", 1, "SCHOOL_STICKER");
		List<CategroyUtil> list = new ArrayList<CategroyUtil>();
		if(categoryList != null && categoryList.size() > 0){
			for(Category category:categoryList){
				CategroyUtil categroyUtil = new CategroyUtil();
				categroyUtil.setCategory(category);
				List<Category> childrenList = categoryService.getListByLevelAndParent(category.getId(),2,"SCHOOL_STICKER");
				List<CategroyChildUtil> categroyChildUtil = new ArrayList<CategroyChildUtil>();
				if(childrenList != null && childrenList.size() > 0){
					for(Category child:childrenList){
						CategroyChildUtil childUtil = new CategroyChildUtil();
						childUtil.setCategory(child);
						childUtil.setGrendSon(categoryService.getListByLevelAndParent(child.getId(),3,"SCHOOL_STICKER"));
						categroyChildUtil.add(childUtil);
					}
				}
				categroyUtil.setChildren(categroyChildUtil);
				list.add(categroyUtil);
			}
		}
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryList", list);
		return "front/school/category_choose";
	}
	/**
	 * 机构详情页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/detail/{id}", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request, @PathVariable("id") Long id) {
		School school = schoolService.findOne(id);
		model.addAttribute("school", school);
		//检测用户是否登录
//		UserProfile userProfile = super.getSessionUser(request.getSession());
//		if(userProfile != null){
//			model.addAttribute("loginStatus", 1);
//		}else{
//			model.addAttribute("loginStatus", 0);
//		}
//		//查看机构的开设的课程
//		PageEntity<SchoolProduct> productEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
//		productEntity.getParams().put("school.id", id);
//		productEntity = schoolProductService.getPage(productEntity);
//		model.addAttribute("productList", productEntity);
//		//查询机构的环境
//		PageEntity<SchoolAttachment> attachmentEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
//		attachmentEntity.getParams().put("school.id", id);
//		attachmentEntity.setOrderBy(" order by o.id asc ");
//		attachmentEntity = schoolAttachmentService.getPage(attachmentEntity);
//		model.addAttribute("attachmentList", attachmentEntity);
//		
//		//获取该活动的评论
//		PageEntity<SchoolComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
//		pageEntity.getParams().put("school.id", id);
//		pageEntity.getParams().put("referLever", 1);
//		pageEntity = schoolCommentService.getPage(pageEntity);
//		//获取评论的子评论
//		List<SchoolCommentUtil> utilList = new ArrayList<SchoolCommentUtil>();
//		for(SchoolComment list:pageEntity.getResult()){
//			SchoolCommentUtil commentUtil = new SchoolCommentUtil();
//			commentUtil.setComment(list);
//			List<SchoolComment> childList = schoolCommentService.getCommentByParentId(list.getId());
//			commentUtil.setChildComment(childList);
//			utilList.add(commentUtil);
//		}
//		model.addAttribute("commentList", utilList);
//		model.addAttribute("pageBean", pageEntity);
		if(this.checkEquipment(request).equals("pc")){
			return "front/school/detail";
		}else{
			return "front/school/detail_mobile";
		}
	}
	
	/**
	 * 机构评价页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/showComment/{id}", method = RequestMethod.GET)
	public String showComment(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取该活动的评论
		PageEntity<SchoolComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity.getParams().put("referLever", 1);
		pageEntity = schoolCommentService.getPage(pageEntity);
		//获取评论的子评论
		List<SchoolCommentUtil> utilList = new ArrayList<SchoolCommentUtil>();
		for(SchoolComment list:pageEntity.getResult()){
			SchoolCommentUtil commentUtil = new SchoolCommentUtil();
			commentUtil.setComment(list);
			List<SchoolComment> childList = schoolCommentService.getCommentByParentId(list.getId());
			commentUtil.setChildComment(childList);
			utilList.add(commentUtil);
		}
		model.addAttribute("commentList", utilList);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("school", schoolService.findOne(id));
		if(this.checkEquipment(request).equals("pc")){
			return "front/school/school_comment_pc";
		}else{
			return "front/school/school_comment_mobile";
		}
	}
	/**
	 * 机构评价查看更多
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/showMoreComment/{id}", method = RequestMethod.GET)
	public String showMoreComment(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		//获取该活动的评论
		PageEntity<SchoolComment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity.getParams().put("referLever", 1);
		pageEntity = schoolCommentService.getPage(pageEntity);
		//获取评论的子评论
		List<SchoolCommentUtil> utilList = new ArrayList<SchoolCommentUtil>();
		for(SchoolComment list:pageEntity.getResult()){
			SchoolCommentUtil commentUtil = new SchoolCommentUtil();
			commentUtil.setComment(list);
			List<SchoolComment> childList = schoolCommentService.getCommentByParentId(list.getId());
			commentUtil.setChildComment(childList);
			utilList.add(commentUtil);
		}
		model.addAttribute("commentList", utilList);
		return "front/school/school_comment_mobile_more";
	}
	/**
	 * 对机构评价提交
	 * @param model
	 * @param request
	 * @param schoolComment
	 * @return
	 */
	@RequestMapping(value = "/m/school/submitComment")
	public String submitComment(Model model, HttpServletRequest request,@ModelAttribute SchoolComment schoolComment) {
		//获取当前登陆的用户
//		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
//		if(userInfo != null){
//			schoolComment.setAuthor(userInfo);
//			schoolComment.setCreatedDate(new Date());
//			schoolComment.setReferLever(1);
//			schoolCommentService.save(schoolComment);
//			//评论成功之后将机构的评论数加一
//			School school = schoolService.findOne(schoolComment.getSchool().getId());
//			school.setCommentNumber(school.getCommentNumber() + 1);
//			schoolService.update(school);
//			
//			return "redirect:/front/school/detail/"+schoolComment.getSchool().getId();
//		}else{
//			return "redirect:/login";
//		}
		return null;
	}
	
	/**
	 * 提交对评论的回复
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/m/school/submitChildCommit")
	public Map<String,Object> submitChildCommit(Model model, HttpServletRequest request,@ModelAttribute SchoolComment schoolComment) {
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前登陆的用户
//		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
//		if(userInfo != null){
//			schoolComment.setAuthor(userInfo);
//			schoolComment.setCreatedDate(new Date());
//			schoolComment.setReferLever(2);
//			schoolCommentService.save(schoolComment);
//			//评论成功之后将机构的评论数加一
//			School school = schoolService.findOne(schoolComment.getSchool().getId());
//			school.setCommentNumber(school.getCommentNumber() + 1);
//			schoolService.update(school);
//			
//			map.put("msg", 1);
//		}else{
//			map.put("msg", -1);
//		}
		
		return map;
	}
	/**
	 * 获取机构的分校
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/showBranch/{id}")
	public String showBranch(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		PageEntity<BranchSchool> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
		pageEntity.getParams().put("parent.id", id);
		pageEntity = schoolBranchService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("id", id);
		return "front/school/school_branch";
	}
	/**
	 * 机构的分校查看更多
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/school/showMoreBranch/{id}")
	public String showMoreBranch(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		PageEntity<BranchSchool> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 3);
		pageEntity.getParams().put("parent.id", id);
		pageEntity = schoolBranchService.getPage(pageEntity);
		StringBuffer html = new StringBuffer();
		for(BranchSchool branch:pageEntity.getResult()){
			html.append("<li class=\"media border-style\" style=\"padding-top: 10px; padding-bottom: 10px; margin-top: 0px;\">");
			html.append("<div class=\"row mar\" style=\"margin: 0;\">");
			html.append("<div class=\"col-md-1 col-sm-1 phs\"><i class=\"fa fa-map-marker text-xxlg center-block mtms\"></i></div>");
			html.append("<div class=\"col-md-11 col-sm-11 phs\">");
			html.append("<span class=\"text-lg\" >"+branch.getSchoolName()+"</span><br /> <span>"+branch.getAddress()+"</span>");
			html.append("</div>");
			html.append("</div>");
			html.append("</li>");
		}
		return html.toString();
	}
	/**
	 * 机构课程页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/showProduct/{id}")
	public String showProduct(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		PageEntity<SchoolProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity = schoolProductService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("id", id);
		if(this.checkEquipment(request).equals("pc")){
			return "front/school/school_product_pc";
		}else{
			return "front/school/school_product_mobile";
		}
	}
	/**
	 * 机构课程查看更多
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/school/showMoreProduct/{id}")
	public String showMoreProduct(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		PageEntity<SchoolProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity = schoolProductService.getPage(pageEntity);
		StringBuffer html = new StringBuffer();
		for(SchoolProduct product:pageEntity.getResult()){
			html.append("<div class=\"row border-style pvm\">");
			html.append("<div class=\"float-l\">");
			html.append("<img src=\""+StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")) + product.getImagePath()+"\"class=\"img60 mhm\" />");
			html.append("</div><div>");
			html.append("<p class=\"text-center text-lg mvl mlm\">"+product.getProductName()+"</p>");
			html.append("</div>");
		}
		return html.toString();
	}
	/**
	 * 机构环境页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/school/showAttach/{id}")
	public String showAttach(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		PageEntity<SchoolAttachment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity.setOrderBy(" order by o.id asc ");
		pageEntity = schoolAttachmentService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("id", id);
		if(this.checkEquipment(request).equals("pc")){
			return "front/school/school_attach_pc";
		}else{
			return "front/school/school_attach_mobile";
		}
	}
	/**
	 * 机构环境页面查看更多
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/school/showMoreAttach/{id}")
	public Map<String,Object> showMoreAttach(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
		Map<String,Object> map = new HashMap<String, Object>();
		PageEntity<SchoolAttachment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 15);
		pageEntity.getParams().put("school.id", id);
		pageEntity = schoolAttachmentService.getPage(pageEntity);
		List<String> list = new ArrayList<String>();
		for(SchoolAttachment attach:pageEntity.getResult()){
			list.add(attach.getImagePath());
		}
		map.put("data", list);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/school/freeCall")
	public Map<String,Object> freeCall(Model model, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String callPhone = request.getParameter("callPhone");
//		SendPhoneTel sendPhoneTel = new SendPhoneTel();
//		sendPhoneTel.setPhone(callPhone);
//		if(sendPhoneTel.send(phone)){
//			map.put("msg", 1);
//		}else{
//			map.put("msg", -1);
//		}
		return map;
	}
}
