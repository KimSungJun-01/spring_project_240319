package com.market.post.domain;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CardView {
	// 그림 1개
	private Image image;
	
	// 글 1개
	private Post post;
	
	// 좋아요 개수
	private int likeCount;
}
