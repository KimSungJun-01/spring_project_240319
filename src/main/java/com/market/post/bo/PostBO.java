package com.market.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	// input : 
}
