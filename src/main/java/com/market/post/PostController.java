package com.market.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.cardview.bo.CardViewBO;
import com.market.cardview.domain.CardView;
import com.market.like.bo.LikeBO;
import com.market.like.domain.Like;
import com.market.post.bo.PostBO;
import com.market.post.bo.PostImageBO;
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
			@RequestParam(value = "listOrder", required = false) String listOrder,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			Model model) {
		
		Integer totalPosts = postBO.getPostListCount();
		SetPage setPage = new SetPage(currentPage, totalPosts);
		
		List<CardView> cardViewList = null;
		if (listOrder == null) {
			cardViewList = cardViewBO.generateCardViewList(setPage.getLimitStart(), setPage.getPostsPerPage());
		} else {
			cardViewList = cardViewBO.generateCardViewList(listOrder, setPage.getLimitStart(), setPage.getPostsPerPage());
		}

		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("startPage", setPage.getStartPage());
		model.addAttribute("endPage", setPage.getEndPage());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", setPage.getPrev());
		model.addAttribute("next", setPage.getNext());
		model.addAttribute("listOrder", listOrder);
		
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
	public String likeView(
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		// like 테이블에서 사용자가 좋아요를 누른 좋아요 객체 배열 가져오기
		List<Like> pushedLikeList = likeBO.getLikeListByUserId(userId);
		Integer totalPosts = pushedLikeList.size();
		SetPage setPage = new SetPage(currentPage, totalPosts);
		
		List<Like> pushedLikeListPaging = likeBO.getLikeListByUserIdAndLimitStartAndPostsPerPage(userId, setPage.getLimitStart(), setPage.getPostsPerPage());
		
		// pushedLikeList의 각 개체가 가지고 있는 postId에 해당하는 게시물 cardViewList로 만들기
		List<CardView> cardViewList = cardViewBO.generateMyLikeCardViewList(pushedLikeListPaging);
		
		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("startPage", setPage.getStartPage());
		model.addAttribute("endPage", setPage.getEndPage());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", setPage.getPrev());
		model.addAttribute("next", setPage.getNext());
		
		return "post/myLikeProduct";
	}
	
	// 내가 올린 제품 현황
	@GetMapping("/my-product-view")
	public String myProductView(
			@RequestParam("currentPage") Integer currentPage,
			HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId);
		SetPage setPage = new SetPage(currentPage, postList.size());
		
		List<CardView> cardViewList = cardViewBO.generateMyCardViewList(userId, setPage.getLimitStart(), setPage.getPostsPerPage());
		
		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("startPage", setPage.getStartPage());
		model.addAttribute("endPage", setPage.getEndPage());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", setPage.getPrev());
		model.addAttribute("next", setPage.getNext());
		
		return "post/myProduct";
	}
	
	// 내가 거래요청한 제품 현황
	@GetMapping("/my-exchange-view")
	public String myExchangeView(
			@RequestParam("currentPage") Integer currentPage,
			HttpSession session, Model model) {
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		Integer totalPosts = postBO.getPostListCountByMyExchange(userId);
		SetPage setPage = new SetPage(currentPage, totalPosts);
		
		List<CardView> cardViewList = cardViewBO.generateRequestExchangeCardViewList(userId, setPage.getLimitStart(), setPage.getPostsPerPage());
		
		
		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("startPage", setPage.getStartPage());
		model.addAttribute("endPage", setPage.getEndPage());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", setPage.getPrev());
		model.addAttribute("next", setPage.getNext());
				
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
		UserEntity writeUser = userBO.getUserEntityById(post.getUserId());
		boolean filledLike = likeBO.filledLikeByPostIdUserId(postId, userId);
		
		model.addAttribute("image", image);
		model.addAttribute("post", post);
		model.addAttribute("writeUser", writeUser);
		model.addAttribute("filledLike", filledLike);
		
		return "post/postDetail";
	}
	
}
