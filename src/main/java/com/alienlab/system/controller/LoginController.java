package com.alienlab.system.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.system.service.LoginService;

@Controller 
public class LoginController {
	//对外开放的登录接口
	@RequestMapping(value="login.interface",method=RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest request ,HttpServletResponse response){
		HttpSession session = request.getSession();
		LoginService ls=new LoginService();
		String result = "";
		//参数
		//String config =request.getParameter("config");
		String username=request.getParameter("login_name");
		String pwd=request.getParameter("login_pwd");
		//判断是否通过
		ExecResult rep = ls.doLogin(username,pwd);
		//session 变量
		if(rep.getResult() > 0){ 
			JSONObject usermap = (JSONObject) rep.getData();
			session.setAttribute("secret",new java.util.Date().getTime());
			session.setAttribute("loginname", usermap.getString("login_name"));
			session.setAttribute("username", usermap.getString("user_name"));
			session.setAttribute("usertype", usermap.getString("user_type"));
			session.setAttribute("userid", usermap.getString("user_id"));
			session.setAttribute("userPurview", usermap.getString("user_purview"));
		}
		result = rep.toString();
	
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
