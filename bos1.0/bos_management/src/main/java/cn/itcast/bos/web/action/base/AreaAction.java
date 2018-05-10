package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {
	// 注入业务层
	@Autowired
	private AreaService areaService;
	// 接收参数
	private File file;
	private String fileFileName;

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/* 区域的批量导入功能 */
	@Action(value = "area_batchImport", results = { @Result(name = "success", type = "redirect", location = "./pages/base/area.html") })
	public String batchImport() throws IOException {
		// 标志位，导入是否成功，1成功 2失败
		int flag = 1;
		try {

			List<Area> list = new ArrayList<Area>();
			Workbook workbook;
			// 加载Excel文件.xls
			if (fileFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(file));

			} else {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}

			// 选着sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 遍历sheet表单的每行
			for (Row row : sheet) {
				// 一行数据对应一个区域对象，跳过表头
				if (row.getRowNum() == 0) {
					continue;
				}
				// 跳过空行
				if (row.getCell(0) == null
						|| StringUtils.isBlank(row.getCell(0)
								.getStringCellValue())) {
					continue;
				}
				// 将表格的数据封装
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());
				area.setProvince(row.getCell(1).getStringCellValue());
				area.setCity(row.getCell(2).getStringCellValue());
				area.setDistrict(row.getCell(3).getStringCellValue());
				area.setPostcode(row.getCell(4).getStringCellValue());

				// 城市编码和区域简码
				String province = area.getProvince();
				String city = area.getCity();
				String district = area.getDistrict();
				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);
				String[] headArray = PinYin4jUtils.getHeadByString(province
						+ city + district);
				StringBuffer shortCode = new StringBuffer();
				for (String headS : headArray) {
					shortCode.append(headS);
				}
				area.setShortcode(shortCode.toString());

				String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
				area.setCitycode(cityCode);

				list.add(area);
			}
			// 调用业务层将数据存储到数据库中
			areaService.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		}
		ServletActionContext.getResponse().getWriter().println(flag);
		return SUCCESS;
	}

	/* 分页查询，带条件 */
	@Action(value = "area_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		// 创键Pageable
		Pageable pageable = new PageRequest(page-1, rows);
		// 添加附加条件
		Specification<Area> spec = new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> pList = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(model.getProvince())) {
					Predicate p1 = cb.like(root.get("province")
							.as(String.class), "%" + model.getProvince() + "%");
					pList.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					Predicate p2 = cb.like(root.get("city").as(String.class),
							"%" + model.getCity() + "%");
					pList.add(p2);
				}
				if (StringUtils.isNotBlank(model.getDistrict())) {
					Predicate p3 = cb.like(root.get("district")
							.as(String.class), "%" + model.getDistrict() + "%");
					pList.add(p3);
				}
				return cb.and(pList.toArray(new Predicate[0]));
			}
		};

		Page<Area> pageData = areaService.pageQuery(pageable, spec);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(map);

		return SUCCESS;
	}
}
