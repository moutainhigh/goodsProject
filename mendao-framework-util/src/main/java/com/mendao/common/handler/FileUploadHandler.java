package com.mendao.common.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mendao.common.enums.AttachTypeEnum;
import com.mendao.common.util.StringUtil;
import com.mendao.util.PropertiesUtil;

public class FileUploadHandler {

	private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	private final String curFolder = format.format(new Date());
	private long unit = 1024 * 1024;
	
	
	private long maxSize = unit * 1;
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
	private String uploadPath;
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	private String filePath;
	public String getFilePath() {
		return filePath;
	}
	
	private double quality;
	public double getQuality() {
		return quality;
	}
	public void setQuality(double quality) {
		this.quality = quality;
	}
	private Map<String, Object> attribute;
	
	public Map<String, Object> getAttribute() {
		return attribute;
	}
	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	protected FileUploadHandler(String uploadPath){
		if(StringUtil.isBlank(uploadPath)){
			uploadPath = this.getClass().getResource("/static").getPath() + File.separator;
		}
		this.uploadPath = uploadPath;
		this.attribute = new HashMap<String, Object>();
	}
	
	public static FileUploadHandler instance(){
		String uploadPath = PropertiesUtil.getProperty("service.upload_path");
		return new FileUploadHandler(uploadPath);
	}
	
	public static FileUploadHandler instance(String uploadPath){
		return new FileUploadHandler(uploadPath);
	}
	
	/**
	 * 移动文件
	 * @param original
	 * @param destination
	 * @return
	 */
	public static boolean move(String original, String destination){
		File file = new File(original);
		if(!file.exists()){
			return false;
		}
		
		File dest = new File(destination);
		File dir = dest.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		if(!dir.canWrite()){
			try {
				Runtime.getRuntime().exec("chmod 777 " + file.getPath());
			} catch (IOException e) {
				return false;
			}
		}
		file.renameTo(dest);
		return true;
	}
	
	/**
	 * 上传文件保存到设置的临时目录
	 * @param file
	 * @param prefix
	 * @return
	 */
	public boolean save(MultipartFile file, String prefix){
		return save(file, prefix, true);
	}
	/**
	 * 上传文件保存到设置的目录
	 * @param file
	 * @return
	 */
	public boolean save(MultipartFile file, String prefix, boolean temp){
		
		if (!checkFileSize(file.getSize())) {
			return false;
		}
		this.attribute.put("fileSize", file.getSize());
		
		//设置文件相对路径
		filePath = File.separator + "attached" + File.separator;
		if(temp){
			filePath += "temp" + File.separator;
		}
		String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
		
		if(StringUtil.contains("png,jpg,jpeg,bmp", fileExt.toLowerCase())){
			filePath += AttachTypeEnum.IMAGE.getCode().toLowerCase() + File.separator + "original" + File.separator;
		}
		filePath += (curFolder + File.separator);
		if(StringUtil.isNotBlank(prefix)){
			filePath += prefix + File.separator;
		}
		filePath = filePath.replaceAll("//", "/");
		
		//生成文件名称
		fileName = System.currentTimeMillis() + "." + fileExt;
		
		//设置文件绝对路径
		String absolutePath = this.uploadPath + "/" + filePath;
		if(!createDir(absolutePath.replaceAll("//", "/"))){
			return false;
		}
		
		//保存文件
		File fileOut = new File(absolutePath + fileName);
		try {
			FileUtils.writeByteArrayToFile(fileOut, file.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
			this.setErrorMessage(e.getMessage());
		}
		return true;
	}
	
	
//	protected boolean checkDir(String dirPath){
//		try{
//			
//			// 若使用共享目录上传 
//			// SmbFile dir = new SmbFile(dirPath);
//			
//			
//			File dir = new File(dirPath);
//			if(!dir.exists()){
//				dir.mkdirs();
//				Runtime.getRuntime().exec("chmod 777 "+dir.getPath());
//			}
//		}catch(Exception e){
//			this.setErrorMessage("修改文件夹权限失败。" + e.getMessage());
//			return false;
//		}
//		return true;
//	}
	
	protected boolean checkFileSize(long fileSize){
		if(fileSize > this.maxSize){
			setErrorMessage("文件必须小于" + this.maxSize/unit + "M");
			return false;
		}
		return true;
	}
	
	
	private void writeLog(String message, String logType) {
		FileWriter fw = null;
		try {
			File dir = new File(PropertiesUtil.getProperty("service.log.path"));
			if(!dir.exists()){
				dir.mkdirs();
				try {
					Runtime.getRuntime().exec("chmod 777 " + dir.getPath());
				} catch (IOException e) {
					;
				}
			}
			File file = new File(dir.getAbsolutePath() + File.separator + logType + ".log");
			if(!file.exists()){
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsolutePath(), true);
			fw.write(message + "\r\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean createDir(String dirPath) {
		if(dirPath.indexOf(".") > -1){
			dirPath = dirPath.substring(0, dirPath.lastIndexOf("/"));
		}
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
//			return setDirPormission(file);
		}else if(!file.canWrite()){
//			return setDirPormission(file);
		}
		
		return true;
	}
	
	private boolean setDirPormission(File file){
		try {
			Runtime.getRuntime().exec("chmod 777 " + file.getPath());
		} catch (IOException e) {
			this.setErrorMessage("文件夹无写权限!");
			return false;
		}
		return true;
	}
}
