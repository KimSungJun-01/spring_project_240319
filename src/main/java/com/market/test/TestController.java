package com.market.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.post.mapper.PostMapper;

@Controller
public class TestController {
	
	@Autowired
	private PostMapper postMapper;
	
	@ResponseBody
	@GetMapping("/test-first")
	public String test1() {
		return "<h1>Hello world!<h1>";
	}
	
	@ResponseBody
	@GetMapping("/test-second")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		return map;
	}
	
	@GetMapping("/test-third")
	public String test3() {
		return "test/test";
	}
	
	@ResponseBody
	@GetMapping("test-fourth")
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostListTest();
	}
}
