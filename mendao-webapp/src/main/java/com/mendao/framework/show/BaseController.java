package com.mendao.framework.show;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mendao.business.dto.TreeNode;
import com.mendao.business.dto.UserProfile;
import com.mendao.common.handler.FileUploadHandler;
import com.mendao.common.handler.ImageHandler;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.util.ContentUtil;
import com.mendao.util.MobileUtil;
import com.mendao.util.PropertiesUtil;

public class BaseController {

	public static final String ERROR_MESSAGE = "errorMessage";
	
	public static final String SUCCESS_MESSAGE = "successMessage";
	
	private String LOGIN_ACCOUNT = "LoginAccount";
	
	protected static final String LOGIN = "home";
	
	protected static final String REGISTER = "user_register";
	
	protected static final String REDIRECT_LOGIN = "redirect:/login";
	
	protected static final String REDIRECT_REGISTER = "redirect:/register";
	
	protected static final String REDIRECT_LOGOUT = "redirect:/logout";
	
	protected int pageSize = 20;
	
	public void setSessionUser(final HttpSession session, UserUtil profile){
		session.setAttribute(LOGIN_ACCOUNT, profile);
	}
	
	public UserUtil getSessionUser(final HttpSession session){
		return (UserUtil)session.getAttribute(LOGIN_ACCOUNT);
	}
	
	public Long getSessionUserId(final HttpSession session){
		UserProfile profile = (UserProfile)session.getAttribute(LOGIN_ACCOUNT);
		return (null != profile ? profile.getId() : 0L);
	}
	
	protected void createTreeUl(TreeNode root, StringBuffer bf, Long checkedId){
		//if(root.getParentId() != null){
		boolean checked = (root.getId() == checkedId);
	    	bf.append("<ul " + (root.getId() == 0L ? "" : "class='subul'") + ">");
	    	
	    	bf.append("<li level='" + root.getLevel() + "' sortSeq='" + root.getSortSeq() + "' >");
	    	if(!root.isLeaf()){
	    		bf.append("<label class='ul_label'>+</label>");
	    	}else{
	    		bf.append("<label class='ul_label_none'></label>");
	    	}
	    	bf.append(root.getNodeName());
			bf.append(MessageFormat.format("<input id=\"{0}\" name=\"rdParent\" type=\"radio\" value=\"{0}\" parent=\"{1}\" {2}/>", 
					root.getId(), (root.getParentId() == null ? "" : root.getParentId()), 
					(checked ? "checked=\"checked\"" : "")));
			
		//}
		if(root.isLeaf() == false){
			//bf.append("\n<ul class='subul'>\n");
			root.getChildList().forEach(n -> {
				createTreeUl(n, bf, checkedId);
			});
			//bf.append("</ul>\n");
		}
		//if(root.getParentId() != null){
			bf.append("</li></ul>\n");
		//}
    }
	
	protected String moveImageToRealPath(String tmpImage){
		String uploadPath = PropertiesUtil.getProperty("service.upload_path");
		if(StringUtil.isBlank(uploadPath)){
			uploadPath = this.getClass().getResource("/static").getPath() + File.separator;
		}
		if(StringUtil.contains(tmpImage, "/temp/")){
			String realImage = tmpImage.replaceFirst("/temp/", "/");
			FileUploadHandler.move(uploadPath + tmpImage, uploadPath + realImage);
			ImageHandler.instance().compressImage(uploadPath + realImage, uploadPath);
			return realImage;
		}
		return tmpImage;
	}
	
	protected Map<String, String> moveImageToRealPath(List<String> tmpImages){
		Map<String, String> realPaths = new HashMap<String, String>();
		String uploadPath = PropertiesUtil.getProperty("service.upload_path");
		if(StringUtil.isBlank(uploadPath)){
			uploadPath = this.getClass().getResource("/static").getPath() + File.separator;
		}
		for(String image : tmpImages){
			if(StringUtil.contains(image, "/temp/")){
				String realImage = image.replaceFirst("/temp/", "/");
				FileUploadHandler.move(uploadPath + image, uploadPath + realImage);
				ImageHandler.instance().compressImage(uploadPath + realImage, uploadPath);
				realPaths.put(image, realImage);
			}
		}
		
		return realPaths;
	}
	
	protected String moveContentImageToRealPath(String content){
		List<String> allImages = ContentUtil.getAllImgs(content, 
				StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")));
		if(!allImages.isEmpty()){
			Map<String, String> realImages = moveImageToRealPath(allImages);
			if(!realImages.isEmpty()){
				for(String key : realImages.keySet()){
					content = content.replaceFirst(key, realImages.get(key));
				}
			}
		}
		return ContentUtil.formatContent(content);
	}
	
	protected String checkEquipment(HttpServletRequest request){
		//判断访问设备
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }
        boolean isFromMobile = MobileUtil.check(userAgent);
        //判断是否为移动端访问  
        if (!isFromMobile) {
        	return "pc";
        }else{
        	return "mobile";
        }
	}
}
