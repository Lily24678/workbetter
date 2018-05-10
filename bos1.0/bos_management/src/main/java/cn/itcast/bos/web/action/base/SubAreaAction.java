package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.web.action.common.BaseAction;

@Actions
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {
	// 业务层注入
	@Autowired
	private SubAreaService subAreaService;
	@Autowired
	private AreaService areaService;
	private File file;
	private String fileFileName;

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	//分页查询
	@Action(value="subArea_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable = new PageRequest(page, rows);
		Page<SubArea> pageData = subAreaService.findAll(pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}

	// 一键上传，批量加载
	@Action(value = "subarea_batchImport", results = { @Result(name = "success", type = "redirect", location = "./pages/base/sub_area.html") })
	public String batchImport() throws FileNotFoundException, IOException {
		Workbook workbook;

		if (fileFileName.endsWith(".xls")) {
			// 加载EXCEL
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}
		// 选择sheet
		Sheet sheet = workbook.getSheetAt(0);
		List<SubArea> list = new ArrayList<SubArea>();
		// 遍历sheet将每行数据封装
		for (Row row : sheet) {
			// 跳过表头
			if (row.getRowNum() == 0) {
				continue;
			}
			// 跳过空行
			if (row.getCell(0) == null
					|| StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
				continue;
			}
			SubArea subArea = new SubArea();

			subArea.setId(row.getCell(0).getStringCellValue());
			Area area = new Area();
			area.setProvince(row.getCell(1).getStringCellValue());
			area.setCity(row.getCell(2).getStringCellValue());
			area.setDistrict(row.getCell(3).getStringCellValue());
			Area area2 = areaService.findOne(area);
			subArea.setArea(area2);
			subArea.setKeyWords(row.getCell(4).getStringCellValue());
			subArea.setStartNum(row.getCell(5).getStringCellValue());
			subArea.setEndNum(row.getCell(6).getStringCellValue());
			subArea.setSingle(new Character(row.getCell(7).getStringCellValue()
					.charAt(0)));
			subArea.setAssistKeyWords(row.getCell(8).getStringCellValue());
			list.add(subArea);

		}
		// 调用业务层
		subAreaService.save(list);
		// 将文件上传到服务器
		String realPath = ServletActionContext.getServletContext().getRealPath(
				"/upload");
		File desFile = new File(realPath, fileFileName);
		FileUtils.copyFile(file, desFile);

		return SUCCESS;
	}
}
