package com.alienlab.common;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest  工具�?
 * @author
 */
public class RequestUtils{
	
   /**
    * 获取int  类型变量
    * @param  request
    * @param  name
    * @return 
    */
   public static int Int(HttpServletRequest request,String name)
   {
	   return Int(request, name,0);
   }
   
   public static String String(HttpServletRequest request,String name)
   {
	   Object param= request.getParameter(name);
	   return param!=null ? param.toString() : "";
   }
   
   public static String String(HttpServletRequest request,String name,String defaultValue)
   {
	   Object param= request.getParameter(name);
	   return param!=null ? param.toString() : defaultValue;
   }
   
   public static boolean Boolean(HttpServletRequest request,String name,boolean defaultValue)
   {
	   Object param= request.getParameter(name);
	   if(param!=null)
	   {  
		   String str = param.toString().trim().toLowerCase();
		   return str.equals("true") || str.equals("1") ? true : false; 
	   }
	   else
	   {
		   return defaultValue;
	   }
   }
   
   public static boolean Boolean(HttpServletRequest request,String name)
   {
	   return Boolean(request, name,false);
   }
   
   public static int Int(HttpServletRequest request,String name,int defaultValue)
   {  
	   Object param= request.getParameter(name);
	   if(param==null)
	   {
		  return defaultValue;
	   }
	   try
	   {
		 return  Integer.parseInt(param.toString());
	   }
	   catch(Exception e){}
	   return defaultValue;
   }
}
