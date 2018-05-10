package web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;


@WebServlet("/UploadServlet1")
@MultipartConfig
public class UploadServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求体中指定的的Part对象
				Part part = request.getPart("upload");		
				//得到上传文件的文件名
				String header = part.getHeader("Content-Disposition");
				int indexOf = header.indexOf("filename=\"");//使用转义符\
				String filename = header.substring(indexOf+10, header.length()-1);
				System.out.println(filename);
				InputStream is = part.getInputStream();
				/*文件重名问题*/
				String uuid = UUIDUtils.getUUID();
				String uuidFileName = uuid+"_"+filename;
				/*一个文件夹下存储过多的文件会导致打开速度变慢*/
				String path = request.getServletContext().getRealPath("/upload");//文件上传到tomcat中的位置	
				String realPath = path+UploadUtils.getPath(uuidFileName);
				File file = new File(realPath);
				if(!file.exists()){
					file.mkdirs();
				}
				//创建输出流					
				OutputStream os = new FileOutputStream(realPath+"/"+uuidFileName);
				//使用Apache commons的工具commons-io-1.4.jar
				IOUtils.copy(is, os);
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(is);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
