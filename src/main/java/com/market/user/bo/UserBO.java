package com.market.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.market.common.FileManagerService;
import com.market.user.entity.UserEntity;
import com.market.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// input : loginId
	// output : UserEntity or null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	// input : 회원가입 정보
	// output : UserEntity
	public UserEntity addUser(String loginId, String password, String name, String phoneNumber, String email, MultipartFile profileImage) {
		
		String profileImagePath = fileManagerService.profileUploadFile(profileImage, loginId);
		
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.phoneNumber(phoneNumber)
				.degree(36.5)
				.email(email)
				.profileImagePath(profileImagePath)
				.build());
	}
	
	// input : loginId, hashedPassword
	// output : UserEntity or null
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
	
	// input : userId
	// output : UserEntity
	public UserEntity getUserEntityByLoginId(int userId) {
		return userRepository.findById(userId);
	}
	
	public void updateDegreeById(double fixedDegree, int id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		
		
	}
}
