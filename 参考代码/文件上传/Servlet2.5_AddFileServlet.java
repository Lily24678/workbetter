package com.itheima.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.fujb.commons.CommonsUtil;

@WebServlet("/AddFileServlet")
public class AddFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {
		// 使用FileUpload完成文件上传
		/**
		 * 使用Commons-FileUpload完成文件上传的步骤： 
		 * * 1、生成工厂类 DiskFileItemFactory 
		 * * 2、生成解析器类 ServletFileUpload 
		 * * 3、解析request对象，的到表单项的对象的集合List<FileItem>
		 * * 4、遍历list集合，获取相关表单项的值
		 */

		// 创建工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置文件的缓存路径
		factory.setRepository(new File("D:/upload"));
		// 设置上传的文件大于多少的时候需要缓存，即大小限制
		factory.setSizeThreshold(1024 * 1024 * 3);

		// 创建解析器类，需要工厂类作为参数
		ServletFileUpload fileUpload = new ServletFileUpload(factory);

		// 如果文件名出现中文，并且中文乱码，就可以通过一下设置规避
		fileUpload.setHeaderEncoding("UTF-8");

		try {
			// 解析request，得到表单项对象的集合
			List<FileItem> list = fileUpload.parseRequest(request);

			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					// 是普通表单项
					// 获取普通项的值
					// String value = fileItem.getString();
					// 对普通项中文乱码的处理
					String value = fileItem.getString("UTF-8");
					// 获取当前表单项的name的值
					String fieldName = fileItem.getFieldName();

					System.out.println("表单项name ： " + fieldName + ", 值是： " + value);

				} else {
					// 是文件表单项
					// 获取文件名
					String fileName = fileItem.getName();
					System.out.println("文件名： " + fileName);
					// 获取唯一文件名
					fileName = CommonsUtil.UUID() + "_" + fileName;

					// 目录分离
					String hexString = Integer.toHexString(fileName.hashCode());
					String path = hexString.charAt(0) + "/" + hexString.charAt(1);

					File destFile = new File("D:/upload/" + path);
					destFile.mkdirs();

					File file = new File(destFile, fileName);
					OutputStream out = new FileOutputStream(file);

					// 文件流
					InputStream in = fileItem.getInputStream();

					// 拷贝文件流
					IOUtils.copy(in, out);
					
					//释放资源
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);

				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		}

	}

}
