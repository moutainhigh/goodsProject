package com.mendao.framework.action;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.BranchSchool;
import com.mendao.business.entity.Province;
import com.mendao.business.entity.School;
import com.mendao.business.entity.SchoolAttachment;
import com.mendao.business.entity.SchoolProduct;
import com.mendao.business.entity.SchoolSticker;
import com.mendao.business.entity.Sticker;
import com.mendao.business.service.DistrictService;
import com.mendao.business.service.SchoolAttachmentService;
import com.mendao.business.service.SchoolBranchService;
import com.mendao.business.service.SchoolProductService;
import com.mendao.business.service.SchoolService;
import com.mendao.business.service.SchoolStickerService;
import com.mendao.business.service.StickerService;
import com.mendao.common.handler.FileUploadHandler;
import com.mendao.common.handler.ImageHandler;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.show.BaseController;


@Controller
@RequestMapping("/backend/school")
@SessionAttributes(types = School.class)
@SuppressWarnings("unchecked")
public class SchoolController extends BaseController{
	
	@Autowired
	DistrictService districtService;
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private SchoolStickerService schoolStickerService;
	
	@Autowired
	private StickerService stickerService;
	
	@Autowired
	private SchoolAttachmentService schoolAttachmentService;
	
	@Autowired
	private SchoolProductService schoolProductService;
	
