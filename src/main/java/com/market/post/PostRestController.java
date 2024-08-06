package com.market.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.market.post.bo.PostBO;
import com.market.post.bo.PostImageBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private PostImageBO postImageBO;
	
	// 글쓰기
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("price") int price,
			@RequestParam("address") String address,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam("files") List<MultipartFile> files,
			HttpSession session) {	
		
		int userId = (int) session.getAttribute("userId");
		
		// DB insert
		int postId = postBO.addPost(userId, subject, price, address, content);
		postImageBO.addImages(postId, files);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
