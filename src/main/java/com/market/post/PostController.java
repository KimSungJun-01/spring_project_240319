package com.market.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.like.bo.LikeBO;
import com.market.like.domain.Like;
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
		
		// like 테이블에서 사용자가 좋아요를 누른 좋아요 객체 배열 가져오기
		List<Like> pushedLikeList = likeBO.getLikeListByUserId(userId);
		
		// pushedLikeList의 각 개체가 가지고 있는 postId에 해당하는 게시물 cardViewList로 만들기
		List<CardView> cardViewList = cardViewBO.generateMyLikeCardViewList(pushedLikeList);
		
		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("userId", userId);
		
		return "post/myLikeProduct";
	}
	
	// 내가 올린 제품 현황
	@GetMapping("/my-product-view")
	public String myProductView(HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		List<CardView> cardViewList = cardViewBO.generateMyCardViewList(userId);
		
		model.addAttribute("userId", userId);
		model.addAttribute("cardViewList", cardViewList);
		
		return "post/myProduct";
	}
	
	// 내가 거래요청한 제품 현황
	@GetMapping("/my-exchange-view")
	public String myExchangeView(HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
				
		List<CardView> cardViewList = cardViewBO.generateRequestExchangeCardViewList(userId);
		
		model.addAttribute("userId", userId);
		model.addAttribute("cardViewList", cardViewList);
				
		return "post/myExchange";
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
