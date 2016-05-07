package com.mendao.business.entity;

import java.io.Serializable;

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
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private DProduct product;
}
