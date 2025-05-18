package com.enotes.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.endpoint.CacheEndpoint;
import com.enotes.service.CacheManagerService;
import com.enotes.util.CommonUtil;

@RestController
public class CacheController implements CacheEndpoint {
	
	@Autowired
	private CacheManagerService cacheManagerService;

	@Override
	public ResponseEntity<?> getAllCache() {
		Collection<String> cache = cacheManagerService.getCache();
		return  CommonUtil.createBuildResponse(cache, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCache(String cache_name) {
		Cache cacheName = cacheManagerService.getCacheName(cache_name);
		return  CommonUtil.createBuildResponse(cacheName, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeAllCache() {
		cacheManagerService.removeAllCache();
		return CommonUtil.createBuildResponseMessage("Remove all cache", HttpStatus.OK);
	}

}
