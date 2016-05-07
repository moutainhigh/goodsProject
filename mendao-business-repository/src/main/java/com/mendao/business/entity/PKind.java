package com.mendao.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.framework.entity.ShopUser;

/**
 * 
 * @ClassName: PKind 
 * @Description: TODO(产品类目) 
 * @author TianMeifeng
 * @date 2016年5月7日 下午11:12:23 
 *
 */
@Entity
@Table(name = "t_kind")
public class PKind implements Serializable {
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = -7828282487220007443L;
	
	/**
	 * 类目id
	 */
	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * 类目名称
	 */
	@Column(nullable = false, length = 50)
	private String kindName;
	
	/**
	 * 创建人
	 */
	@Column(length = 11, nullable = false)
	private ShopUser createId;
	
	/**
	 * 父级类目
	 */
	@Column(length = 11, nullable = true)
	private PKind pKind;
	
	/**
	 * 类目状态  0:可用   -1：删除（不可用）
	 */
	@Column(length = 11, nullable = true)
	private Integer status;
	
}
