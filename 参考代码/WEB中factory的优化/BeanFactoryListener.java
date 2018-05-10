package com.itheima.web.listener;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itheima.utils.BeanFactory;

/**
 * 因为每次创建实例都得解析xml文件，为提高效率，
 * 思考：能不能只进行一次解析，然后使用工厂创建实例的时候直接在解析过的数据中查找需要的相关数据
 *
 */
public class BeanFactoryListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent arg0)  { 
    	
    	try {
    		//使用dom4j对xml文件进行解析，context
        	InputStream is = arg0.getServletContext().getResourceAsStream("WEB-INF/classes/applicationContext.xml");
        	SAXReader reader = new SAXReader();
			Document read = reader.read(is);
			List<Element> elments = read.selectNodes("//bean[@id]");
			for (Element element : elments) {				
				BeanFactory.getMap().put(element.attributeValue("id"), element.attributeValue("class"));
			}
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
    	
        
    }

	
    public void contextDestroyed(ServletContextEvent arg0)  { 
        
    }
	
}
