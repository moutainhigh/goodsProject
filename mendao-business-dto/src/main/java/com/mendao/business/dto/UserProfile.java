package com.mendao.business.dto;

import java.util.Date;
import java.util.Map;

import com.mendao.business.entity.School;
import com.mendao.business.entity.UserInfo;
import com.mendao.common.util.StringUtil;

public class UserProfile extends FormObject implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2297445333018450643L;
	
	private UserInfo userInfo;
	
	private School school;
	
	private Long id;

	public Long getId() {
		return null == id ? 0L : id;
	}

	public void setId(Long id) {
		userInfo.setId(id);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
		this.id = userInfo.getId();
		this.superUser = userInfo.getFwAccount().getAcctName().equalsIgnoreCase("admin");
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	//[start] Account Info
	/**
	 * 获取登录账号
	 */
	public String getAcctName(){
		return userInfo.getFwAccount().getAcctName();
	}
	public void setAcctName(String acctName) {
		userInfo.getFwAccount().setAcctName(acctName);
	}

	public String getPassword() {
		return userInfo.getFwAccount().getAcctPwd();
	}
	public void setPassword(String password) {
		userInfo.getFwAccount().setAcctPwd(password);
	}
	
	
	public int getStatus() {
		return userInfo.getFwAccount().getStatus();
	}
	public void setStatus(int status) {
		userInfo.getFwAccount().setStatus(status);
	}
	
	
	public Date getCreateDate() {
		return userInfo.getFwAccount().getCreateDate();
	}
	public void setCreateDate(Date createDate) {
		userInfo.getFwAccount().setCreateDate(createDate);
	}
	
	
	public String getSecurityCode() {
		return userInfo.getFwAccount().getSecurityCode();
	}

	public void setSecurityCode(String securityCode) {
		userInfo.getFwAccount().setSecurityCode(securityCode);
	}
	//[end]

	
	//[start] User Detail Info
	/**
	 * 用户昵称
	 * @return
	 */
	public String getNickName(){
		return userInfo.getNickName();
	}
	/**
	 * 用户昵称
	 * @param userName
	 */
	public void setNickName(String nickName) {
		userInfo.setNickName(nickName);
	}
	
	
	/**
	 * 用户绑定的手机号
	 * @return
	 */
	public String getMobile() {
		return userInfo.getMobile();
	}
	/**
	 * 用户绑定的手机号
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		userInfo.setMobile(mobile);
	}

	
	/**
	 * 用户绑定的邮箱
	 * @return
	 */
	public String getEmail() {
		return userInfo.getEmail();
	}
	/**
	 * 用户绑定的邮箱
	 * @param email
	 */
	public void setEmail(String email) {
		userInfo.setEmail(email);
	}
	
	
	/**
	 * 用户住址
	 * @return
	 */
	public String getDistrict() {
		return StringUtil.defaultIfBlank(userInfo.getDistrict());
	}
	/**
	 * 用户住址
	 * @param address
	 */
	public void setDistrict(String district) {
		userInfo.setDistrict(district);
	}
	
	/**
	 * 用户简介
	 * @return
	 */
	public String getSummary() {
		return userInfo.getSummary();
	}
	/**
	 * 用户简介
	 * @param summary
	 */
	public void setSummary(String summary) {
		userInfo.setSummary(summary);
	}
	
	
	/**
	 * 用户头像
	 * @return
	 */
	public String getIconPath() {
		return userInfo.getIcon();
	}
	//[end]
	
	//[start] School Info
	/**
	 * 获取学校名称
	 * @return
	 */
	public String getSchoolName(){
		return school != null ? school.getSchoolName() : "";
	}
	public void setSchoolName(String schoolName) {
		this.school.setSchoolName(schoolName);
	}
	

	public UserProfile(){
		//userMenus = new ArrayList<MenuItem>();
		this.userInfo = new UserInfo();
	}
	
	/**
	 * 是否是超级用户
	 */
	private boolean superUser;
	/**
	 * 是否是超级用户
	 * @return
	 */
	public boolean isSuperUser() {
		return superUser;
	}
	/**
	 * 是否是超级用户
	 * @param superUser
	 */
	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	
	
	/**
	 * 用户菜单树
	 */
	private MenuItem rootMenu;
	/**
	 * 用户菜单树
	 * @return
	 */
	public MenuItem getRootMenu() {
		return rootMenu;
	}
	/**
	 * 用户菜单树
	 * @param rootMenu
	 */
	public void setRootMenu(MenuItem rootMenu) {
		this.rootMenu = rootMenu;
	}
	
	
	/**
	 * 用户权限Map(id, url)
	 */
	private Map<Long, String> sourceMap;
	/**
	 * 用户权限Map(id, url)
	 * @return
	 */
	public Map<Long, String> getSourceMap() {
		return sourceMap;
	}
	/**
	 * 用户权限Map(id, url)
	 * @param sourceMap
	 */
	public void setSourceMap(Map<Long, String> sourceMap) {
		this.sourceMap = sourceMap;
	}
	
	
	public boolean isBackendUser(){
		return !sourceMap.isEmpty();
	}
}
