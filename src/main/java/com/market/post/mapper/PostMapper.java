package com.market.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.post.domain.Image;
import com.market.post.domain.Post;

@Mapper
public interface PostMapper {
	
	// 글
	public List<Map<String, Object>> selectPostListTest();
	public List<Post> selectPostList();
	public void insertPost(Post post);
	
	// 이미지
	public void insertImage(
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
	
	public Image selectImageByPostId(int postId);
}
