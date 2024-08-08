package com.market.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.post.bo.PostBO;
import com.market.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	//
	
	@Autowired
	private PostBO postBO;
	
	// 중고거래 화면
	@GetMapping("/post-list-view")
	public String postListView(Model model) {
		
		List<Post> postList = postBO.getPostList();
		
		model.addAttribute("postList", postList);
		
		return "post/postList";
	}
	
	// 글쓰기 화면
	@GetMapping("/post-create-view")
	public String postCreateView(HttpSession session) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		return "post/postCreate";
	}
	
	// 찜한 목록 화면
	@GetMapping("/like-view")
	public String likeView(HttpSession session, Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		model.addAttribute("userId", userId);
		
		return "post/like";
	}
	
	// 나의 거래 현황
	@GetMapping("/state-view")
	public String stateView() {
		return "post/state";
	}
}
