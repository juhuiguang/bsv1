package com.alienlab.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;
import com.alienlab.utils.Azdg;

public class LoginService {
	public ExecResult doLogin(String loginName,String loginPwd){
		//JSONObject jsonObject =(JSONObject) JSONObject.parse(params);
		//String loginName = jsonObject.getString("login_name");
		//String loginPwd = jsonObject.getString("login_pwd"); 
		String sql = "SELECT * FROM `tb_user` WHERE user_loginname = '"+loginName+"' ";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult result = jsonResponse.getSelectResult(sql, null, "");
		if(result.getResult() > 0){
			JSONArray rep =  (JSONArray)result.getData();
			JSONObject rep_ = rep.getJSONObject(0); 
			
			if(rep_.getString("user_pwd").equals(new Azdg().encrypt(loginPwd))){
				ExecResult result_ = new ExecResult(true , "查询成功");
				result_.setData(rep_);
				return result_;
			}else{
				return new ExecResult(false , "密码错误");
			}
		}else{
			return new ExecResult(false , "用户名不存在");
		}
	}
}
