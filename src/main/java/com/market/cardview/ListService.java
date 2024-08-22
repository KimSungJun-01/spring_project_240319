package com.market.cardview;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListService {
	
	Page<Map<String, Object>> list(Map<String, Object> paramMap, Pageable page);
}
