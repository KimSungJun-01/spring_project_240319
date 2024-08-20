package com.market.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.market.common.EncryptUtils;
import com.market.like.bo.LikeBO;
import com.market.post.bo.PostBO;
import com.market.post.bo.PostImageBO;
import com.market.post.domain.Post;
import com.market.user.bo.UserBO;
import com.market.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private PostImageBO postImageBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// ID 중복확인
	@PostMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// DB에서 ID로 user 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) { // user가 비어있지 않은 경우 => ID 중복
			result.put("is_duplicated_id", true);
		} else { // user가 비어있는 경우 => ID 중복 X
			result.put("is_duplicated_id", false);
		}
		return result;
	}
	
	// 회원가입
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam("profileImage") MultipartFile profileImage) {
		
		// password 암호화 (md5 알고리즘)
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB 저장
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, phoneNumber, email, profileImage);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패해습니다.");
		}
		return result;
	}
	
	// 로그인
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// password hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB 조회
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 로그인 처리, 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// session에 사용자 정보 담기
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 200);
			result.put("userName", user.getName());
			result.put("result", "성공");
		} else {
			result.put("code", 403);
			result.put("error_message", "로그인 정보가 일치하지 않습니다.");
		}
		return result;
	}
	
	// 평가하기
	@PostMapping("/evaluate")
	public Map<String, Object> evaluate(
			@RequestParam("starPoint") int starPoint,
			@RequestParam("traderId") int traderId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		int addPoint = 0;
		switch(starPoint) {
		case 1:
			addPoint = -2;
			break;
		case 2:
			addPoint = -1;
			break;
		case 3:
			addPoint = 0;
			break;
		case 4:
			addPoint = 1;
			break;
		case 5:
			addPoint = 2;
			break;
		}
		
		UserEntity user = userBO.getUserEntityById(traderId);
		double fixedDegree = user.getDegree() + addPoint;
		userBO.updateDegreeById(fixedDegree, user.getId());
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	// 회원탈퇴
	@DeleteMapping("/delete-user")
	public Map<String, Object> deleteUser(
			@RequestParam("userId") int userId) {
		Map<String, Object> result = new HashMap<>();
		
		// 유저가 올렸던 게시글 가져오기
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		// 게시글 하나씩에 있는 사진 삭제
		for (int i = 0; i < postList.size(); i++) {
			// 이미지 삭제
			postImageBO.deleteImageByPostId(postList.get(i).getId());
		}
		
		// 유저가 올렸던 게시글 전부 삭제
		postBO.deletePostByUserId(userId);
		
		// 유저가 눌렀던 좋아요 정보 삭제
		likeBO.deleteLikeByUserId(userId);
		
		// 유저가 거래요청 했던 정보 삭제
		postBO.updatePostByBuyerId(userId);
		
		// 유저 프로필 이미지 삭제
		userBO.deleteProfileImageById(userId);
		
		// 유저 삭제
		userBO.deleteUserById(userId);
		
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
}
