package com.market.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.like.bo.LikeBO;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name =  "postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		likeBO.likeToggle(postId, userId);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
