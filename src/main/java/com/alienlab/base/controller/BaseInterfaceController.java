package com.alienlab.base.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;
import com.alienlab.utils.Azdg;
import com.alienlab.utils.HttpRequest;
import com.alienlab.utils.PropertyConfig;

@Controller 
public class BaseInterfaceController {
	@RequestMapping(value="getToken.do",method=RequestMethod.POST)
	@ResponseBody 
	public void getToken(HttpServletRequest request , HttpServletResponse response){ 
		JSONObject configObject = getAllUrl(request.getParameter("config"));
		String config = configObject.toJSONString();
		//获得自己的token 
		String propertyFlag = new PropertyConfig("sysConfig.properties").getValue("flag");
		String key = new PropertyConfig("sysConfig.properties").getValue("ssokey");
		String token = null;
		token = Azdg.getToken(key, config);
		JSONObject jsonObject = new JSONObject(); 
		jsonObject.put("token", token);
		jsonObject.put("flag", propertyFlag);
		jsonObject.put("config", configObject);
		try { 
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(jsonObject.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="requestPort.do",method=RequestMethod.POST)
	@ResponseBody 
	public void requestPort(HttpServletRequest request, HttpServletResponse response){
		String config =getAllUrl(request.getParameter("config")).toJSONString();
		Map<String, String> map = new HashMap<String, String>();
		String propertyFlag = new PropertyConfig("sysConfig.properties").getValue("flag");
		map.put("flag", propertyFlag);
		map.put("config", config);
		map.put("token", Azdg.getToken(new PropertyConfig("sysConfig.properties").getValue("ssokey"), config));
		JSONObject configJson = (JSONObject) JSONObject.parse(config); 
		String rep = HttpRequest.httpRequest(configJson.getString("tourl"), map); 
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(rep); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获得key方法
	public String getKey(String flag){
		String sql="SELECT * FROM `system_key` WHERE flag='"+flag+"'";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult result = jsonResponse.getSelectResult(sql, null, "");
		if(result.getResult() > 0){
			JSONArray jsonArray = (JSONArray)result.getData();
			return jsonArray.getJSONObject(0).getString("key");
		}
		return null;
	}
	public JSONObject getAllUrl(String config){
		
		JSONObject jsonObject = (JSONObject) JSONObject.parse(config);
		if(!jsonObject.getString("simple_url").equals("false")){
			String ssourl = new PropertyConfig("sysConfig.properties").getValue("ssourl");
			String ownurl = new PropertyConfig("sysConfig.properties").getValue("rootpath");
			jsonObject.put("tourl", ssourl + jsonObject.getString("tourl"));
			jsonObject.put("fromurl", ownurl + jsonObject.getString("fromurl"));
		}
		return jsonObject;
	}
	
}
