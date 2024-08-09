package com.market.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
	// input : postId
	// output : int
	public int selectLikeCountByPostId(int postId);
	
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void insertLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
}
