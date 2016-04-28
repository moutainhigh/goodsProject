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
@Table(name = "fw_duty_source")
public class FwDutySource implements java.io.Serializable {   
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "identity")
    @GenericGenerator(name = "identity", strategy = "identity")
    @Column(unique = true, nullable = false)
	private Long id ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 权限
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "source_id", nullable = false)
	private FwSource fwSource;
	/**
	 * 权限
	 * @return
	 */
	public FwSource getFwSource() {
		return fwSource;
	}
	/**
	 * 权限
	 * @param fwSource
	 */
	public void setFwSource(FwSource fwSource) {
		this.fwSource = fwSource;
	}

	/**
	 * 角色
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "duty_id", nullable = false)
	private FwDuty fwDuty;
	/**
	 * 角色
	 * @return
	 */
	public FwDuty getFwDuty() {
		return fwDuty;
	}
	/**
	 * 角色
	 * @param fwDuty
	 */
	public void setFwDuty(FwDuty fwDuty) {
		this.fwDuty = fwDuty;
	}
}  
