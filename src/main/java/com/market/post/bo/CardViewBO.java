package com.market.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.like.bo.LikeBO;
import com.market.post.domain.CardView;
import com.market.post.domain.Post;

@Service
public class CardViewBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private PostImageBO postImageBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// input : 
	// output : List<CardView>
	public List<CardView> generateCardViewList() {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> postList = postBO.getPostList();
		
		for (int i = 0; i < postList.size(); i++) {
			CardView card = new CardView();
			
			// CardView에 글 추가
			card.setPost(postList.get(i));
			
			// 해당 글의 id 가져오기
			int thisPostId = card.getPost().getId();
			
			// 글의 id로 해당 대표 이미지 1개 CardView에 추가
			card.setImage(postImageBO.getImageByPostId(thisPostId));
			
			// 해당 글의 좋아요 개수 추가
			card.setLikeCount(likeBO.getLikeCountByPostId(thisPostId));
			
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}
