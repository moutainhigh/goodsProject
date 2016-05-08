package com.mendao.framework.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_relation")
public class UserRelation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8344500431984627929L;

	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 100, nullable = false)
	private String uuid;
	/**
	 * 用户partent id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	private ShopUser parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_user_id", nullable = false)
	private ShopUser currentUser;

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

}
