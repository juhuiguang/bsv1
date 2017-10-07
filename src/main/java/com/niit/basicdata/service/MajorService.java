package com.niit.basicdata.service;

import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;

public class MajorService {
	/**
	 * 获取所有专业数据
	 * @return ExecResult
	 */
	public ExecResult getMajors(){
		String sql="SELECT a.*,b.`dep_name`,b.`dep_abbreviation`,c.`teacher_name` AS major_teacher_name,c.`teacher_name` ,d.`teacher_name` AS major_master_name "+ 
						"FROM `base_major` a,base_department b,base_teacher c ,base_teacher d "+
						"WHERE a.`dep_no`=b.dep_no AND a.major_teacher_no=c.`teacher_no` AND a.`major_master_no`=d.`teacher_no` ";
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "base_major");
	}
	
	/**
	 * 获取部门专业数据
	 * @param dep_no
	 * @return ExecResult
	 */
	public ExecResult getDepMajors(String dep_no){
		String sql="SELECT a.*,b.`dep_name`,b.`dep_abbreviation`,c.`teacher_name` AS major_teacher_name,c.`teacher_name` ,d.`teacher_name` AS major_master_name "+ 
				"FROM `base_major` a,base_department b,base_teacher c ,base_teacher d "+
				"WHERE a.`dep_no`=b.dep_no AND a.major_teacher_no=c.`teacher_no` AND a.`major_master_no`=d.`teacher_no` and a.dep_no='"+dep_no+"'";
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "base_major");
	}
	
	
}