	@Autowired
	private SchoolBranchService schoolBranchService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String query(Model model, HttpServletRequest request) {
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity =  this.schoolService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/school/school_list";
	}
	/**
	 * 新增机构
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		School school = new School();
		school.setFwAccount(new FwAccount());
		return initEditPage(model, school);
	}
	/**
	 * 机构基本信息修改页
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request, @PathVariable("id") Long id) {
		School school = schoolService.findOne(id);
		return initEditPage(model, school);
	}
	/**
	 * 保存机构基本信息
	 * @param model
	 * @param request
	 * @param school
	 * @return
	 */
	@RequestMapping(value = "/editProcess", method = RequestMethod.POST)
	public String editProcess(Model model, HttpServletRequest request, @ModelAttribute School school) {
		String introduction = school.getIntroduction();
		school.setIntroduction(super.moveContentImageToRealPath(introduction));
		if(school.getId() != null && school.getId() > 0){
			schoolService.update(school);
		}else{
			schoolService.save(school);
		}
		return "redirect:/backend/school/edit/"+school.getId();
	}
	/**
	 * 查看机构教学环境
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/attachment/{schoolId}")
	public String attachment(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		PageEntity<SchoolAttachment> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("school.id", schoolId);
		pageEntity.setOrderBy(" order by o.id asc");
		pageEntity =  this.schoolAttachmentService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("schoolId", schoolId);
		return "backend/school/school_attachment";
	}
	/**
	 * 为机构教学环境添加图片
	 * @param request
	 * @param model
	 * @param schoolAttachment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAttachment",method = RequestMethod.POST)
	public Map<String,Object> addAttachment(HttpServletRequest request, Model model,  @ModelAttribute SchoolAttachment schoolAttachment){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			//********************************************
			//** 将临时图片文件移入指定文件夹
			//********************************************
			schoolAttachment.setImagePath(super.moveImageToRealPath(schoolAttachment.getImagePath()));
			schoolAttachmentService.save(schoolAttachment);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 删除教学环境图片
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAttachment/{id}",method = RequestMethod.POST)
	public Map<String,Object> deleteAttachment(HttpServletRequest request, Model model,@PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			schoolAttachmentService.deleteById(id);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 查看机构课程
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/product/{schoolId}")
	public String product(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		PageEntity<SchoolProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("school.id", schoolId);
		pageEntity =  this.schoolProductService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("schoolId", schoolId);
		return "backend/school/school_product";
	}
	/**
	 * 保存机构课程
	 * @param request
	 * @param model
	 * @param schoolProduct
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addProduct",method = RequestMethod.POST)
	public Map<String,Object> addProduct(HttpServletRequest request, Model model,  @ModelAttribute SchoolProduct schoolProduct){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			//********************************************
			//** 将临时图片文件移入指定文件夹
			//********************************************
			schoolProduct.setImagePath(super.moveImageToRealPath(schoolProduct.getImagePath()));
			schoolProductService.save(schoolProduct);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 修改机构课程
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editProduct/{id}")
	public @ResponseBody Map<String,Object> editProduct(HttpServletRequest request, Model model, @PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			SchoolProduct product = schoolProductService.findById(id);
			map.put("msg", 1);
			map.put("id", product.getId());
			map.put("productName", product.getProductName());
			map.put("imagePath", product.getImagePath());
			map.put("summary", product.getSummary());
			map.put("schoolId", product.getSchool().getId());
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 删除机构课程
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProduct/{id}",method = RequestMethod.POST)
	public Map<String,Object> deleteProduct(HttpServletRequest request, Model model,@PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			schoolProductService.deleteById(id);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 查看分校
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/branch/{schoolId}")
	public String branch(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		PageEntity<BranchSchool> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("parent.id", schoolId);
		pageEntity.setOrderBy(" order by o.sortSeq desc ");
		pageEntity =  this.schoolBranchService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		//获取省
		List<Province> province = districtService.getAllProvince();
		model.addAttribute("province", province);
		
		model.addAttribute("schoolId", schoolId);
		return "backend/school/school_branch";
	}
	/**
	 * 保存分校
	 * @param request
	 * @param model
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBranch",method = RequestMethod.POST)
	public Map<String,Object> addBranch(HttpServletRequest request, Model model,  @ModelAttribute BranchSchool branch){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			schoolBranchService.save(branch);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 修改分校信息
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editBranch/{id}")
	public @ResponseBody Map<String,Object> editBranch(HttpServletRequest request, Model model, @PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			BranchSchool branch = schoolBranchService.findById(id);
			map.put("msg", 1);
			map.put("id", branch.getId());
			map.put("schoolName", branch.getSchoolName());
			map.put("address", branch.getAddress());
			map.put("district", branch.getDistrict());
			map.put("parentId", branch.getParent().getId());
			map.put("sortSeq", branch.getSortSeq());
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 删除分校信息
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteBranch/{id}",method = RequestMethod.POST)
	public Map<String,Object> deleteBranch(HttpServletRequest request, Model model,@PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			schoolBranchService.deleteById(id);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 查看机构的标签
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/sticker/{schoolId}")
	public String sticker(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		PageEntity<SchoolSticker> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.getParams().put("school.id", schoolId);
		pageEntity =  this.schoolStickerService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		return "backend/school/school_sticker";
	}
	/**
	 * 显示新增标签选择页面
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/showSchoolSticker/{schoolId}")
	public String showSchoolSticker(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		PageEntity<Sticker> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 300);
		List<SchoolSticker> list = schoolStickerService.findBySchoolId(schoolId);
		if(list != null && list.size() > 0){
			List<Long> idList = new ArrayList<Long>();
			for(SchoolSticker l:list){
				idList.add(l.getSticker().getId());
			}
			pageEntity.getParams().put("id_notin", idList);
		}
		pageEntity.getParams().put("code", "SCHOOL_STICKER");
		pageEntity =  this.stickerService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		
		model.addAttribute("schoolId", schoolId);
		return "backend/school/school_sticker_choose";
	}
	/**
	 * 新增标签
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSticker")
	public Map<String,Object> addSticker(HttpServletRequest request, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] ids = request.getParameterValues("id");
			String schoolId = request.getParameter("schoolId");
			School school = schoolService.findOne(Long.valueOf(schoolId));
			List<Sticker> stickers = stickerService.findListByIds(ids);
			if(stickers != null && stickers.size() > 0){
				for(Sticker sticker:stickers){
					SchoolSticker schoolSticker = new SchoolSticker();
					schoolSticker.setSchool(school);
					schoolSticker.setSticker(sticker);
					schoolSticker.setCreatedTime(new Date());
					schoolStickerService.save(schoolSticker);
				}
			}
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", 0);
		}
		return map;
	}
	/**
	 * 标签修改
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editSticker/{id}")
	public Map<String,Object> editSticker(HttpServletRequest request, Model model, @PathVariable("id") Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String sortSeq = request.getParameter("sortSeq");
			SchoolSticker schoolSticker = schoolStickerService.findById(id);
			schoolSticker.setSortSeq(sortSeq);
			schoolStickerService.save(schoolSticker);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 标签删除
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSticker/{id}",method = RequestMethod.POST)
	public Map<String,Object> deleteSticker(HttpServletRequest request, Model model,@PathVariable("id") Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			schoolStickerService.deleteById(id);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	/**
	 * 显示机构logo
	 * @param request
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/logo/{schoolId}")
	public String logo(HttpServletRequest request, Model model, @PathVariable("schoolId") Long schoolId){
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("school", schoolService.findOne(schoolId));
		return "backend/school/school_logo";
	}
	/**
	 * 机构logo上传
	 * @param request
	 * @param model
	 * @param attr
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/uploadLogo/{schoolId}")
	public String uploadLogo(MultipartHttpServletRequest request, Model model, RedirectAttributes attr, @PathVariable("schoolId") Long schoolId){
		//UserProfile profile = super.getSessionUser(request.getSession());
		
//		Iterator<String> it = request.getFileNames();
//		
//		FileUploadHandler upload = FileUploadHandler.instance();
//		if(!upload.save(request.getFile(it.next()), profile.getId() + "")){
//			attr.addFlashAttribute(ERROR_MESSAGE, "文件上传失败：" + upload.getErrorMessage());
//		}
//		
//		String filePath = upload.getFilePath() + upload.getFileName();
//		model.addAttribute("imagePath", filePath);
//		request.getSession().setAttribute("USER_TEMP_ICON", filePath);
//		model.addAttribute("schoolId", schoolId);
//		return "backend/school/school_logo_preview";
		return null;
	}
	/**
	 * 机构logo裁减保存
	 * @param request
	 * @param attr
	 * @param schoolId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cutLogo/{schoolId}", method = RequestMethod.POST)
	public Map<String,Object> cutLogo(final HttpServletRequest request, RedirectAttributes attr, @PathVariable("schoolId") Long schoolId){
		Map<String,Object> map = new HashMap<String, Object>();
		
		FileUploadHandler upload = FileUploadHandler.instance();
		String filePath = (String)request.getSession().getAttribute("USER_TEMP_ICON");
						
		Double x = Double.valueOf(request.getParameter("x1"));
		Double y = Double.valueOf(request.getParameter("y1"));
		Double w = Double.valueOf(request.getParameter("w"));
		Double h = Double.valueOf(request.getParameter("h"));
		String realPath = filePath.replaceFirst("/temp/", "/");
		ImageHandler handler = ImageHandler.instance();
		boolean success = ImageHandler.instance().cutImage(upload.getUploadPath() + filePath, 
				upload.getUploadPath() + realPath, x.intValue(), y.intValue(), w.intValue(), h.intValue());
		if(!success){
			attr.addFlashAttribute(ERROR_MESSAGE, "图片裁减出错");
		}
		handler.compressImage(upload.getUploadPath() + realPath, upload.getUploadPath());
		try {
			School school = schoolService.findOne(schoolId);
			school.setLogoImage(realPath);
			schoolService.update(school);
			map.put("msg", 1);
		} catch (Exception e) {
			attr.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
			map.put("msg", -1);
		}
		
		return map;
	}
	
	private String initEditPage(Model model, School school){
		model.addAttribute("school", school);
		List<Province> province = districtService.getAllProvince();
		model.addAttribute("province", province);
		
		return "backend/school/school_edit";
	}
	
}
