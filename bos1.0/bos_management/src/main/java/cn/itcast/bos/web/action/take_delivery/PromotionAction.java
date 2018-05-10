package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.take_delivery.PromotionService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {
	// 注入业务层
	@Autowired
	private PromotionService promotionService;
	private File titleImgFile;
	private String titleImgFileFileName;

	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	@Action(value = "promotion_save", results = { @Result(name = "success", type = "redirect", location = "pages/take_delivery/promotion.html") })
	public String save() {
		try {
			//防止同文件名被覆盖
			String randomFileNameString = UUID.randomUUID().toString()+titleImgFileFileName.substring(titleImgFileFileName.indexOf("."));
			File destFile = new File(ServletActionContext.getServletContext().getRealPath("/")+"upload/", randomFileNameString);
			FileUtils.copyFile(titleImgFile, destFile);
			String titleImg = ServletActionContext.getRequest().getContextPath() + "/upload/" + randomFileNameString;
			model.setTitleImg(titleImg);
			promotionService.save(model);
			return SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	
	}
	
	@Action(value="promotion_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Promotion> pagedata = promotionService.pageQuery(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pagedata.getSize());
		map.put("rows", pagedata.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
}
