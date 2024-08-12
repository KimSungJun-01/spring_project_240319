package com.market.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	
	// notebook
	//public static final String FILE_UPLOAD_PATH = "C:\\Users\\moonh\\OneDrive\\바탕 화면\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\post_img/";
	
	// megastudy
	public static final String FILE_UPLOAD_PATH = "D:\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\post_img/";
	
	// input : userLoginId, MultipartFile
	// output : imagePath
	public String uploadFile(MultipartFile file, String loginId) {
		
		String directoryName = loginId + "_" + System.currentTimeMillis();
		
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null;
		}
		
		try {
			byte bytes[] = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return "/img/post_img/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	// notebook
	//public static final String PROFILE_FILE_UPLOAD_PATH = "C:\\Users\\moonh\\OneDrive\\바탕 화면\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\profile_img/";
	
	// megastudy
	public static final String PROFILE_FILE_UPLOAD_PATH = "D:\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\profile_img/";
	
	public String profileUploadFile(MultipartFile profileImage, String loginId) {
		
		String directoryName = loginId + "_" + System.currentTimeMillis();
		
		String filePath = PROFILE_FILE_UPLOAD_PATH + directoryName + "/";
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null;
		}
		
		try {
			byte bytes[] = profileImage.getBytes();
			Path path = Paths.get(filePath + profileImage.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return "/img/profile_img/" + directoryName + "/" + profileImage.getOriginalFilename();
	}
}
