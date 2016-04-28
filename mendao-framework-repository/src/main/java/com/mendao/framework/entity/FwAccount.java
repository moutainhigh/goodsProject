package com.mendao.framework.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fw_account")
public class FwAccount  implements java.io.Serializable {

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
	 * 登录用户名
	 */
	@Column(nullable = false, length = 50)
	private String acctName;
	/**
	 * 登录用户名
	 * 
	 * @return
	 */
	public String getAcctName() {
		return acctName;
	}
	/**
	 * 登录用户名
	 * 
	 * @param acctName
	 */
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	
	
	/**
	 * 登录密码
	 */
	@Column(nullable = false, length = 50)
	private String acctPwd;

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public String getAcctPwd() {
		return acctPwd;
	}

	/**
	 * 登录密码
	 * 
	 * @param acctPwd
	 */
	public void setAcctPwd(String acctPwd) {
		this.acctPwd = acctPwd;
	}

	/**
	 * 用户状态。 0:锁定；1:激活
	 */
	@Column(nullable = false)
	private int status;
	/**
	 * 用户状态
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 用户状态
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 账户余额
	 */
	@Column(nullable = false, precision=10, scale=2)
	private BigDecimal amount;
	/**
	 * 账户余额
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 账户余额
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 基金余额
	 */
	@Column(nullable = false, precision=10, scale=2)
	private BigDecimal fund;
	/**
	 * 基金余额
	 * @return
	 */
	public BigDecimal getFund() {
		return fund;
	}
	/**
	 * 基金余额
	 * @param fund
	 */
	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}

	/**
	 * 付款密码
	 */
	private String securityCode;
	/**
	 * 付款密码
	 * @return
	 */
	public String getSecurityCode() {
		return securityCode;
	}
	/**
	 * 付款密码
	 * @param securityCode
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public FwAccount(){
		this.status = 0;
		this.createDate = new Date();
		this.amount = new BigDecimal(0);
		this.fund = new BigDecimal(0);
		this.acctName = "";
	}
	
	
	@Transient
	private String nickName;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@OneToMany(mappedBy="fwAccount", cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<FwAccountDuty> fwAccountDuties;
	public Set<FwAccountDuty> getFwAccountDuties() {
		return this.fwAccountDuties;
	}
	public void setFwAccountDuties(Set<FwAccountDuty> fwAccountDuties) {
		this.fwAccountDuties = fwAccountDuties;
	}
	
	
	@OneToMany(mappedBy="fwAccount", cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<FwAccountIO> fwAccountIOItems;
	public Set<FwAccountIO> getFwAccountIOItems() {
		return fwAccountIOItems;
	}
	public void setFwAccountIOItems(Set<FwAccountIO> fwAccountIOItems) {
		this.fwAccountIOItems = fwAccountIOItems;
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
}
