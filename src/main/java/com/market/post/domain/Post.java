package com.market.post.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Post {
	private int id;
	private int userId;
	private String subject;
	private int price;
	private String address;
	private String content;
	private String state;
	private int buyerId;	
	private boolean isEvaluated;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
