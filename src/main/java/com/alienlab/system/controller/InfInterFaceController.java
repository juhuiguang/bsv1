package com.alienlab.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.system.service.InfInterFaceService;
@Controller 
public class InfInterFaceController {
	/**
	 *获取本人的所有的信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value="getAllInf.interface",method=RequestMethod.POST)
	@ResponseBody
	public String getAllInf(HttpServletRequest request){
		InfInterFaceService infInterFaceService = new InfInterFaceService();
		//String config = request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		String teacher_no =  request.getParameter("teacher_no");  
		String start_time =   request.getParameter("start_time");  
		String end_time =   request.getParameter("end_time");  
		String month_start =  request.getParameter("month_start");  
		String month_finish =   request.getParameter("month_finish");
		String inf_system =  request.getParameter("inf_system");  
		String menu_value =  request.getParameter("menu_value"); 
		String result = infInterFaceService.getAllInf(teacher_no,start_time,end_time,month_start,month_finish,inf_system,menu_value).toString();
		return result; 
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="postInfData.interface",method=RequestMethod.POST)
	@ResponseBody
	public String postInfData(HttpServletRequest request){
		InfInterFaceService infInterFaceService = new InfInterFaceService();
		//String config = request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		String inf_type =   request.getParameter("inf_type");  
		String inf_importance =  request.getParameter("inf_importance");  
		String inf_content =  request.getParameter("inf_content");  
		String inf_post =   request.getParameter("inf_post");  
		String inf_get_name =  request.getParameter("inf_get_name");  
		String inf_get =  request.getParameter("inf_get");  
		String inf_system =  request.getParameter("inf_system");  
		String inf_return_url = request.getParameter("inf_return_url");  
		String [] params = {inf_type,inf_importance,inf_content,inf_post,inf_get_name,inf_get,inf_system,inf_return_url};
		
		String result = infInterFaceService.postInfData(params).toString();
		return result; 
	}
	
	
	/**
	 * 改变信息的已读未读状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changeStatus.interface",method=RequestMethod.POST)
	@ResponseBody
	public String changeStatus(HttpServletRequest request){
		InfInterFaceService infInterFaceService = new InfInterFaceService();
		//String config = request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		String inf_no =  request.getParameter("inf_no");  
		String result = infInterFaceService.changeStatus(inf_no).toString();
		return result; 
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deleteInf.interface",method=RequestMethod.POST)
	@ResponseBody
	public String deleteInf(HttpServletRequest request){
		InfInterFaceService infInterFaceService = new InfInterFaceService();
		//String config = request.getParameter("config");
		//JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		String inf_no =  request.getParameter("inf_no");  
		String result = infInterFaceService.deleteInf(inf_no).toString();
		return result; 
	}
}
