package com.itheima.a_annotation.demo3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 核心运行类:在核心运行类使用反射
 * @author admin
 *
 */
public class CoreRunner {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		/**
		 * 获得测试类的Class.
		 * 获得Class中的所有的方法.
		 * 遍历每个方法,查看每个方法上是否有MyTest注解.
		 * 有MyTest注解,这个方法就执行.
		 */
		
		// 1.获得测试类的Class:
		Class clazz = AnnotationDemo1.class;
		// 2.获得Class中的所有的方法: 规定了测试的方法必须是public.
		Method[] methods = clazz.getMethods();//MyTest注解的权限修饰时public
		// 3.遍历每个方法:
		for(Method method:methods){
			boolean flag = method.isAnnotationPresent(MyTest.class);//Method父类方法
			// System.out.println(method.getName()+ "     " + flag);
			if(flag){
				// 说明方法上有MyTest注解:
				method.invoke(clazz.newInstance(), null);//没有参数，不知道传递什么参数
			}
		}
	}
}
