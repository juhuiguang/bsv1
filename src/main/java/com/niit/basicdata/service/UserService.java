package com.niit.basicdata.service;

import com.alienlab.common.TypeUtils;
import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;
import com.alienlab.utils.Azdg;

public class UserService {
	public ExecResult getUsers(String type,String page,String length){
		String sql="SELECT * FROM tb_user WHERE user_status='1'  ";
		if(type!=null&&!type.equals("")){
			sql+="and user_type='"+type+"' ";
		}
		sql+=" ORDER BY user_loginname ";
		sql+=getPageSql(page,length);
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "tb_user");
	}
	
	public ExecResult getUsersByLoginname(String loginname,String type){
		String sql="SELECT * FROM tb_user WHERE user_status='1'  ";
		if(type!=null&&!type.equals("")){
			sql+="and user_type='"+type+"' ";
		}
		if(loginname!=null&&!loginname.equals("")){
			sql+=" and user_loginname like '%"+loginname+"%' ";
		}
		sql+=" ORDER BY user_loginname ";
		sql+=getPageSql(null,null);
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "tb_user");
	}
	
	public ExecResult getUsersByName(String name,String type){
		String sql="SELECT * FROM tb_user WHERE user_status='1'  ";
		if(type!=null&&!type.equals("")){
			sql+="and user_type='"+type+"' ";
		}
		if(name!=null&&!name.equals("")){
			sql+=" and user_name like '%"+name+"%' ";
		}
		sql+=" ORDER BY user_loginname ";
		sql+=getPageSql(null,null);
		 JSONResponse jr=new JSONResponse();
		 return jr.getSelectResult(sql, null, "tb_user");
	}
	
	public ExecResult addUser(String loginname,String username,String usertype , String loginpwd){
		String sql = "insert into tb_user('user_loginname','user_name','user_type','user_status','user_createtime','user_pwd')"
				+ " values (''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'')";
		Azdg a=new Azdg();
		if(loginpwd == null || "".equals(loginpwd)){
			loginpwd = "111111"; 
		}
		String pwd=a.encrypt(loginpwd);
		 JSONResponse jr=new JSONResponse();
		 return jr.getExecInsertId(sql, new String[]{loginname,username,usertype,"1",TypeUtils.getTime(),pwd}, "用户创建成功", "用户创建失败");
	}
	
	private String getPageSql(String page,String length){
		if(page!=null&&!page.equals("")){
			int p=Integer.parseInt(page);
			int l=50;
			if(length!=null&&!length.equals("")){
				l=Integer.parseInt(length);
			}
			return "LIMIT "+((p-1)*l+1)+","+l;
		}else{
			return "LIMIT 0,5000 ";
		}
	}

	public ExecResult getUserByUnLn(String userInfo) {
		String sql="SELECT * FROM tb_user WHERE user_status='1' AND (user_loginname ='"+userInfo+"' OR user_name = '"+userInfo+"')";
		
		 JSONResponse jr=new JSONResponse();
		 ExecResult result = jr.getSelectResult(sql, null, "tb_user");
		 return result;
	}
}
