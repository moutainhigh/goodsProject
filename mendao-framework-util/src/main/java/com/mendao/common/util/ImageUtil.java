package com.mendao.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;


public class ImageUtil {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String ymd = sdf.format(new Date());
	private String ALLOWED_EXT = "gif,jpg,jpeg,png,bmp";

	private String ERROR_EXT = "文件格式错误。";

	private String NO_FILE = "请选择文件！";
	private String SIZE_SLOP = "上传文件大小超过限制。";

	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getLocaFilePath() {
		return locaFilePath;
	}

	public void setLocaFilePath(String locaFilePath) {
		this.locaFilePath = locaFilePath;
	}

	private String locaFilePath;

	public static void cutImage(String oldImageFolder,String imagepath){
		File f = new File(imagepath);
		ImageCutHandle imageCut = new ImageCutHandle();//将文件拆分为 大中小 三分
		imageCut.compressImage(oldImageFolder, f.getParent());
	}
}
