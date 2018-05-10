package cn.itcast.demo;


import org.junit.Test;

public class DemoTest {
	@Test
	public void Test1() {
		char[] arrs = {'a','b','c'};
		System.out.println(new String(arrs));
		byte[] bts = {12,32,45};
		System.out.println(new String(bts));
	}
}
