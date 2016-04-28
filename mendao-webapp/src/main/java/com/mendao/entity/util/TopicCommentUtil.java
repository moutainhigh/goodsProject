package com.mendao.entity.util;

import java.util.List;

import com.mendao.business.entity.TopicComment;

public class TopicCommentUtil {
	
	private TopicComment comment;
	private List<TopicComment> childComment;
	
	public TopicComment getComment() {
		return comment;
	}
	public void setComment(TopicComment comment) {
		this.comment = comment;
	}
	public List<TopicComment> getChildComment() {
		return childComment;
	}
	public void setChildComment(List<TopicComment> childComment) {
		this.childComment = childComment;
	}

	
}
