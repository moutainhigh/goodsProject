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
@Table(name = "fw_account_duty")
public class FwAccountDuty implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 56432278631030039L;

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
	
	
	//[start] fwDuty 角色
	/**
	 * 角色
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name = "duty_id", nullable = false)
	private FwDuty fwDuty;
	/**
	 * 角色
	 * @return
	 */
	public FwDuty getFwDuty() {
		return this.fwDuty;
	}
	/**
	 * 角色
	 * @param fwDuty
	 */
	public void setFwDuty(FwDuty fwDuty) {
		this.fwDuty = fwDuty;
	}
	//[end]
	
	//[start] fwAccount 用户
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "acct_id", nullable = false)
	private FwAccount fwAccount;
	/**
	 * 用户
	 * @return
	 */
	public FwAccount getFwAccount() {
		return this.fwAccount;
	}
	/**
	 * 用户
	 * @param fwAccount
	 */
	public void setFwAccount(FwAccount fwAccount) {
		this.fwAccount = fwAccount;
	}
	//[end]


	public FwAccountDuty(){

	}

	/** full constructor */
	public FwAccountDuty(FwDuty fwDuty,
			FwAccount fwAccount) {
		this.fwDuty = fwDuty;
		this.fwAccount = fwAccount;
	}
}
