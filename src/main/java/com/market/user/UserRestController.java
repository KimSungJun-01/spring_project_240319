package com.market.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.common.EncryptUtils;
import com.market.user.bo.UserBO;
import com.market.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
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
			@RequestParam(value = "email", required = false) String email) {
		
		// password 암호화 (md5 알고리즘)
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB 저장
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, phoneNumber, email);
		
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
}
