package com.alienlab.cache.ehcache;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheService {
	private static Logger logger = Logger.getLogger(CacheService.class);
	public static Cache dbcache ;
	public static void init(String path){
		CacheManager manager = CacheManager.newInstance(path);
		dbcache= manager.getCache("dbcache");
		logger.info("ehcache has started>>>"+JSON.toJSONString(dbcache.getCacheConfiguration()));
	}
}
