package com.market.post.bo;

import java.util.ArrayList;
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
	
	// input : x
	// output : List<Post>
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}
	
	public List<Post> getMyLikePostList(List<Like> pushedLikeList) {
		List<Post> myLikePostList = new ArrayList<>();
		for (int i = 0; i < pushedLikeList.size(); i++) {
			int thisPostId = pushedLikeList.get(i).getPostId();
			myLikePostList.add(postMapper.selectPostById(thisPostId));
		}
		return myLikePostList;
	}
	
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	public List<Post> getRequestExchangePostListByUserId(int userId) {
		return postMapper.selectRequestExchangePostListByUserId(userId);
	}
	
	// input : x
	// output : List<Post>
	public List<Post> getPostListLatestOrder() {
		return postMapper.selectPostListOrderByCreatedAt();
	}
	
	public List<Post> getPostListAscendingOrderPrice() {
		return postMapper.selectPostListOrderByAscendingOrderPrice();
	}
	
	public List<Post> getPostListDescendingOrderPrice() {
		return postMapper.selectPostListOrderByDescendingOrderPrice();
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
}
