package com.mendao.framework.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fw_duty")
public class FwDuty  implements java.io.Serializable{

	
	private static final long serialVersionUID = -8474025844522178714L;
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
	 * 状态。0:不可用；1:可用
	 */
	private int status;
	/**
	 * 状态。0:不可用；1:可用
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 状态。0:不可用；1:可用
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 角色名
	 */
	@Column(length = 50, nullable = false)
	private String dutyName;
	/**
	 * 角色名
	 * @return
	 */
	public String getDutyName() {
		return dutyName;
	}
	/**
	 * 角色名
	 * @param dutyName
	 */
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	/**
	 * 描述
	 */
	@Column(name = "description")
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
	 * 
	 */
	@Column(length = 20)
	private String dutyType;
	public String getDutyType() {
		return dutyType;
	}
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	
	/**
	 * 角色权限集合
	 */
	@OneToMany(mappedBy="fwSource", cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<FwDutySource> fwDutySources;
	/**
	 * 角色权限集合
	 * @return
	 */
	public Set<FwDutySource> getFwDutySources() {
		return this.fwDutySources;
	}
	/**
	 * 角色权限集合
	 * @param fwDutyActions
	 */
	public void setFwDutyActions(Set<FwDutySource> fwDutySources) {
		this.fwDutySources = fwDutySources;
	}

	
	/**
	 * 用户角色集合
	 */
	@OneToMany(mappedBy="fwDuty", cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<FwAccountDuty> accountDuties;
	/**
	 * 用户角色集合
	 * @return
	 */
	public Set<FwAccountDuty> getAccountDuties() {
		return this.accountDuties;
	}
	/**
	 * 用户角色集合
	 * @param fwAccountDuties
	 */
	public void setAccountDuties(Set<FwAccountDuty> fwAccountDuties) {
		this.accountDuties = fwAccountDuties;
	}
	
	public FwDuty(){
		this.status = 0;
	}
}
