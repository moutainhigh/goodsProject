package com.mendao.entity.util;

import java.util.List;

import com.mendao.business.entity.SchoolComment;

public class SchoolCommentUtil {

	private SchoolComment comment;
	private List<SchoolComment> childComment;

	public SchoolComment getComment() {
		return comment;
	}

	public void setComment(SchoolComment comment) {
		this.comment = comment;
	}

	public List<SchoolComment> getChildComment() {
		return childComment;
	}

	public void setChildComment(List<SchoolComment> childComment) {
		this.childComment = childComment;
	}

}
