package com.market.post.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.like.domain.Like;
import com.market.post.domain.Post;
import com.market.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper; 
	
	private static final int POST_MAX_SIZE = 8;
	
	// input : x
	// output : List<Post>
	public List<Post> getPostList(Integer prevId, Integer nextId) {
		Integer standardId = null;
		String direction = null;
		if (prevId != null) {
			standardId = prevId;
			direction = "prev";
			List<Post> postList = postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
			Collections.reverse(postList);
			return postList;
		} else if (nextId != null) {
			standardId = nextId;
			direction = "next";
		}
		
		return postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
	}
	
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	public List<Post> getRequestExchangePostListByUserId(int userId) {
		return postMapper.selectRequestExchangePostListByUserId(userId);
	}
	
	// input : x
	// output : List<Post>
	public List<Post> getPostListLatestOrder(Integer prevId, Integer nextId) {
		Integer standardId = null;
		String direction = null;
		if (prevId != null) {
			standardId = prevId;
			direction = "prev";
			List<Post> postList = postMapper.selectPostListOrderByCreatedAt(standardId, direction, POST_MAX_SIZE);
			Collections.reverse(postList);
			return postList;
		} else if (nextId != null) {
			standardId = nextId;
			direction = "next";
		}
		
		return postMapper.selectPostListOrderByCreatedAt(standardId, direction, POST_MAX_SIZE);
	}
	
	public List<Post> getPostListAscendingOrderPrice(Integer prevId, Integer nextId) {
		Integer standardId = null;
		String direction = null;
		if (prevId != null) {
			standardId = prevId;
			direction = "prev";
			List<Post> postList = postMapper.selectPostListOrderByAscendingOrderPrice(standardId, direction, POST_MAX_SIZE);
			Collections.reverse(postList);
			return postList;
		} else if (nextId != null) {
			standardId = nextId;
			direction = "next";
		}
		
		return postMapper.selectPostListOrderByAscendingOrderPrice(standardId, direction, POST_MAX_SIZE);
	}
	
	public List<Post> getPostListDescendingOrderPrice(Integer prevId, Integer nextId) {
		Integer standardId = null;
		String direction = null;
		if (prevId != null) {
			standardId = prevId;
			direction = "prev";
			List<Post> postList = postMapper.selectPostListOrderByDescendingOrderPrice(standardId, direction, POST_MAX_SIZE);
			Collections.reverse(postList);
			return postList;
		} else if (nextId != null) {
			standardId = nextId;
			direction = "next";
		}
		
		return postMapper.selectPostListOrderByDescendingOrderPrice(standardId, direction, POST_MAX_SIZE);
	}
	
	public List<Post> getMyLikePostList(List<Like> pushedLikeList) {
		List<Post> myLikePostList = new ArrayList<>();
		for (int i = 0; i < pushedLikeList.size(); i++) {
			int thisPostId = pushedLikeList.get(i).getPostId();
			myLikePostList.add(postMapper.selectPostById(thisPostId));
		}
		return myLikePostList;
	}
	
	// input : userId, subject, price, address, content(비필수)
	// output : int
	public int addPost(int userId, String subject, int price, String address, String content) {
		Post post = new Post();
		post.setUserId(userId);
		post.setSubject(subject);
		post.setPrice(price);
		post.setAddress(address);
		post.setContent(content);
		
		postMapper.insertPost(post);
		return post.getId();
	}
	
	// input : postId, userId
	// output : x
	public void updatePostBuyerId(int userId, int postId) {
		postMapper.updateBuyerIdByPostId(userId, postId);
	}
	
	public void cancelPostBuyerId(int userId) {
		postMapper.updateBuyerIdByUserId(userId);
	}
	
	// input : postId
	// output : Post
	public Post getPostByPostId(int postId) {
		return postMapper.selectPostById(postId);
	}
	
	public void deletePostById(int postId) {
		postMapper.deletePostById(postId);
	}
	
	public void deletePostByUserId(int userId) {
		postMapper.deletePostByUserId(userId);
	}
	
	public void updateIsEvaluatedByPostId(int postId) {
		postMapper.updateIsEvaluatedByPostId(postId);
	}
	
	public boolean isPrevLastPage(int prevId) {
		int maxPostId = postMapper.selectPostIdAsSort("DESC");
		return maxPostId == prevId;
	}
	
	public boolean isNextLastPage(int nextId) {
		int minPostId = postMapper.selectPostIdAsSort("ASC");
		return minPostId == nextId;
	}
	
	public void updatePostByBuyerId(int userId) {
		postMapper.updateBuyerIdByUserId(userId);
	}
}
