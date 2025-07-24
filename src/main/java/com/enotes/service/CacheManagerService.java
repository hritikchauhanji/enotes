package com.enotes.service;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.Cache;

public interface CacheManagerService {

	public Collection<String> getCache();
	
	public Cache getCacheName(String cacheName);
	
	public void removeAllCache();
	
	public void removeCacheById(List<String> cacheNames);
}
