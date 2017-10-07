package com.alienlab.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;
import com.alienlab.utils.Azdg;

public class UserManageService {

	public ExecResult get_search(String name_no) {
		// TODO Auto-generated method stub
		String sql ="SELECT * FROM tb_user a  WHERE a.`user_name` LIKE '"+name_no+"%' OR a.`user_name` LIKE '%"+name_no+"' OR a.`user_name` LIKE '%"+name_no+"%' OR a.`user_loginname` LIKE '%"+name_no+"' OR a.`user_loginname`LIKE '"+name_no+"%' OR a.`user_loginname`LIKE '%"+name_no+"'";
		JSONResponse jr = new JSONResponse();
		ExecResult result = jr.getSelectResult(sql, null, "tb_menu");
		return result;
	}

	public ExecResult load_delete(String user_loginname) {
		List<String> sqls=new ArrayList<String>();
		
		sqls.add( "DELETE  FROM  tb_user   WHERE user_loginname='"+user_loginname+"'");
		 
		JSONResponse jr = new JSONResponse();
		ExecResult result = jr.getExecResult(sqls,"删除成功！","删除失败");
		return result;
	}

	public ExecResult save_table(Map<String, String> param) {
		String sql1="insert into tb_user (user_loginname,user_pwd,user_name,user_type,user_purview,user_status) values "
				+ "(";
			sql1+="'"+param.get("user_loginname")+"',";
		sql1+="'"+param.get("user_pwd")+"',";
		sql1+="'"+param.get("user_name")+"',";
		sql1+="'"+param.get("user_type")+"',";
		sql1+="'"+param.get("user_purview")+"',";
		sql1+="'"+param.get("user_status")+"')";
		
		JSONResponse response=new JSONResponse();
		ExecResult result= response.getExecInsertId(sql1,null,"新用户创建成功","新用户创建失败");
		return result;
	}

	public ExecResult change_table(Map<String, String> param) {
		String sql;
		sql= "UPDATE tb_user  SET user_name="+"'"+param.get("user_name")+"'"+", user_type="+"'"+param.get("user_type")+"'"+", user_purview="+"'"+param.get("user_purview")+"'"+" ,user_status= "+"'"+param.get("user_status")+"'"+ " WHERE user_loginname= "+"'"+param.get("user_loginname")+"'";
		JSONResponse response=new JSONResponse();
		ExecResult result= response.getExecResult(sql,null,"修改成功","修改失败");
		return result;	
	}

	public ExecResult changepwd(String user_loginname, String user_type) {
		String sql="";
		String pwd="111111";
		if(user_type.equals("学生")){
			pwd="123456";
		}
		Azdg az=new Azdg();
		pwd=az.encrypt(pwd);
		sql= "UPDATE tb_user a SET a.`user_pwd`='"+pwd+"' WHERE a.`user_loginname`='"+user_loginname+"'";
		JSONResponse response=new JSONResponse();
		ExecResult result= response.getExecResult(sql,null,"密码重置成功","密码重置失败");
		return result;	
	}

}
