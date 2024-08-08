package com.market.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.market.common.FileManagerService;
import com.market.post.domain.Image;
import com.market.post.mapper.PostMapper;

@Service
public class PostImageBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// input : postId, List<MultipartFile> files
	// output : x
	public void addImages(int postId, String userLoginId, List<MultipartFile> files) {
		for (int i = 0; i < files.size(); i++) {
			String imagePath = fileManagerService.uploadFile(files.get(i), userLoginId);
			postMapper.insertImage(postId, imagePath);
		}
	}
	
	// input : postId
	// output : List<Image>
	public Image getImageByPostId(int postId) {
		return postMapper.selectImageByPostId(postId);
	}
}
