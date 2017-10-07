package com.alienlab.system.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.alienlab.cache.ehcache.CacheService;
import com.alienlab.db.DataSourceManager;

/**
 * Servlet implementation class SystemStart
 */
public class SystemStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemStart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//加载数据库连接，创建连接池
		String dbpath=config.getServletContext().getRealPath("/")+"WEB-INF/classes/dbconfig.json";
		DataSourceManager.loadConfig(dbpath);
		String cachepath=config.getServletContext().getRealPath("/")+"WEB-INF/classes/ehcache.xml";
		//创建缓存服务
		CacheService.init(cachepath);
	}

}
