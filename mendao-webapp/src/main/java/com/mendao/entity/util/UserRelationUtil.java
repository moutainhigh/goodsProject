package com.mendao.entity.util;

import com.mendao.framework.entity.ShopUser;

public class UserRelationUtil {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String uuid;

	private ShopUser parent;

	private ShopUser currentUser;

	private int grade;

	private int useDay;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ShopUser getParent() {
		return parent;
	}

	public void setParent(ShopUser parent) {
		this.parent = parent;
	}

	public ShopUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ShopUser currentUser) {
		this.currentUser = currentUser;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getUseDay() {
		return useDay;
	}

	public void setUseDay(int useDay) {
		this.useDay = useDay;
	}

}
