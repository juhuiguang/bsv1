package com.alienlab.system.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.DAO;
import com.alienlab.db.ExecResult;
import com.alienlab.system.service.ChangePwdService;

@Controller
public class ChangePwdController {
	private static Logger logger = Logger.getLogger(ChangePwdController.class);
	@RequestMapping(value="changePwd.interface",method=RequestMethod.POST)
	@ResponseBody 
	public void changePwd(HttpServletRequest request , HttpServletResponse response){ 
		//String config =request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		
		String loginname=request.getParameter("login_name");
		String login_pwd=request.getParameter("login_pwd");
		String new_login_pwd=request.getParameter("new_login_pwd");
		logger.info("go into changepwd interface>>>"+loginname+","+login_pwd+","+new_login_pwd);
		ChangePwdService changePwdService = new ChangePwdService();
		ExecResult rep = changePwdService.changePwd(loginname , login_pwd,new_login_pwd);
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(rep.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="getSystemAdmin.interface",method=RequestMethod.POST)
	@ResponseBody 
	public void getSystemAdmin(HttpServletRequest request , HttpServletResponse response){ 
		//String config =request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		String flag=request.getParameter("flag");
		ChangePwdService changePwdService = new ChangePwdService();
		ExecResult rep = changePwdService.getSystemAdmin(flag);
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(rep.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
