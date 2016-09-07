package com.mendao.common.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

import com.mendao.common.enums.AttachTypeEnum;
import com.mendao.common.util.ImageStandardEnum;
import com.mendao.common.util.StringUtil;

public class ImageHandler {
	
	private final String IMAGE_FOLDER = AttachTypeEnum.IMAGE.getCode().toLowerCase(); 
	
	private ImageHandler(){
		
	}
	
	public static ImageHandler instance(){
		return new ImageHandler();
	}
	
	public int getWidth(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%w"); // 设置获取宽度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println("运行指令出错!");
		}
		return line;
	}

	/**
	 * 获得图片的高度
	 * 
	 * @param imagePath
	 *            文件路径
	 * @return 图片高度
	 */
	public int getHeight(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();

			op.format("%h"); // 设置获取高度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println("运行指令出错!" + e.toString());
		}
		return line;
	}
	
	
	
	public boolean zoomImage(String imagePath, String newPath, Integer width,
	        Integer height) {

	    boolean flag = false;
	    try {
	        IMOperation op = new IMOperation();
	        op.addImage(imagePath);
	        if (width == null) {// 根据高度缩放图片
	            op.resize(null, height);
	        } else if (height == null) {// 根据宽度缩放图片
	            op.resize(width);
	        } else {
	            op.resize(width, height);
	        }
	        op.addImage(newPath);
	        ConvertCmd convert = new ConvertCmd(true);
	        convert.run(op);
	        flag = true;
	    } catch (IOException e) {
	        System.out.println("文件读取错误!");
	        flag = false;
	    } catch (InterruptedException e) {
	        flag = false;
	    } catch (IM4JavaException e) {
	        flag = false;
	    } finally {

	    }
	    return flag;
	}
	
	/**
	 * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
	 * 
	 * @param imagePath
	 *            源图片路径
	 * @param newPath
	 *            处理后图片路径
	 * @param width
	 *            缩放后的图片宽度
	 * @param height
	 *            缩放后的图片高度
	 * @return 返回true说明缩放成功,否则失败
	 */
	protected boolean zoomImage(String imagePath, String newPath, Integer width,
			Integer height, BigDecimal quality) {

		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			op.addRawArgs("-scale", width + "x" + height + "!");
			op.addRawArgs("-gravity", "center");
			op.addRawArgs("-extent", width + "x" + height);
			BigDecimal value = new BigDecimal(100).multiply(quality);
			op.addRawArgs("-quality", Integer.toString(value.intValue()));
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {

		}
		return flag;
	}
	
	public boolean cutImage(String imagePath, String newPath, int x, int y,
			int width, int height) {
		boolean flag = false;
		try {
			imagePath = imagePath.replaceAll("//", "/");
			newPath = newPath.replaceAll("//", "/");
			createDir(newPath);
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			/** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
			op.crop(width, height, x, y);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {

		}
		return flag;
	}
	
	
	public void compressImage(String oldImageFolder, String newImageFolder) {
		if (StringUtil.isBlank(oldImageFolder)) {
			System.out.print("未指定文件源目录");
			return;
		}
		File oldFolder = new File(oldImageFolder);
		if (!oldFolder.exists()) {
			System.out.print("源文件不存在");
			return;
		}
		File newFolder = new File(newImageFolder);
		if (!newFolder.isDirectory()) {
			System.out.print("目标不是一个目录");
			return;
		}
		this.compress(oldFolder, newImageFolder);
	}
	
	private void createImage(File file, ImageStandardEnum imageStandardEnum,
			String newPath) {
		String folder = File.separator + "attached" + File.separator + IMAGE_FOLDER + File.separator;
		String original = folder + "original" + File.separator;
		int height = this.getHeight(file.getAbsolutePath());
		int width = this.getWidth(file.getAbsolutePath());
		//String path = file.getAbsolutePath().replaceFirst(p, p + imageStandardEnum.getFolderName() + File.separator);
		String path = newPath
				+ folder
				+ imageStandardEnum.getFolderName()
				+ File.separator
				+ file.getAbsolutePath().substring(
						file.getAbsolutePath().indexOf(original) + original.length(),
						file.getAbsolutePath().lastIndexOf(File.separator));
		this.createDir(path);
		if (height == 0 || width == 0) {
			this.copy(file.getAbsolutePath(),
					path + File.separator + file.getName());// 发生异常进行文件复制
			return;
		}
		int size = this.getSize(file.getAbsolutePath());
		BigDecimal heightDecimal = null;
		BigDecimal widthDecimal = null;
		// System.out.println(file.getAbsolutePath() +
		// " ---height:"+height+"---width:"+width+"-----------" + size);
		if (size > 30720) {// 小于30KB 不处理
			heightDecimal = new BigDecimal(imageStandardEnum.getRatio()
					* height);
			widthDecimal = new BigDecimal(imageStandardEnum.getRatio() * width);
		} else {
			heightDecimal = new BigDecimal(height);
			widthDecimal = new BigDecimal(width);
		}

		this.zoomImage(file.getAbsolutePath(),
				path + File.separator + file.getName(),
				widthDecimal.intValue(), heightDecimal.intValue(),
				new BigDecimal(imageStandardEnum.getRatio()));
	}
	
	private int getSize(String imagePath) {
		int size = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(imagePath);
			size = inputStream.available();
			inputStream.close();
			inputStream = null;
		} catch (FileNotFoundException e) {
			size = 0;
			System.out.println("文件未找到!");
		} catch (IOException e) {
			size = 0;
			System.out.println("读取文件大小错误!");
		} finally {
			// 可能异常为关闭输入流,所以需要关闭输入流
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("关闭文件读入流异常");
				}
				inputStream = null;
			}
		}
		return size;
	}
	
	private void copy(String fileFrom, String fileTo) {
		try {
			FileInputStream in = new java.io.FileInputStream(fileFrom);
			FileOutputStream out = new FileOutputStream(fileTo);
			byte[] bt = new byte[1024];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			in.close();
			out.close();
		} catch (IOException ex) {
			System.out.println("文件复制错误:" + ex);
		}
	}
	
	/**
	 * 图片处理，指定文件进行压缩，压缩成大、中、小三个比例
	 * 
	 * 存储到目标目录中
	 * 
	 * @param file
	 *            待压缩的文件
	 * 
	 * @param newPath
	 *            目标目录
	 */
	private void handle(File file, String newPath) {
		//this.createImage(file, ImageStandardEnum.MAX, newPath);
		this.createImage(file, ImageStandardEnum.MID, newPath);
		this.createImage(file, ImageStandardEnum.MIN, newPath);
	}
	
	/**
	 * 递归遍历文件，进行处理
	 * 
	 * @param 源文件目录
	 * @param newPath
	 *            目标目录
	 */
	private void compress(File f, String newPath) {
		if (f != null) {
			if (f.isDirectory()) {
				File[] fileArray = f.listFiles();
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						compress(fileArray[i], newPath);
					}
				}
			} else {
				this.handle(f, newPath);
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
			return setDirPormission(file);
		}else if(!file.canWrite()){
			return setDirPormission(file);
		}
		
		return true;
	}
	
	private boolean setDirPormission(File file){
//		try {
//			Runtime.getRuntime().exec("chmod 777 " + file.getPath());
//		} catch (IOException e) {
//			return false;
//		}
		return true;
	}
}
