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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.framework.entity.ShopUser;

/**
 * 
 * @ClassName: DProduct 
 * @Description: TODO 代理产品类
 * @author TianMeifeng
 * @date 2016年5月6日 下午12:07:05 
 *
 */
@Entity
@Table(name = "t_d_product")
public class DProduct implements Serializable{
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 5525425128154610665L;

	/**
	 * 产品id
	 */
	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * 产品名称
	 */
	@Column(length = 100, nullable = false)
	private String pName;
	
	/**
	 * 产品描述
	 */
	
	@Column(length = 500, nullable = true)
	private String desc;
	
	/**
	 * 产品价格
	 */
	@Column(length = 11, nullable = true)
	private Float price;
	
	/**
	 * 产品状态 0在售 1补货2售空
	 */
	@Column(length = 11, nullable = true)
	private Integer status;
	
	/**
	 * 类目 id
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "kind_id", nullable = true)
	private PKind kindId;
	
	/**
	 * 创建人id
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	private ShopUser creteUserId;
	
	/**
	 * 创建时间
	 */
	@Column(nullable = false)
	private Date createTime;
	
	/**
	 * 修改人id
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "modify_user_id", nullable = true)
	private ShopUser modifyUserId;
	
	/**
	 * 前台显示类目
	 */
	@Column(length = 11, nullable = true)
	private String showKind;
}
