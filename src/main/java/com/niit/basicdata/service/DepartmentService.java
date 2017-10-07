package com.niit.basicdata.service;

import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;

public class DepartmentService {
	/**
	 * 获得所有部门数据
	 * @return ExecResult
	 */
	public ExecResult getDepartments(){
		String sql="SELECT`dep_no`,`dep_name`,`dep_type`,`dep_cddw_no`,`dep_sort`,`dep_abbreviation` FROM `base_department` ";
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "base_department");
	}
	
	/**
	 * 获取指定部门数据
	 * @param dep_no 部门编码
	 * @return ExecResult
	 */
	public ExecResult getDepartment(String dep_no){
		if(dep_no==null||dep_no.equals("")){
			return new ExecResult(false,"未正确传入部门编码");
		}else{
			String sql="SELECT`dep_no`,`dep_name`,`dep_type`,`dep_cddw_no`,`dep_sort`,`dep_abbreviation` FROM `base_department` where dep_no='"+dep_no+"' ";
			 JSONResponse jr=new JSONResponse();
			 return jr.getSelectResult(sql, null, "base_department");
		}
		
	}
	
	/**
	 * 获取所有教学部门
	 * @return ExecResult
	 */
	public ExecResult getTeachingDep(){
		String sql="SELECT`dep_no`,`dep_name`,`dep_type`,`dep_cddw_no`,`dep_sort`,`dep_abbreviation` FROM `base_department`  where dep_type='教学'";
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "base_department");
	}
}
