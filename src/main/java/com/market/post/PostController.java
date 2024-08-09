package com.market.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.like.bo.LikeBO;
import com.market.post.bo.CardViewBO;
import com.market.post.bo.PostBO;
import com.market.post.bo.PostImageBO;
import com.market.post.domain.CardView;
import com.market.post.domain.Image;
import com.market.post.domain.Post;
import com.market.user.bo.UserBO;
import com.market.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private CardViewBO cardViewBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private PostImageBO postImageBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// 중고거래 화면
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value="listOrder", required=false) String listOrder,
			Model model) {
		
		//List<Post> postList = postBO.getPostList();
		
		//model.addAttribute("postList", postList);
	
		List<CardView> cardViewList = null;
		if (listOrder == null) {
			cardViewList = cardViewBO.generateCardViewList();
		} else {
			cardViewList = cardViewBO.generateCardViewList(listOrder);
		}
		
		model.addAttribute("cardViewList", cardViewList);
		
		if (listOrder != null) {
			return "post/postList :: postListFragment";
		}
		
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
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		model.addAttribute("userId", userId);
		
		return "post/like";
	}
	
	// 나의 거래 현황
	@GetMapping("/state-view")
	public String stateView(HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		model.addAttribute("userId", userId);
		
		return "post/state";
	}
	
	// 글 상세 화면
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		Image image = postImageBO.getImageByPostId(postId);
		Post post = postBO.getPostByPostId(postId);
		UserEntity writeUser = userBO.getUserEntityByLoginId(post.getUserId());
		boolean filledLike = likeBO.filledLikeByPostIdUserId(postId, userId);
		
		model.addAttribute("image", image);
		model.addAttribute("post", post);
		model.addAttribute("writeUser", writeUser);
		model.addAttribute("filledLike", filledLike);
		
		return "post/postDetail";
	}
}
