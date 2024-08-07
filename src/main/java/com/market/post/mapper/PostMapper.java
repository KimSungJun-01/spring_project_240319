package com.market.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String, Object>> selectPostListTest();
	public List<Post> selectPostList();
	public void insertPost(Post post);
	public void insertImage(
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
}
