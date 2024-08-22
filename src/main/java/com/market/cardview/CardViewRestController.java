package com.market.cardview;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardViewRestController {
	
	@Autowired
	private ListService listService;
	
	@RequestMapping(value = "/rest/List")
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam Map<String, Object> paramMap,
			@PageableDefault(value = 8) Pageable page) {
		
		Map<String, Object> resultMap = new HashMap<>();
		Page<Map<String, Object>> result = listService.list(paramMap, page);
		
		resultMap.put("pages", result);
		resultMap.put("size", page.getPageSize());
		return resultMap;
	}
}
