package com.itheima.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getUUIDFileName(String fileName){
		return UUID.randomUUID().toString().replace("-", "")+"_"+fileName;
	}
}
