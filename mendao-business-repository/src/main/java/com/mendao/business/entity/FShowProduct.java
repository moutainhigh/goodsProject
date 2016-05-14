package com.mendao.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.framework.entity.ShopUser;

/**
 * 
 * @ClassName: FShowProduct
 * @Description: TODO 分销商可见商品表
 * @author TianMeifeng
 * @date 2016年5月7日 下午10:47:06
 *
 */
@Entity
@Table(name = "t_f_show_product")
public class FShowProduct implements Serializable {

	private static final long serialVersionUID = 6975242819283513565L;
	/**
	 * id
	 */
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

	/**
	 * 对应产品ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dproduct_id", nullable = true)
	private DProduct dproduct;
	/**
	 * 分销商用户ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private ShopUser user;

	/**
	 * 创建日期
	 */
	private Date createDate;

	public DProduct getDproduct() {
		return dproduct;
	}

	public void setDproduct(DProduct dproduct) {
		this.dproduct = dproduct;
	}

	public ShopUser getUser() {
		return user;
	}

	public void setUser(ShopUser user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
