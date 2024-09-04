package com.market.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	
	// notebook
	//public static final String FILE_UPLOAD_PATH = "C:\\Users\\moonh\\OneDrive\\바탕 화면\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\post_img/";
	
	// study
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\201-16\\Desktop\\web\\6_market_project\\clone\\src\\main\\resources\\static\\img\\post_img/";
	
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
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	public void deleteFile(String imagePath) {
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/img/post_img/", ""));
		
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[FileManagerService 파일삭제] 삭제 실패. path:{}", path.toString());
				return;
			}
			
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제] 디렉토리 삭제 실패 path:{}", path.toString());
				}
			}
		}
	}
	
	// notebook
	//public static final String PROFILE_FILE_UPLOAD_PATH = "C:\\Users\\moonh\\OneDrive\\바탕 화면\\김성준\\6_spring_project\\market\\clone\\src\\main\\resources\\static\\img\\profile_img/";
	
	// study
	public static final String PROFILE_FILE_UPLOAD_PATH = "C:\\Users\\201-16\\Desktop\\web\\6_market_project\\clone\\src\\main\\resources\\static\\img\\profile_img/";
	
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
		
		return "/profile_images/" + directoryName + "/" + profileImage.getOriginalFilename();
	}
	
	public void deleteProfileFile(String imagePath) {
		Path path = Paths.get(PROFILE_FILE_UPLOAD_PATH + imagePath.replace("/img/profile_img/", ""));
		
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[FileManagerService 파일삭제] 삭제 실패. path:{}", path.toString());
				return;
			}
			
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제] 디렉토리 삭제 실패 path:{}", path.toString());
				}
			}
		}
	}
}
