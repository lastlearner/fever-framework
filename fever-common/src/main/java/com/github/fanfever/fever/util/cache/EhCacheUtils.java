package com.github.fanfever.fever.util.cache;

import com.github.fanfever.fever.util.SpringContextHolder;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public class EhCacheUtils {
	private static CacheManager cacheManager = ((CacheManager) SpringContextHolder.getBean("cacheManager"));

	private static final String DEFAULT_CACHE = "default_cache";
	private static final String SYS_CACHE = "sysCache";
	private static final String SESSION_CACHE = "sessionCache";

	public static Session getSession(Serializable id) {
		Element element = getCache(SESSION_CACHE).get(id);
		if (null == element) {
			return null;
		}
		return (Session) element.getObjectValue();
	}

	public static List<Session> getSessionAll() {
		return (List<Session>) getCache(SESSION_CACHE).getKeys().stream().map(i -> {
			Session session = getSession((Serializable) i);
			return session;
		}).collect(Collectors.toList());
	}

	public static void putSession(Serializable id, Session session) {
		Element element = new Element(id, session);
		getCache(SESSION_CACHE).put(element);
	}

	public static void removeSession(Serializable id) {
		getCache(SESSION_CACHE).remove(id);
	}

	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	public static Object getDefault(String key) {
		return get(DEFAULT_CACHE, key);
	}

	public static void putDefault(String key, Object value) {
		put(DEFAULT_CACHE, key, value);
	}

	public static void remove(String key) {
		remove(SYS_CACHE, key);

	}

	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	/**
	 * 获得一个Cache，没有则创建一个。
	 */
	private static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
}
