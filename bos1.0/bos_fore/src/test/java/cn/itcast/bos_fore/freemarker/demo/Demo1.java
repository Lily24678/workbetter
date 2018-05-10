package cn.itcast.bos_fore.freemarker.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.itcast.bos.domain.constant.Constant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Demo1 {
	@Test
	public void test1() throws IOException, Exception{
		//创建Configuration（配置对象）
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
		//配置生成FreeMarker模板文件的位置
		configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/FreeMarkerDemo/"));
		//获取模板对象
		Template template = configuration.getTemplate("demo1.ftl");
		//动态数据对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "freemarker");
		map.put("msg", "hello world");
		//合并输出
		template.process(map, new PrintWriter(System.out));
	}

}
