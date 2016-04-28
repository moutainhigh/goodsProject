package com.mendao.framework.show;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.City;
import com.mendao.business.entity.District;
import com.mendao.business.entity.Feedback;
import com.mendao.business.entity.Topic;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.DistrictService;
import com.mendao.business.service.FeedbackService;
import com.mendao.business.service.TopicService;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.common.enums.AttachTypeEnum;
import com.mendao.common.util.StringUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.util.PropertiesUtil;

@Controller
public class CommonController extends BaseController {

	@Autowired
	FeedbackService feedbackService;
	
	@Autowired
	DistrictService districtService;
	
	@Autowired 
	VerifyUserService verifyService;
	
	@Autowired
	UserActionService userActionService;
	
	@Autowired
	TopicService topicService;
	
	/**
	 * 关于我们、加入我们、合作申请、意见反馈
	 */
	@RequestMapping(value = {"/aboutUs", "/joinUs", "/cooperate", "/feedback"}, method = RequestMethod.GET)
	public String contactUs(final HttpServletRequest request){
		String url = request.getServletPath();
		url = url.substring(url.lastIndexOf("/") + 1);
		switch(url){
		case "aboutUs":
			return "front/about_us";
		case "joinUs":
			return "front/join_us";
		case "cooperate":
			return "front/cooperate";
		case "feedback":
			return "front/feedback";
		}
		return "front/about_us";
	}
	
