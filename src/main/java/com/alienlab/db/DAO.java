package com.alienlab.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alienlab.cache.ehcache.CacheService;
import com.alienlab.common.TypeUtils;
import com.alienlab.utils.Azdg;

import net.sf.ehcache.Element;

public class DAO {
	private static Logger logger = Logger.getLogger(DAO.class);
	private String flag=null;
	public DAO() {
		try {
			this.flag=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DAO(String flag){
		try {
			this.flag=flag;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cacheresult(long start,long end,String sql,Object datas){
		if(end-start>3000){//执行时间大于3秒，自动执行缓存
			String key=Azdg.strMD5(sql);
			Element e=new Element(key, datas);
			CacheService.dbcache.put(e);
			logger.info("exec more than 3 seconds,add to cache."+"key="+key+",sql="+sql);
		}
	}
	
	private Object getResultFromCache(String sql){
		String key=Azdg.strMD5(sql);
		if(CacheService.dbcache!=null && CacheService.dbcache.isKeyInCache(key)){
			logger.info("find resultset in cache."+"key="+key+",sql="+sql);
			return CacheService.dbcache.get(key);
		}else{
			return null;
		}
	}
	/**
	 * 获取返回数据总行
	 * 
	 * @param sql
	 * @return
	 */
	public int getDataCount(String sql) {
		int count = 0;
		ResultSet resSet = null;
		DruidPooledConnection conn = null;
		Statement stmt = null;
		try {
			// sql=sql.toUpperCase();
			
			if (!TypeUtils.isEmpty(sql)) {
				Object result=getResultFromCache(sql);
				if(result!=null){
					return (Integer)result;
				}
				long start=System.currentTimeMillis();
				if(resSet==null){
					conn = DataSourceManager.getConn(flag);
					stmt = conn.createStatement();
					resSet = stmt.executeQuery(sql);
					
				}
				if(resSet.next()) { 
					count=resSet.getInt("totalCount"); 
				}
				long end=System.currentTimeMillis();
				cacheresult(start,end,sql,count);
				
			} else {
				logger.info("SQL 输入语法错误或没有可用的连接");
				return 0;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("SQL 输入语法错误或没有可用的连接---" + sql);
		}finally {
			closeResultSet(resSet);
			closeStatement(stmt);
			closeConnection(conn);
		}
		return count;
	}

	/**
	 * 获取返回数据
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getDataSet(String sql) {
		List<Map<String, Object>> datas = null;
		// PreparedStatement pstmt=null; //使用子类报错，不知道为啥�??
		ResultSet resSet = null;
		DruidPooledConnection conn = null;
		Statement stmt = null;
		try {
			// sql=sql.toUpperCase();
			if (!TypeUtils.isEmpty(sql) ) {
				Object result=getResultFromCache(sql);
				if(result!=null){
					return (List<Map<String, Object>>)result;
				}
				long start=System.currentTimeMillis();
				conn = DataSourceManager.getConn(flag);
				stmt = conn.createStatement();
				resSet = stmt.executeQuery(sql);
				// pstmt=conn.prepareStatement(sql);
				
				ResultSetMetaData rsmd = resSet.getMetaData();
				// 取得结果集列�??
				int columnCount = rsmd.getColumnCount();
				// 构�?�泛型结果集
				datas = new ArrayList<Map<String, Object>>();
				Map<String, Object> data = null;
				// 循环结果�??
				while (resSet.next()) {
					data = new HashMap<String, Object>();
					// 每循环一条将列名和列值存入Map
					for (int i = 1; i <= columnCount; i++) {
						data.put(rsmd.getColumnLabel(i).toUpperCase(),
								TypeUtils.getString(resSet.getObject(rsmd.getColumnLabel(i))));
					}
					// 将整条数据的Map存入到List�??
					datas.add(data);
				}
				long end=System.currentTimeMillis();
				cacheresult(start,end,sql,datas);
				return datas;
			} else {
				logger.info("SQL 输入语法错误或没有可用的连接");
				return new ArrayList<Map<String, Object>>();
			}
		} catch (Exception e) {
			logger.error("SQL 输入语法错误或没有可用的连接---" + sql);
		} finally {
			closeResultSet(resSet);
			closeStatement(stmt);
			closeConnection(conn);
		}
		return datas;
	}

	/**
	 * 删除、更新语�?
	 * 
	 * @param sql
	 * @return
	 */
	public boolean execCommand(String sql) {
		boolean bool = false;
		Statement pstmt = null;
		DruidPooledConnection conn = null;
		try {
			if (!TypeUtils.isEmpty(sql)) {
				conn = DataSourceManager.getConn(flag);
				pstmt = conn.createStatement();
				int row = pstmt.executeUpdate(sql);
				if (row > 0) {
					bool = true;
				}
			}
		} catch (Exception e) {
			logger.error("SQL===" + sql + "\n" + e.getMessage());
		} finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
		return bool;
	}

	public Object execInsertId(String sql) {
		Statement pstmt = null;
		DruidPooledConnection conn = null;
		try {
			conn = DataSourceManager.getConn(flag);
			if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(conn)) {
				pstmt = conn.createStatement();
				if (pstmt.executeUpdate(sql) > 0) {
					sql = "SELECT @@IDENTITY AS id";
					ResultSet rs = pstmt.executeQuery(sql);
					if (rs.next()) {
						return rs.getObject(1);
					}
					return null;
				} else {
					return null;
				}

			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("SQL===" + sql + "\n" + e.getMessage());
			return null;
		} finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	/**
	 * 删除、更新�?�增�?? 防止sql 依赖注入
	 * 
	 * @param sql
	 * @return
	 */
	public boolean execCommandPrepared(String sql, Map<String, Object> map) {
		boolean bool = false;
		PreparedStatement pstmt = null;
		DruidPooledConnection conn = null;
		try {
			conn = DataSourceManager.getConn(flag);
			if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(map) && !TypeUtils.isEmpty(conn)) {
				pstmt = conn.prepareStatement(sql);
				for (String key : map.keySet()) {
					pstmt.setObject(TypeUtils.getInt(key), map.get(key));
				}
				int row = pstmt.executeUpdate();
				if (row > 0) {
					bool = true;
				}
			}
		} catch (Exception e) {
			logger.error("SQL===" + sql + "\n" + e.getMessage());
		} finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
		return bool;
	}

	/**
	 * 批量插入
	 * 
	 * @param sql
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public boolean executeBatch(List<String> sql) throws SQLException {
		boolean bool = false;
		// 生产connection
		Connection conn=null;
		try {
			conn = DataSourceManager.getConn(flag);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement sm = null;
		int[] result = null;
		sm = conn.createStatement();
		try {
			// 获得当前Connection的提交模�??
			boolean autoCommit = conn.getAutoCommit();
			// 关闭自动提交模式
			conn.setAutoCommit(false);
			sm = conn.createStatement();
			// 遍历sql
			for (String s : sql) {
				sm.addBatch(s);
			}
			// 执行批量更新
			result = sm.executeBatch();
			for (int v : result) {
				if (v >= 0) {
					bool = true;
				} else {
					return bool;
				}
			}
			// 提交事务
			conn.commit();
			// 还原提交模式
			conn.setAutoCommit(autoCommit);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			closeStatement(sm);
			closeConnection(conn);
		}
		return bool;
	}

	public static void closeStatement(Statement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("close PreparedStatement error", e);
		}
	}

	public static void closeConnection(Connection dbConnection) {
		try {
			if (dbConnection != null && (!dbConnection.isClosed())) {
				dbConnection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("close connection error", e);
		}
	}

	public static void closeResultSet(ResultSet res) {
		try {
			if (res != null) {
				res.close();
				res = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("close ResultSet error", e);
		}
	}
}
