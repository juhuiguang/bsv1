package com.alienlab.system.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alienlab.db.ExecResult;
import com.alienlab.system.service.UserManageService;
import com.alienlab.utils.Azdg;
@Controller 
public class UserManage {
	@RequestMapping(value="get_search.do",method=RequestMethod.POST)
	@ResponseBody
	public void get_search(HttpServletRequest request , HttpServletResponse response) {
		String name_no = request.getParameter("name_no");
		String result = "";
		UserManageService service = new UserManageService();
		System.out.println("get_search.do in>>>>");
		result = (service.get_search(name_no)).toString(); 
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@RequestMapping(value="load_delete.do",method=RequestMethod.POST)
	@ResponseBody
	public void load_delete(HttpServletRequest request , HttpServletResponse response) {
		String user_loginname=request.getParameter("user_loginname");
		String result = "";
		if(user_loginname==null)
		{
			ExecResult rs = new ExecResult(false,"数据获取失败！");
			result = rs.toString();
		}
		else
		{
			UserManageService service = new UserManageService();
			result = (service.load_delete(user_loginname)).toString();
		}

		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@RequestMapping(value="save_table.do",method=RequestMethod.POST)
	@ResponseBody
	public void save_table(HttpServletRequest request , HttpServletResponse response){
		String user_loginname=request.getParameter("user_loginname");
		String user_name=request.getParameter("user_name");
		Azdg a=new Azdg();
		String user_pwd = a.encrypt(request.getParameter("user_pwd"));
		String user_purview=request.getParameter("user_purview");
		String user_type=request.getParameter("user_type");
		String user_status=request.getParameter("user_status");
		Map<String,String>param=new HashMap();	
		param.put("user_loginname", user_loginname);
		param.put("user_name", user_name);
		param.put("user_pwd", user_pwd);
		param.put("user_purview", user_purview);
		param.put("user_type",user_type);
		param.put("user_status", user_status);

		UserManageService service = new UserManageService();
		String result=(service.save_table(param)).toString();
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping(value="change_table.do",method=RequestMethod.POST)
	@ResponseBody
	public void change_table(HttpServletRequest request , HttpServletResponse response){
		String user_loginname=request.getParameter("user_loginname");
		String user_name=request.getParameter("user_name");
		String user_purview=request.getParameter("user_purview");
		String user_type=request.getParameter("user_type");
		System.out.println(user_type + "++++++++++++++++++++++");
		String user_status=request.getParameter("user_status");

		
		Map<String,String>param=new HashMap();	
		param.put("user_loginname", user_loginname);
		param.put("user_name", user_name);
		param.put("user_purview", user_purview);
		param.put("user_type",user_type);
		param.put("user_status", user_status);

		UserManageService service = new UserManageService();
		String result=(service.change_table(param)).toString();
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="changepwd.do",method=RequestMethod.POST)
	@ResponseBody
	public void changepwd(HttpServletRequest request , HttpServletResponse response) {
		String user_loginname = request.getParameter("user_loginname");
		String user_type = request.getParameter("user_type");
		String result = "";
		UserManageService service = new UserManageService();
		result = (service.changepwd(user_loginname,user_type)).toString();
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
