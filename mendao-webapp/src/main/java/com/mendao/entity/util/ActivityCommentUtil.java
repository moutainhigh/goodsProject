package com.mendao.entity.util;

import java.util.List;

import com.mendao.business.entity.ActivityComment;

public class ActivityCommentUtil {

	private ActivityComment comment;
	private List<ActivityComment> childComment;

	public ActivityComment getComment() {
		return comment;
	}

	public void setComment(ActivityComment comment) {
		this.comment = comment;
	}

	public List<ActivityComment> getChildComment() {
		return childComment;
	}

	public void setChildComment(List<ActivityComment> childComment) {
		this.childComment = childComment;
	}

}
