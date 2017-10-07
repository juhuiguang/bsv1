package com.alienlab.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.utils.Azdg;
import com.alienlab.utils.PropertyConfig;



public class AdminFilter implements Filter {
	private static  Logger logger = Logger.getLogger("登录拦截器");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		request.setCharacterEncoding("utf-8");
		HttpServletResponse rep = (HttpServletResponse) response;
		response.setCharacterEncoding("utf-8");
		String path = req.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		
		HttpSession session=req.getSession(true);
		PropertyConfig pc=new PropertyConfig("sysConfig.properties");
	/*	String loginpath=pc.getValue("loginpath");//登陆页面*/		
		String requrl = req.getRequestURL().toString(); 
	/*    String requri=req.getServletPath().toString().trim();*/
		String ip = req.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("客户端主机地址"+ip);
		logger.info("请求页面地址"+requrl);
		//登录页不进行过滤
		/*if(requrl.indexOf(loginpath)>=0){
			logger.info("是登录页面，不进行拦截");
			chain.doFilter(request, response);
			return;
		}
		if(requrl.indexOf("index_sso.jsp")>=0){
			logger.info("是单点登录页面，不进行拦截");
			chain.doFilter(request, response);
			return;
		}*/
		//不在配置文件制定目录的页面，也不进行过滤
		String filterpath=pc.getValue("filterPath");
		if(requrl.indexOf(filterpath)<0||requrl.indexOf("addCusinfo.jsp")>=0){
			logger.info("非拦截目录，不进行拦截");
			chain.doFilter(request, response);
			return;
		}

		//解析参数
		logger.info(request.getParameter("config"));
		String flag = request.getParameter("flag");
		
		String config = request.getParameter("config");
		logger.info(config); 
		String token = request.getParameter("token");
		logger.info("request param:"+flag+","+config+","+token);
		try{
			if(Azdg.judgeToken(flag, config, token)){
				JSONObject param=JSONObject.parseObject(config);
				logger.info("begin params convert:"+param.toJSONString());
				Map<String,String> newparam=new HashMap<String,String>();
				 for (Map.Entry<String, Object> entry : param.entrySet()) {
					 logger.info("get param:"+entry.getKey()+":"+entry.getValue());
					 newparam.put(entry.getKey() , String.valueOf(entry.getValue()));
			      }
				 logger.info("new params is:"+JSON.toJSONString(newparam));
				request=new ParamWrapper((HttpServletRequest)request, newparam);
				 logger.info("new request param is:"+JSON.toJSONString(request.getParameterMap()));
				 logger.info("new request querystring is:"+JSON.toJSONString(((HttpServletRequest)request).getQueryString()));
				chain.doFilter(request, response);
			}else{
				System.err.println("请求错误");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*String param=java.net.URLEncoder.encode(az.encrypt(requrl),"UTF-8");
		logger.info("加密后且encode后的url:"+param);
		loginpath+="?url="+param;
		logger.info("过滤器生成的登录地址"+basePath+loginpath);
		Object secret=session.getAttribute("secret");
		if(secret==null){
			if(requrl.indexOf("/mobile/")>0){
				logger.info("移动端未登录，跳转登录>>>>"+basePath+"/mobile/"+loginpath);
				rep.sendRedirect(basePath+"/mobile/"+loginpath);
			}else{
				logger.info("未登录，跳转登录>>>>"+basePath+loginpath);
				rep.sendRedirect(basePath+loginpath);
			}
			return;
		}else{
//			Long time=(Long)session.getAttribute("secret");
//			if((new Date().getTime()-time)>(1000*60*60)){
//				logger.info("登录超时，跳转登录界面>>>>"+basePath+loginpath);
//				rep.sendRedirect(basePath+loginpath);
//				return;
//			}
		}*/
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
