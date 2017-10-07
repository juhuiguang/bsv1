package com.alienlab.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataSourceManager {
	private static  Logger logger = Logger.getLogger(DataSourceManager.class);
	private static Map<String,DruidDataSource> DataSources=new Hashtable();
	
	private static JSONArray dsconfig=null;
	
	/**
	 * 获得默认链接
	 * @return
	 * @throws Exception
	 */
	protected  static DruidPooledConnection getConn() throws Exception{
		if(DataSources.containsKey("default")){
			DruidDataSource ds=DataSources.get("default");
			return ds.getConnection();
		}else{
			throw new Exception("default connection is not define");
		}
	}
	
	protected  static DruidPooledConnection getConn(String flag) throws Exception{
		if(flag==null){
			return getConn();
		}
		if(DataSources.containsKey(flag)){
			DruidDataSource ds=DataSources.get(flag);
			return ds.getConnection();
		}else{
			throw new Exception(flag+" connection is not define");
		}
	}
	
	public static void loadConfig(String path){
		logger.info("load datasource config from "+path);
		String fullFileName = path;
        File file = new File(fullFileName);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        System.out.println(buffer.toString());
        dsconfig=JSONArray.parseArray(buffer.toString());
        
        CreatePools();
	}
	
	private static void CreatePools(){
		if(dsconfig==null){
			logger.error("datasource config is null.");
			return;
		}
		for(int i=0;i<dsconfig.size();i++){
			JSONObject ds=dsconfig.getJSONObject(i);
			String flag=ds.getString("conn_flag");
			String type=ds.getString("conn_type");
			boolean isauto=ds.getBooleanValue("conn_autoconnect");
			if(isauto){
				DruidDataSource dds=new DruidDataSource();
				dds.setDriverClassName(ds.getString("conn_driver"));
				dds.setUrl(ds.getString("conn_connstr"));
				dds.setUsername(ds.getString("conn_user"));
				dds.setPassword(ds.getString("conn_pwd"));
				dds.setMaxActive(ds.getIntValue("conn_poolsize"));
				dds.setInitialSize(ds.getIntValue("conn_poolsize"));
				dds.setTestWhileIdle(ds.getBooleanValue("testWhileIdle"));
				dds.setValidationQuery(ds.getString("validationQuery"));
				dds.setQueryTimeout(600);
				try {
					dds.init();
					dds.getConnection(1000*60*60);
					logger.info("create pool success>>>"+ds.toJSONString());
					logger.info(flag+" connection count is:"+dds.getConnectCount());
					DataSources.put(flag, dds);
					//如果是默认连接
					if(type.equals("default")){
						DataSources.put("default", dds);
						logger.info("default connect is>>>"+ds.toJSONString());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("create pool error>>>>"+ds.toJSONString());
				}
			}
			
		}
	}
}
