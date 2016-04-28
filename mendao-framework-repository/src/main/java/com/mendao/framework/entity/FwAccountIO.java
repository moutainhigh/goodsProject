package com.mendao.framework.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.common.enums.AccountIOEnum;

/**
 * 用户账户出入账流水
 * @author libra
 *
 */
@Entity
@Table(name = "fw_account_io")
public class FwAccountIO implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2297445333018450643L;
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
	 * 用户账户
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "acct_id", nullable = false)
	private FwAccount fwAccount;
	/**
	 * 用户账户
	 * @return
	 */
	public FwAccount getFwAccount() {
		return fwAccount;
	}
	/**
	 * 用户账户
	 * @param fwAccount
	 */
	public void setFwAccount(FwAccount fwAccount) {
		this.fwAccount = fwAccount;
	}
	
	/**
	 * 目标账户（若付款，则该字段为收款人；若为收款，该字段为付款人）
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "target_id")
	private FwAccount tgAccount;
	/**
	 * 目标账户
	 * @return
	 */
	public FwAccount getTgAccount() {
		return tgAccount;
	}
	/**
	 * 目标账户
	 * @param tgAccount
	 */
	public void setTgAccount(FwAccount tgAccount) {
		this.tgAccount = tgAccount;
	}


	/**
	 * 流水创建日期
	 */
	private Date createDate;
	/**
	 * 流水创建日期
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 流水创建日期
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 流水金额（正数为收款，负数为付款）
	 */
	@Column(nullable = false, precision=10, scale=2)
	private BigDecimal amount;
	/**
	 * 流水金额
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 流水金额
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 基金流水金额（正数为收款，负数为付款）
	 */
	@Column(nullable = false, precision=10, scale=2)
	private BigDecimal fund;
	/**
	 * 基金流水金额（正数为收款，负数为付款）
	 * @return
	 */
	public BigDecimal getFund() {
		return fund;
	}
	/**
	 * 基金流水金额（正数为收款，负数为付款）
	 * @param amount
	 */
	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}
	
	/**
	 * 描述
	 */
	@Column(length = 255)
	private String description;
	/**
	 * 描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 操作类型 
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private AccountIOEnum ioType;
	/**
	 * 操作类型
	 * @return
	 */
	public AccountIOEnum getIoType() {
		return ioType;
	}
	/**
	 * 操作类型
	 * @param ioType
	 */
	public void setIoType(AccountIOEnum ioType) {
		this.ioType = ioType;
	}
	
	public BigDecimal getTotalAmount(){
		if(this.amount == null && this.fund == null){
			return new BigDecimal(0);
		}else if(this.amount == null){
			return this.fund;
		}else if(this.fund == null){
			return this.amount;
		}else{
			return this.amount.add(fund);
		}
	}
	public FwAccountIO(){
		this.createDate = new Date();
		this.amount = new BigDecimal(0);
		this.fund = new BigDecimal(0);
	}
}
