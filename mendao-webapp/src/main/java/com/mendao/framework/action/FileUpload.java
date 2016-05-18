package com.mendao.framework.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mendao.framework.enums.UserUtil;

@Controller
@RequestMapping("/fileUpload")
@SessionAttributes(types = UserUtil.class)
public class FileUpload {
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void handleFileUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
		String result = "";
		List<MultipartFile> files = request.getFiles("fileUpload");
		if(files.size() > 0){
			MultipartFile file = files.get(0);
			String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"  
                    + file.getOriginalFilename();
		}
		response.getWriter().write("上传成功");;
//		   try {  
//	            String path;  
//	            path = "C://";  
//	            FileUtils.copyFile(file, new File(path));  
//	            result = "{flag:\"1\",msg:\"操作成功\",path:\""  
//	                    + HandlerPath.getHostSimulateAuthorLogoPath()  
//	                    + fileFileName + "\",picName:\"" + fileFileName + "\"}";  
//	            return SUCCESS;  
//	        } catch (Exception e) {  
//	            e.printStackTrace();  
//	            result = "{flag:\"0\",msg:\"操作失败\"}";  
//	            return SUCCESS;  
//	        } 
	}
}
