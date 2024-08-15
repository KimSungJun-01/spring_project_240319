package com.market.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.user.bo.UserBO;
import com.market.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	// 로그인 화면
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
	// 회원가입 화면
	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "user/signUp";
	}
	
	// 로그아웃 API
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/user/sign-in-view";
	}
	
	// 회원정보 화면
	@GetMapping("/info-view")
	public String infoView(HttpSession session, Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		String userLoginId = (String)session.getAttribute("userLoginId");
		UserEntity user = userBO.getUserEntityByLoginId(userLoginId);
		
		model.addAttribute("user", user);
		
		return "user/info";
	}
	
	// 평가하기 화면
	@GetMapping("/evaluate-view")
	public String evaluate(
			@RequestParam("buyerId") int buyerId,
			Model model) {
		
		// 구매자의 id로 해당 유저 정보 가져오기
		UserEntity buyUser = userBO.getUserEntityById(buyerId);
		
		model.addAttribute("buyUser", buyUser);
		
		return "user/evaluate";
	}
}
