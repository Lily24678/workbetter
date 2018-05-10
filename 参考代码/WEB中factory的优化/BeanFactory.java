package com.itheima.utils;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
	private static Map<String,String> map = new HashMap<String,String>();
	public static Object getInstance(String id){
		/*第二种方式*/
		try {
			return Class.forName(map.get(id)).newInstance();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
		/*
		try {
			
			 *  第一种方式
			
			//使用dom4j解析xml文件,类加载器
			SAXReader reader = new SAXReader();
			Document document = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			//使用XPath协助解析
			Element elment = (Element) document.selectSingleNode("//bean[@id='"+id+"']");
			String value = elment.attributeValue("class");
//			System.out.println(value);
			//反射生产实例
			Class clazz = Class.forName(value);
			return clazz.newInstance();	
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}*/
				
	}
	public static Map<String, String> getMap() {
		return map;
	}


}
