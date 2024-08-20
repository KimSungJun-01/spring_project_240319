package com.market.user.bo;

import java.time.LocalDateTime;

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
	public UserEntity getUserEntityById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public void updateDegreeById(double fixedDegree, int id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user = user.toBuilder()
					.degree(fixedDegree)
					.updatedAt(LocalDateTime.now())
					.build();
			user = userRepository.save(user);
		}
	}
	
	public void deleteUserById(int id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			userRepository.delete(user);
		}
	}
	
	public void deleteProfileImageById(int id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if (user != null) {
			String profileImagePath = user.getProfileImagePath();
			fileManagerService.deleteProfileFile(profileImagePath);
		}
	}
}
