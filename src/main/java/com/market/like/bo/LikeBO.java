package com.market.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	// input : postId
	// output : int
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostId(postId);
	}
	
	// input : postId, userId
	// output : boolean
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		
		if (userId == null) {
			return false;
		}
		
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) == 1 ? true : false;
	}
	
	// input : postId, userId
	// output : x
	public void likeToggle(int postId, int userId) {
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		if (count > 0) {
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			likeMapper.insertLike(postId, userId);
		}
	}
}
