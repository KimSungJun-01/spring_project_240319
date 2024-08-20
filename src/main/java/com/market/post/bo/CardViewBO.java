package com.market.post.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.like.bo.LikeBO;
import com.market.like.domain.Like;
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
	public List<CardView> generateCardViewList(Integer prevId, Integer nextId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> postList = postBO.getPostList(prevId, nextId);
		
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
	
	// input : listOrder
	// output : List<CardView>
	public List<CardView> generateCardViewList(String listOrder, Integer prevId, Integer nextId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> postList = null;
		if (listOrder.equals("latestOrder")) {
			postList = postBO.getPostListLatestOrder(prevId, nextId);
		} else if (listOrder.equals("popularityOrder")) {
			postList = postBO.getPostList(prevId, nextId);
		} else if (listOrder.equals("ascendingOrder")) {
			postList = postBO.getPostListAscendingOrderPrice(prevId, nextId);
		} else if (listOrder.equals("descendingOrder")) {
			postList = postBO.getPostListDescendingOrderPrice(prevId, nextId);
		}
		
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
		
		// 인기순인 경우 cardViewList 완성 후 좋아요 갯수에 따라 재배열 (내림차순 정렬)
		if (listOrder.equals("popularityOrder")) {
			for (int i = (cardViewList.size() - 1); i >= 1; i--) { // 버블정렬 (시간복잡도가 높아서 다른 정렬로도 이후에 도전)
				for (int j = 0; j < i; j++) {
					if (cardViewList.get(j).getLikeCount() < cardViewList.get(j + 1).getLikeCount()) {
						Collections.swap(cardViewList, j, (j + 1));
					}
				}
			}
		}
		
		return cardViewList;
	}
	
	public List<CardView> generateMyLikeCardViewList(List<Like> pushedLikeList) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> myLikePostList = postBO.getMyLikePostList(pushedLikeList);
		
		for (int i = 0; i < myLikePostList.size(); i++) {
			CardView card = new CardView();
			
			// CardView에 글 추가
			card.setPost(myLikePostList.get(i));
			
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
	
	public List<CardView> generateMyCardViewList(int userId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> postList = postBO.getPostListByUserId(userId);
		
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
	
	public List<CardView> generateRequestExchangeCardViewList(int userId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> requestExchangePostList = postBO.getRequestExchangePostListByUserId(userId);
		
		for (int i = 0; i < requestExchangePostList.size(); i++) {
			CardView card = new CardView();
			
			// CardView에 글 추가
			card.setPost(requestExchangePostList.get(i));
			
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
