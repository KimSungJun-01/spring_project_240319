package com.market.map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.post.bo.PostBO;
import com.market.post.domain.Post;

@RequestMapping("/map")
@RestController
public class MapRestController {
	
	@Autowired
	private PostBO postBO;
	
	@PostMapping("/get-address")
	public List<Post> getAddress() {
		
		List<Post> postList = postBO.getPostList();
		
		return postList;
	}
}
