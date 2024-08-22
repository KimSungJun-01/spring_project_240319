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
	public List<Post> selectPostListByUserId(int userId);
	public List<Post> selectRequestExchangePostListByUserId(int userId);
	
	public List<Post> selectPostListOrderByCreatedAt();
	public List<Post> selectPostListOrderByAscendingOrderPrice();
	public List<Post> selectPostListOrderByDescendingOrderPrice();
	
	public void insertPost(Post post);
	public Post selectPostById(int id);
	public void updateBuyerIdByPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
	public void updateBuyerIdByUserId(int userId);
	public void deletePostById(int postId);
	public void deletePostByUserId(int userId);
	public void updateIsEvaluatedByPostId(int postId);
	
	public int selectPostIdAsSort(String sort);
	
	// 이미지
	public void insertImage(
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
	
	public Image selectImageByPostId(int postId);
	public int deleteImageByPostId(int postId);
}
