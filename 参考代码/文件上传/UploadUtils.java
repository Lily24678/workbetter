package com.itheima.utils;

public class UploadUtils {

	public static String getPath(String uuidFileName){
		// 使用唯一文件名.hashCode();
		int code1 = uuidFileName.hashCode();
		int d1 = code1 & 0xf; // 获得到1级目录.
		int code2 = code1 >>> 4;
		int d2 = code2 & 0xf; // 获得到2级目录.
		return "/"+d1+"/"+d2;
	}
}
