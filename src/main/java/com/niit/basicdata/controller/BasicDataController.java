package com.niit.basicdata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niit.basicdata.service.DepartmentService;
import com.niit.basicdata.service.MajorService;
import com.niit.basicdata.service.UserService;

@Controller 
public class BasicDataController {
	@RequestMapping(value="/basicdata/department/getdepartments.interface")
	@ResponseBody
	public String getDepartments(HttpServletRequest request){
		DepartmentService service=new DepartmentService();
		return service.getDepartments().toString();
	}

	@RequestMapping(value="/basicdata/department/getdepartments.do")
	@ResponseBody
	public String getDepss(HttpServletRequest request){
		DepartmentService service=new DepartmentService();
		return service.getDepartments().toString();
	}
	
	@RequestMapping(value="/basicdata/department/getdepartment.interface")
	@ResponseBody
	public String getDepartment(HttpServletRequest request){
		String dep_no=request.getParameter("dep_no");
		DepartmentService service=new DepartmentService();
		return service.getDepartment(dep_no).toString();
	}
	
	@RequestMapping(value="/basicdata/department/getTeachingDep.interface")
	@ResponseBody
	public String getTeachingDep(HttpServletRequest request){
		DepartmentService service=new DepartmentService();
		return service.getTeachingDep().toString();
	}
	
	@RequestMapping(value="/basicdata/major/getMajors.interface")
	@ResponseBody
	public String getMajors(HttpServletRequest request){
		MajorService service=new MajorService();
		return service.getMajors().toString();
	}
	
	@RequestMapping(value="/basicdata/major/getDepMajors.interface")
	@ResponseBody
	public String getDepMajors(HttpServletRequest request){
		String dep_no=request.getParameter("dep_no");
		MajorService service=new MajorService();
		return service.getDepMajors(dep_no).toString();
	}
	
	@RequestMapping(value="/basicdata/user/getUsers.interface")
	@ResponseBody
	public String getUsers(HttpServletRequest request){
		String type=request.getParameter("usertype");
		String page=request.getParameter("page");
		String length=request.getParameter("length");
		UserService service=new UserService();
		return service.getUsers(type, page, length).toString();
	}
	
	@RequestMapping(value="/basicdata/user/getUsersByLoginname.interface")
	@ResponseBody
	public String getUsersByLoginname(HttpServletRequest request){
		String type=request.getParameter("usertype");
		String loginname=request.getParameter("loginname");
		UserService service=new UserService();
		return service.getUsersByLoginname(loginname, type).toString();
	}
	
	@RequestMapping(value="/basicdata/user/getUsersByName.interface")
	@ResponseBody
	public String getUsersByName(HttpServletRequest request){
		String type=request.getParameter("usertype");
		String name=request.getParameter("username");
		UserService service=new UserService();
		return service.getUsersByName(name, type).toString();
	}
	
	@RequestMapping(value="/basicdata/user/addUser.interface")
	@ResponseBody
	public String addUser(HttpServletRequest request){
		String usertype=request.getParameter("usertype");
		String username=request.getParameter("username");
		String loginname=request.getParameter("loginname");
		String loginpwd = request.getParameter("loginpwd");
		UserService service=new UserService();
		return service.addUser(loginname, username, usertype ,loginpwd).toString();
	}
	@RequestMapping(value="/basicdata/user/getUserByUnLn.interface")
	@ResponseBody
	public String getUserByUnLn(HttpServletRequest request){
		
		String userInfo=request.getParameter("userInfo");
		
		
		UserService service=new UserService();
		return service.getUserByUnLn(userInfo).toString();
	}
	public static void main(String [] args){
		UserService service=new UserService();
		System.out.println(service.getUsers("教师", null,null));
	}
	
}
