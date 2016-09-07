package com.mendao.framework.show;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mendao.common.handler.FileUploadHandler;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.enums.UserUtil;
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
	@ResponseBody
	@RequestMapping(value = "/upimage", method = RequestMethod.POST)
	public String upimage(
			@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model){
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (!ServletFileUpload.isMultipartContent(request)) {
			result.put("error", 1);
			result.put("message", "请选择文件。");
		}else{
			// 获取当前登陆的用户ID
			UserUtil profile = super.getSessionUser(request.getSession());
			String userId = "other";
			if(profile != null){
				userId = profile.getId().toString();
			}
			if(file.getSize()>2097152){   
				result.put("error", 1);
				result.put("message", "上传失败：文件大小不能超过2M");
            }else{
            	FileUploadHandler handler = FileUploadHandler.instance();
            	handler.setMaxSize(2097152);
            	if(handler.save(file, userId)){
            		String path = handler.getFilePath() + handler.getFileName();
            		String url = StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")) + handler.getFilePath() + handler.getFileName();
            		moveContentImageToRealPath(path);
            		
//				path = path.replaceAll(File.separator, "/");
//				url = url.replaceAll(File.separator, "/");
            		result.put("error", 0);
            		result.put("url", url);
            		result.put("path", path);
            	}else{
            		result.put("error", 1);
            		result.put("message", handler.getErrorMessage());
            	}
            }
		}
		//将map转为json
		JSON json = JSONSerializer.toJSON(result); 
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/upmanyimage", method = RequestMethod.POST)
	public String upmanyimage(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model){
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (!ServletFileUpload.isMultipartContent(request)) {
			result.put("error", 1);
			result.put("message", "请选择文件。");
		}else{
			List<String> fileName = new ArrayList<String>();
			// 获取当前登陆的用户ID
			UserUtil profile = super.getSessionUser(request.getSession());
			String userId = "other";
			if(profile != null){
				userId = profile.getId().toString();
			}
			if(file != null && file.getSize() > 0){
				if(!fileName.contains(file.getOriginalFilename())){
					if(file.getSize()>2097152){   
						result.put("error", 1);
						result.put("message", "上传失败：文件大小不能超过2M");
					}else{
						
						FileUploadHandler handler = FileUploadHandler.instance();
						handler.setMaxSize(2097152);
						if(handler.save(file, userId)){
							String path = handler.getFilePath() + handler.getFileName();
							//String url = StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")) + handler.getFilePath() + handler.getFileName();
							String realPath = moveImageToRealPath(path);
							String realUrl = StringUtil.defaultIfBlank(PropertiesUtil.getProperty("service.cdn")) + realPath;
							result.put("error", 0);
							result.put("url", realUrl);
							result.put("path", realPath);
							result.put("filename", file.getOriginalFilename());
						}else{
							result.put("error", 1);
							result.put("message", handler.getErrorMessage());
						}
					}
				}
			}
		}
		//将map转为json
		JSON json = JSONSerializer.toJSON(result); 
		return json.toString();
	}
}