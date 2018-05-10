package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class ImageAction extends BaseAction<Object> {
	private File imgFile;
	private String imgFileFileName;
	private String imgFileContentType;

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}


	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	@Action(value = "image_upload", results = { @Result(name = "success", type = "json") })
	public String upload() {
		try {
			//将接收的文件复制本地磁盘的upload文件夹下
			String savePath = ServletActionContext.getServletContext().getRealPath("/");
			savePath = savePath + "upload/";
			String saveUrl = ServletActionContext.getRequest().getContextPath() + "/upload";
			//防止同名文件被覆盖
			String uuid = UUID.randomUUID().toString();
			String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
			String randomName = uuid + ext;
			File destFile = new File(savePath,randomName);
			FileUtils.copyFile(imgFile, destFile);
			
			//获取上传文件的显示在后台的页面
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", 0);
			
			map.put("url",saveUrl + "/" + randomName);
			ActionContext.getContext().getValueStack().push(map);
			return SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	
	@Action(value="image_manager",results={@Result(name="success",type="json")})
	public String manager(){
		
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "upload/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = ServletActionContext.getRequest().getContextPath() + "/upload/";
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		
		File currentPathFile = new File(rootPath);
		//遍历目录取的文件信息
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Map<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", rootPath);
		result.put("current_url", rootUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
}
