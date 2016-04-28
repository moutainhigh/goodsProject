package com.mendao.framework.show;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Advert;
import com.mendao.business.entity.Province;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.TopicCollection;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.DistrictService;
import com.mendao.business.service.StickerService;
import com.mendao.business.service.TopicCollectionService;
import com.mendao.business.service.UserActionService;
import com.mendao.common.handler.FileUploadHandler;
import com.mendao.common.handler.ImageHandler;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;

@Controller
@RequestMapping("/front/m/user")
public class UserFrontController extends BaseController{

	@Autowired
	DistrictService districtService;
	
	@Autowired
	UserActionService userActionService;
	
	@Autowired
	private StickerService 	stickerService;

	@Autowired
	private TopicCollectionService topicCollectionService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String userIcon(final HttpServletRequest request, final HttpSession session, final Model model){
		UserProfile profile = this.getSessionUser(session);
		UserInfo user = userActionService.findOneUser(profile.getId());
		profile.setUserInfo(user);
		
		return "front/user/set_user_icon";
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String setUserInfo(final HttpServletRequest request, final HttpSession session, final Model model){
		UserProfile profile = this.getSessionUser(session);
		UserInfo user = userActionService.findOneUser(profile.getId());
		profile.setUserInfo(user);
		//this.setSessionUser(session, profile);
		List<Province> province = districtService.getAllProvince();
		
		//获取关注标签
		List<Sticker> list = stickerService.getStickerByCode("FOLLOW_STICKER");
		model.addAttribute("stickerList", list);
		
		model.addAttribute("userInfo", user);
		model.addAttribute("province", province);
		return "front/user/set_user_info";
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String setUserInfo(final HttpServletRequest request, final HttpSession session, final Model model, @ModelAttribute UserInfo user){
		UserProfile profile = new UserProfile();
		//this.setSessionUser(session, profile);
		List<Province> province = districtService.getAllProvince();
		
		model.addAttribute("userInfo", user);
		model.addAttribute("province", province);
		
		//获取关注标签
		List<Sticker> list = stickerService.getStickerByCode("FOLLOW_STICKER");
		model.addAttribute("stickerList", list);
		
		try {
			profile.setUserInfo(user);
			userActionService.updateUserInfo(profile);
//			super.setSessionUser(session, profile);
		} catch (BusinessCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "front/user/set_user_info";
	}
	
	@RequestMapping(value = "/security", method = RequestMethod.GET)
	public String userSecurity(final HttpServletRequest request, final HttpSession session, final Model model){
		
		model.addAttribute("userInfo", getSessionUser(session).getUserInfo());
		return "front/user/set_user_security";
	}

	/**
	 * 打开用户安全中心详细页面
	 * @param request
	 * @param session
	 * @param model
	 * @param target 该参数表示需要打开的详细页，对应于setting/security.html中的多个不同的form
	 * @return
	 */
	@RequestMapping(value = "/updateSecurity", method = RequestMethod.GET)
	public String resetPassword(final HttpServletRequest request, final HttpSession session, 
			final Model model, @RequestParam("target") String target){
		UserInfo user = getSessionUser(session).getUserInfo();
		model.addAttribute("userInfo", user);
		model.addAttribute("target", target);
		return "front/user/setting/security";
	}
	
	/**
	 * 保存安全中心的修改
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @param target 该参数表示需要打开的详细页，对应于setting/security.html中的多个不同的form
	 * @return
	 */
	@RequestMapping(value = "/updateSecurity", method = RequestMethod.POST)
	public String resetPasswordProcess(final HttpServletRequest request, final HttpSession session, 
			final Model model, @RequestParam("target") String target, RedirectAttributes attr){
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		UserProfile profile = getSessionUser(session);
		try {
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			String security = request.getParameter("security");
			String newSecurity = request.getParameter("newSecurity");
			String verifyCode = request.getParameter("verify");			
			switch (target) {
			case "password":
				userActionService.updatePassword(profile.getId(), password, newPassword);
				attr.addFlashAttribute(ERROR_MESSAGE, "登录密码修改成功");
				
				break;
			case "security":
				// 
				userActionService.updateSecurity(profile.getId(), password, security, newSecurity);
				attr.addFlashAttribute(ERROR_MESSAGE, "支付密码修改成功");
				break;
			case "mobile":
				userActionService.bindMobile(profile.getId(), password, mobile, verifyCode);
				attr.addFlashAttribute(ERROR_MESSAGE, "手机号码绑定成功");
				break;
			case "email":
				userActionService.bindEmail(profile.getId(), password, email, verifyCode);
				attr.addFlashAttribute(ERROR_MESSAGE, "邮箱地址绑定成功");
				break;
			default:
				break;
			}
			//result.put("mark", true);
			profile.setUserInfo(userActionService.findOneUser(profile.getId()));
		} catch (BusinessCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//result.put("mark", true);
			attr.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("email", email);
		}
		//attr.addFlashAttribute("target", target);
		return "redirect:/front/m/user/updateSecurity?target=" + target;
	}
	
	//@ResponseBody
	@RequestMapping(value = "/uploadIcon")
	public String uploadIcon(MultipartHttpServletRequest request, final HttpServletResponse response, 
			final Model model, RedirectAttributes attr){
		UserProfile profile = super.getSessionUser(request.getSession());
		
		Iterator<String> it = request.getFileNames();
		
		FileUploadHandler upload = FileUploadHandler.instance();
		if(!upload.save(request.getFile(it.next()), profile.getId() + "")){
			attr.addFlashAttribute(ERROR_MESSAGE, "文件上传失败：" + upload.getErrorMessage());
			return "redirect:/front/m/user/uploadIcon";
		}
		
		String filePath = upload.getFilePath() + upload.getFileName();
		model.addAttribute("imagePath", filePath);
		request.getSession().setAttribute("USER_TEMP_ICON", filePath);
		return "front/user/icon_preview";
	}
	
	@RequestMapping(value = "/cutIcon", method = RequestMethod.POST)
	public String showIcon(final HttpServletRequest request, RedirectAttributes attr){
		UserProfile profile = super.getSessionUser(request.getSession());
		
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
			userActionService.updateUserIcon(profile.getId(), realPath);
		} catch (BusinessCheckException e) {
			attr.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
		}
		
		return "redirect:/front/m/user";
	}
}
