package com.market.map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import groovy.util.logging.Slf4j;

@Slf4j
@RequestMapping("/map")
@RestController
public class MapRestController {
	
	@Value("${naver.client-id}")
	private String NAVER_API_ID;
	
	@Value("${naver.secret}")
	private String NAVER_API_SECRET;
	
}
