package com.mendao.business.service;

import com.mendao.business.dto.UserProfile;
import com.mendao.exception.BusinessCheckException;
import com.mendao.ronglian.util.enums.RonglianMsgEnum;

public interface VerifyUserService{

	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 * @throws BusinessCheckException
	 */
	UserProfile login(String userName, String password) throws BusinessCheckException;

	/**
	 * 注册用户
	 * @param profile
	 * @return
	 * @throws BusinessCheckException
	 */
	boolean register(UserProfile profile) throws BusinessCheckException;

	
	/**
	 * 
	 * @param target 荣联短信模版key值（register、forgetpwd、bindmobile）
	 * @param mobile
	 * @param userId
	 * @return
	 * @throws BusinessCheckException
	 */
	String getVerifyCode(String target, String mobile, Long userId) throws BusinessCheckException;

	boolean verifyUserPassword(Long id, String password) throws BusinessCheckException;
	
	boolean verifyUserSecurity(Long id, String security) throws BusinessCheckException;

	/**
	 * 
	 * @param target
	 * @param email
	 * @param userId
	 * @return
	 * @throws BusinessCheckException
	 */
	String getVerifyCodeByEmail(String target, String email, Long userId) throws BusinessCheckException;

	/**
	 * 根据邮箱（电话）修改用户密码
	 * @param target
	 * @param password
	 * @return
	 * @throws BusinessCheckException
	 */
	boolean resetPassword(String target, String password) throws BusinessCheckException;
}
