package com.alienlab.common;

public class ErrorCodeUtils {

	/**
	 * 得到错误代码信息
	 * 
	 * @param code 错误代码
	 * @return
	 */
	public static String getCodeMsg(int code) {
		if (code == 1001) {
			return "学年学期编号为空";
		}else if(code == 1002){
			return "文件导入地址不存�?";
		}
		return "";
	}

}
