package com.mendao.business.entity;

import java.io.Serializable;
import java.util.Date;

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

/**
 * 
 * @ClassName: ProductPic
 * @Description: TODO 产品图片
 * @author TianMeifeng
 * @date 2016年5月7日 下午10:47:06
 *
 */
@Entity
@Table(name = "t_product_picture")
public class ProductPic implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2605071065631813041L;

	/**
	 * 图片id
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
	 * 对应代理id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dproduct_id", nullable = true)
	private DProduct dproduct;
	/**
	 * 分销商产品ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fproduct_id", nullable = true)
	private FProduct fproduct;

	/**
	 * 图片地址
	 */
	private String imageUrl;
	/**
	 * 缩略图地址
	 */
	private String thumbUrl;
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

	public FProduct getFproduct() {
		return fproduct;
	}

	public void setFproduct(FProduct fproduct) {
		this.fproduct = fproduct;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
