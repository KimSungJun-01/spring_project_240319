package com.market.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface PostImageMapper {
	
	public void insertImage(int postId, MultipartFile file);
}
