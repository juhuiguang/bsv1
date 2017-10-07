package com.alienlab.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;

public class InfInterFaceService {
	
	public ExecResult postInfData(String [] params){
		String sql = "INSERT INTO tb_inf (inf_type,inf_importance,inf_content,inf_post,inf_get_name,inf_get,inf_system,inf_return_url) VALUES (''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'',''{7}'') ";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult result = jsonResponse.getExecInsertId(sql, params, "发送成功", "发送失败");
		return result;	
	}

	public ExecResult getAllInfYes(String teacher_no,String inf_system){
		String sql = "SELECT * FROM  tb_inf a ,system_key b WHERE a.`inf_get`='"+teacher_no+"' AND a.`inf_status`='1' AND a.`inf_system`='"+inf_system+"' AND a.`inf_system`=b.`flag`  ";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult result = jsonResponse.getSelectResult(sql, null, "");
		return result;	
	}
	
	public ExecResult getAllInf(String teacher_no,String start_time,String end_time,String month_start,String month_finish,String inf_system,String menu_value){
		//获取当周信息 
		String sql="";
		if(menu_value.equals("inf_all")){
			sql= "SELECT * FROM tb_inf a ,system_key b WHERE a.`inf_get`='"+teacher_no+"'  AND a.`inf_system`='"+inf_system+"' AND a.`inf_system`=b.`flag` ORDER BY a.`inf_post_time` DESC ";
		}else if(menu_value.equals("inf_no")){
			sql= "SELECT * FROM tb_inf a ,system_key b WHERE a.`inf_get`='"+teacher_no+"'  AND a.`inf_system`='"+inf_system+"' AND a.`inf_status`='0' AND a.`inf_system`=b.`flag` ORDER BY a.`inf_post_time` DESC ";
		}else if(menu_value.equals("inf_week")){
			sql= "SELECT * FROM tb_inf a ,system_key b WHERE a.`inf_get`='"+teacher_no+"' AND a.`inf_post_time`>='"+start_time+"' AND a.`inf_post_time`<='"+end_time+"' AND a.`inf_system`='"+inf_system+"' AND a.`inf_system`=b.`flag` ORDER BY a.`inf_post_time` DESC ";
		}else if(menu_value.equals("inf_month")){
			sql="SELECT *FROM   tb_inf a ,system_key b WHERE a.`inf_get` ='"+teacher_no+"'  AND a.`inf_post_time`>='"+month_start+"' AND a.`inf_post_time`<='"+month_finish+"' AND a.`inf_system`='"+inf_system+"' AND a.`inf_system`=b.`flag` ORDER BY a.`inf_post_time` DESC ";
		}
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult result = jsonResponse.getSelectResult(sql, null, "");

		return result;
	}
	
	public ExecResult changeStatus(String inf_no){
		String sql = "UPDATE tb_inf a SET a.`inf_status`='0' WHERE a.`inf_no`='"+inf_no+"' ";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult execResult = jsonResponse.getExecResult(sql, null);
		return execResult;
	}
	public ExecResult deleteInf(String inf_no){
		String sql = "DELETE FROM tb_inf WHERE inf_no='"+inf_no+"' ";
		JSONResponse jsonResponse = new JSONResponse();
		ExecResult execResult = jsonResponse.getExecResult(sql, null);
		return execResult;
	}
}
