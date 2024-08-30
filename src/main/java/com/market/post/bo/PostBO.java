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
	
	public List<Post> getPostList(int limitStart, int postsPerPage) {
		return postMapper.selectPostListPaging(limitStart, postsPerPage);
	}
	
	public Integer getPostListCount() {
		List<Post> post = postMapper.selectPostList();
		return post.size();
	}
	
	public Integer getPostListCountByUserId(Integer userId) {
		List<Post> post = postMapper.selectPostListByUserId(userId);
		return post.size();
	}
	
	public Integer getPostListCountByMyExchange(Integer userId) {
		List<Post> post = postMapper.selectMyExchangePostListByUserId(userId);
		return post.size();
	}
	
	public List<Post> getPostListByUserId(Integer userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	public List<Post> getPostListByUserId(int userId, int limitStart, int postsPerPage) {
		return postMapper.selectPostListByUserIdAndLimitStartAndPostsPerPage(userId, limitStart, postsPerPage);
	}
	
	public List<Post> getRequestExchangePostListByUserId(int userId, int limitStart, int postsPerPage) {
		return postMapper.selectRequestExchangePostListByUserId(userId, limitStart, postsPerPage);
	}
	
	// input : x
	// output : List<Post>
	public List<Post> getPostListLatestOrder(int limitStart, int postsPerPage) {
		return postMapper.selectPostListOrderByCreatedAt(limitStart, postsPerPage);
	}
	
	public List<Post> getPostListAscendingOrderPrice(int limitStart, int postsPerPage) {
		return postMapper.selectPostListOrderByAscendingOrderPrice(limitStart, postsPerPage);
	}
	
	public List<Post> getPostListDescendingOrderPrice(int limitStart, int postsPerPage) {
		return postMapper.selectPostListOrderByDescendingOrderPrice(limitStart, postsPerPage);
	}
	
	public List<Post> getMyLikePostList(List<Like> pushedLikeList) {
		List<Post> myLikePostList = new ArrayList<>();
		
		for (int i = 0; i < pushedLikeList.size(); i++) {
			Integer thisPostId = pushedLikeList.get(i).getPostId();
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
	
	public void updatePostByBuyerId(int userId) {
		postMapper.updateBuyerIdByUserId(userId);
	}
}
