package com.alienlab.system.service;

import org.apache.log4j.Logger;

import com.alienlab.db.ExecResult;
import com.alienlab.response.JSONResponse;
import com.alienlab.system.controller.ChangePwdController;
import com.alienlab.utils.Azdg;

public class ChangePwdService {
	private static Logger logger = Logger.getLogger(ChangePwdService.class);
	public ExecResult changePwd(String login_name, String login_pwd ,String new_login_pwd) {
		ExecResult result = new ExecResult();
		JSONResponse jsonResponse = new JSONResponse();
		Azdg azdg = new Azdg();
		String azdg_pwd = azdg.encrypt(login_pwd);
		String select = "SELECT * FROM `tb_user` WHERE user_loginname = '"+login_name+"' AND user_pwd = '"+azdg_pwd+"'";
		logger.error("changepwd service select>>>"+select);
		ExecResult rep = jsonResponse.getSelectResult(select, null, "");
		if(rep.getResult() > 0){
			String azdg_new_pwd = azdg.encrypt(new_login_pwd);
			String update = "UPDATE `tb_user` SET user_pwd = '"+azdg_new_pwd+"' WHERE user_loginname = '"+login_name+"'";
			logger.error("changepwd service update>>>"+update);
			ExecResult rep_ = jsonResponse.getExecResult(update, null, "", "");
			if(rep_.getResult() > 0){
				result.setResult(true);
				result.setMessage("新密码保存成功");
			}else{
				result.setResult(false);
				result.setMessage("新密码保存失败");
			}
		}else{
			logger.error("changepwd service error>>>"+rep.toString());
			result.setResult(false);
			result.setMessage("密码不正确");
		}
		return result;
	}

	public ExecResult getSystemAdmin(String flag) {
		// TODO Auto-generated method stub
		ExecResult result = new ExecResult();
		JSONResponse jsonResponse = new JSONResponse();
		String sql = "SELECT * FROM `system_key` a LEFT JOIN `tb_user` b ON a.`admin_login_name` = b.`user_loginname` WHERE a.flag = '"+flag+"'";
		result = jsonResponse.getSelectResult(sql, null, "");
		return result;
	}

}
