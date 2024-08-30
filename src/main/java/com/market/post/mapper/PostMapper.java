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
	public List<Post> selectPostListPaging(
			@Param("limitStart") int limitStart,
			@Param("postsPerPage") int postsPerPage);
	public List<Post> selectPostListByUserId(Integer userId);
	public List<Post> selectPostListByUserIdAndLimitStartAndPostsPerPage(
			@Param("userId") int userId,
			@Param("limitStart") int limitStart,
			@Param("postsPerPage") int postsPerPage);
	public List<Post> selectMyExchangePostListByUserId(Integer userId);
	public List<Post> selectRequestExchangePostListByUserId(
			@Param("userId") int userId,
			@Param("limitStart") int limitStart,
			@Param("postsPerPage") int postsPerPage);
	
	public List<Post> selectPostListOrderByCreatedAt(
			@Param("limitStart") int limitStart,
			@Param("postsPerPage") int postsPerPage);
	public List<Post> selectPostListOrderByAscendingOrderPrice(
			@Param("limitStart") int limitStrat,
			@Param("postsPerPage") int postsPerPage);
	public List<Post> selectPostListOrderByDescendingOrderPrice(
			@Param("limitStart") int limitStrat,
			@Param("postsPerPage") int postsPerPage);
	
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
