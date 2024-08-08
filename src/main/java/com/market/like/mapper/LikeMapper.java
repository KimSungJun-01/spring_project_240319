package com.market.like.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
	
	// input : postId
	// output : int
	public int selectLikeCountByPostId(int postId);
}