	/**
	 * 提交意见反馈
	 * @param request
	 * @param feedback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public Map<String, Object> feedback(final HttpServletRequest request, @ModelAttribute Feedback feedback){
		Map<String, Object> result = new HashMap<String, Object>();
		feedbackService.save(feedback);
		result.put("mark", true);
		result.put("message", "您的意见反馈已经发送至门道客服，感谢您对门道的支持");
		return result;
	}
	
	
	/**
	 * 根据省份名获取城市列表
	 * @param request
	 * @param province
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/common/cities", method = RequestMethod.GET)
	public String getCitiesByProvince(final HttpServletRequest request, @RequestParam("prov") String province){
		StringBuffer bf = new StringBuffer();
		for(City c : districtService.getAllCityByProvinceName(province)){
			bf.append("," + c.getName());
		}
		return bf.substring(1);
	}
	
	/**
	 * 根据城市名获取行政区域列表
	 * @param request
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/common/districts", method = RequestMethod.GET)
	public String getDistrictsByCity(final HttpServletRequest request, @RequestParam("city") String city){
		StringBuffer bf = new StringBuffer();
		for(District c : districtService.getAllDistrictByCityName(city)){
			bf.append("," + c.getName());
		}
		return bf.substring(1);
	}
	
	
	/**
	 * 请求发送手机(邮箱)验证码
	 * 
	 * @param session
	 * @param target
	 *            手机验证码类型，对应于荣联短信模版id。
	 *            注册验证码（register），忘记密码时的验证码（forgetpwd），绑定手机验证码（bindmobile）
	 * @param mobile 接收验证码的手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/common/verify/{target}", method = RequestMethod.GET)
	public Map<String, Object> sendVerifyCode(final HttpSession session, 
			@PathVariable("target") String target,
			@RequestParam(value="email", required=false, defaultValue="") String email,
			@RequestParam(value="mobile", required=false, defaultValue="") String mobile){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			UserProfile user = getSessionUser(session);
			Long userId = (user == null ? 0L : user.getId());
			String verifyCode = "";
			if(StringUtil.isNotBlank(mobile)){
				verifyCode = verifyService.getVerifyCode(target, mobile, userId);
			}else if(StringUtil.isNotBlank(email)){
				verifyCode = verifyService.getVerifyCodeByEmail(target, email, userId);
			}
			result.put("mark", true);
			result.put("verifyCode", verifyCode);
		}catch(BusinessCheckException e){
			result.put("mark", false);
			result.put("message", e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/upload/school")
	public Map<String, Object> upimage(
			@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model)
			throws FileUploadException {
		Map<String, Object> result = new HashMap<String, Object>();
		// 文件保存目录路径
		String uploadPath = PropertiesUtil.getProperty("service.upload_path");
		String dir = request.getParameter("dir");
		
		// 文件保存目录URL
		String saveUrl = dir + "/";
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", AttachTypeEnum.getValue("IMAGE"));
		

		// 最大文件大小
		long maxSize = 2097152;

		if (!ServletFileUpload.isMultipartContent(request)) {
			result.put("mark", false);
			result.put("message", "请选择文件");
			return result;
		}
		// 检查目录
		File uploadDir = new File(uploadPath + saveUrl);
		if (!uploadDir.exists() || !uploadDir.isDirectory()) {
			uploadDir.mkdirs();
			try {
				Runtime.getRuntime().exec("chmod 777 " + uploadDir.getPath());
			} catch (IOException e) {
				result.put("mark", false);
				result.put("message", "修改文件夹权限失败");
				return result;
			}
		}
		if (!uploadDir.isDirectory()) {
			result.put("mark", false);
			result.put("message", "上传目录不存在");
			return result;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			result.put("mark", false);
			result.put("message", "无权操作");
			return result;
		}

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		saveUrl += ymd + "/";
		File dirFile = new File(uploadPath + saveUrl);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
			try {
				Runtime.getRuntime().exec("chmod 777 " + dirFile.getPath());
			} catch (IOException e) {
				result.put("mark", false);
				result.put("message", "修改文件夹权限失败");
				return result;
			}
		}

		if (file != null) {
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize();
			// 检查文件大小
			if (file.getSize() > maxSize) {
				result.put("mark", false);
				result.put("message", "文件太大");
				return result;
			}
			// 检查扩展名
			String fileExt = FilenameUtils.getExtension(fileName).toLowerCase(Locale.ENGLISH);
//			if (!Arrays.<String> asList(extMap.get(dirName).split(","))
//					.contains(fileExt)) {
//				return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
//			}

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_"
					+ new Random().nextInt(1000) + "." + fileExt;
			int imgWidth = 0, imgHeight = 0; // 用于上传图片时计算图片大小
			try {
				File uploadedFile = new File(uploadPath + saveUrl, newFileName);
				// file.g
				FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
				
				/*
				 * 以下代码用于上传图片时计算图片大小
				 */
//				try {
//					int[] imgWH = getImageWithAndHeight(uploadedFile);
//					imgWidth = imgWH[0];
//					imgHeight = imgWH[1];
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				// item.write(uploadedFile);
			} catch (Exception e) {
				result.put("mark", false);
				result.put("message", "上传失败");
				return result;
			}
			result.put("error", 0);
			result.put("url", PropertiesUtil.getProperty("service.upload_show") + saveUrl + newFileName);
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/front/user/home", method = RequestMethod.GET)
	public String myCollection(Model model, HttpServletRequest request, @RequestParam(value = "id", required = false) Long id) {
		id = (null == id) ? getSessionUserId(request.getSession()) : id;
		UserInfo user = userActionService.findOneUser(id);
		if(user != null){
			UserProfile profile = new UserProfile();
			profile.setUserInfo(user);
			model.addAttribute("LoginAccount", profile);
			PageEntity<Topic> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 5);
			pageEntity.getParams().put("author.id", user.getId());
			pageEntity.getParams().put("examined", 1);
			pageEntity.getParams().put("status", 0);
			pageEntity = topicService.getPage(pageEntity);
			model.addAttribute("pageBean", pageEntity);
			ParamsUtil.addAttributeModle(model, pageEntity);
			if(user.getId() == getSessionUserId(request.getSession())){
				model.addAttribute("isMe", true);
			}
			
		}
		return "/front/user/home";
	}
}
