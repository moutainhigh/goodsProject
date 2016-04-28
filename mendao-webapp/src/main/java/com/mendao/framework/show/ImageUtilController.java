package com.mendao.framework.show;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mendao.business.dto.UserProfile;
import com.mendao.common.handler.FileUploadHandler;
import com.mendao.common.util.StringUtil;
import com.mendao.util.PropertiesUtil;

@Controller
@RequestMapping(value = "/upload")
public class ImageUtilController extends BaseController {
	/**
	 * 图片上传方法
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 * @throws FileUploadException
	 */
	@RequestMapping(value = "/upimage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upimage(
			@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model){
		Map<String, Object> result = new HashMap<String, Object>();
		if (!ServletFileUpload.isMultipartContent(request)) {
			result.put("error", 1);
			result.put("message", "请选择文件。");
		}else{
			// 获取当前登陆的用户ID
			UserProfile profile = super.getSessionUser(request.getSession());
			String userId = "other";
			if(profile != null){
				userId = profile.getId().toString();
			}
			FileUploadHandler handler = FileUploadHandler.instance();
			handler.setMaxSize(2097152);
			if(handler.save(file, userId)){
				
				result.put("error", 0);
				result.put("url", StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")) + handler.getFilePath() + handler.getFileName());
				result.put("path", handler.getFilePath() + handler.getFileName());
			}else{
				result.put("error", 1);
				result.put("message", handler.getErrorMessage());
			}
		}
		return result;
	}
}