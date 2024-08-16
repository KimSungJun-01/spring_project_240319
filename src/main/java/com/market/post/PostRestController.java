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
import com.market.post.domain.Post;

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
		
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// DB insert
		int postId = postBO.addPost(userId, subject, price, address, content);
		postImageBO.addImages(postId, userLoginId, files);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	// 거래요청
	@PostMapping("/buy-request")
	public Map<String, Object> buyRequest(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		postBO.updatePostBuyerId(userId, postId);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	// 거래취소 요청
	@PostMapping("/refund-request")
	public Map<String, Object> refundRequest(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		postBO.cancelPostBuyerId(userId);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	// isEvaluated 변경
	@PostMapping("/update-isEvaluated")
	public Map<String, Object> updateIsEvaluated(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		// 거래글 isEvaluated true로 변경
		postBO.updateIsEvaluatedByPostId(postId);
		
		
		// 거래자 id 추출
		Post post = postBO.getPostByPostId(postId);
		int buyerId = post.getBuyerId();
		
		result.put("code", 200);
		result.put("result", "성공");
		result.put("buyerId", buyerId);
		return result;
	}
	
	@PostMapping("/delete-post")
	public Map<String, Object> deletePost(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		Post post = postBO.getPostByPostId(postId);
		int postUserId = post.getUserId();
		
		// 게시글 삭제
		postBO.deletePostById(postId);
		postImageBO.deleteImageByPostId(postId);
		
		result.put("code", 200);
		result.put("result", "성공");
		result.put("userId", postUserId);
		return result;
	}
}
