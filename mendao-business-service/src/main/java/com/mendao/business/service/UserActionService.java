package com.mendao.business.service;

import java.math.BigDecimal;

import com.mendao.business.dto.Drawing;
import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.UserInfo;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccountIO;

public interface UserActionService {

	/**
	 * 根据用户名获取用户信息
	 * @param acctName
	 * @return
	 * @throws BusinessCheckException
	 */
	UserProfile findUserInfoByAcctName(String acctName);

	/**
	 * 获取用户分页数据
	 * @param myPage
	 * @return
	 */
	PageEntity<UserInfo> getPage(PageEntity<UserInfo> myPage);

	UserInfo findOneUser(Long id);

	boolean updateUserInfo(UserProfile profile) throws BusinessCheckException;

	boolean updatePassword(Long id, String password, String newPassword) throws BusinessCheckException;

	boolean updateSecurity(Long id, String password, String security, String newSecurity) throws BusinessCheckException;

	boolean bindMobile(Long id, String password, String mobile, String verifyCode) throws BusinessCheckException;

	boolean bindEmail(Long id, String password, String email, String verifyCode) throws BusinessCheckException;

	boolean payment(Long userId, Long targetId, BigDecimal amount) throws BusinessCheckException;

	PageEntity<FwAccountIO> getPageOfAccountIO(PageEntity<FwAccountIO> myPage);

	boolean updateUserIcon(Long userId, String filePath) throws BusinessCheckException;

	boolean applyDrawing(Long id, Drawing drawing, String password) throws BusinessCheckException;

	void approveDrawing(Long id);
}
