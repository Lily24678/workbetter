package com.itheima.a_annotation.demo3;

import org.junit.Test;

/**
 * 自定义注解MyTest，让加有@MyTest运行
 */
public class AnnotationDemo1 {
	@MyTest
	public void demo1(){
		System.out.println("demo1执行了...");
	}
	
	@MyTest
	public void demo2(){
		System.out.println("demo2执行了...");
	}
	@Test
	public void demo3(){
		System.out.println("demo3执行了...");
	}
}
