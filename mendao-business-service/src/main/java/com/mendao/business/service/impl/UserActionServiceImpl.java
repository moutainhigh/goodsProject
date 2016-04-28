package com.mendao.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.dto.Drawing;
import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.School;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.repository.AttachmentRepository;
import com.mendao.business.repository.SchoolRepository;
import com.mendao.business.repository.UserInfoRepository;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.common.enums.AccountIOEnum;
import com.mendao.common.handler.EmailHandler;
import com.mendao.common.util.DateUtil;
import com.mendao.common.util.StringUtil;
import com.mendao.constant.MendaoConstant;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwAccountIO;
import com.mendao.framework.repository.AccountIORepository;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.framework.repository.DutyRepository;
import com.mendao.framework.repository.SourceRepository;
import com.mendao.framework.service.LoginService;
import com.mendao.util.CacheUtil;
import com.mendao.util.EncryptService;
import com.mendao.util.RegexUtil;

import net.sf.json.JSONObject;

@Service("userActionService")
public class UserActionServiceImpl implements UserActionService{
	
	@Autowired 
	AccountRepository accountRep;
	
	@Autowired 
	AccountIORepository accountIORep;
	
	@Autowired
	UserInfoRepository userInfoRep;
	
	@Autowired
	AttachmentRepository attachmentRep;
	
	@Autowired
	SchoolRepository schoolRep;
	
	@Autowired
	DutyRepository dutyRep;
	
	@Autowired
	SourceRepository sourceRep;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	EncryptService encryptService;
	
	@Autowired
	VerifyUserService verifyUserService;

	@Override
	public UserProfile findUserInfoByAcctName(String acctName){
		UserInfo user = userInfoRep.getUserInfoByAcctName(acctName);
		if(null == user){
			return null;
		}
		UserProfile profile = new UserProfile();
		profile.setUserInfo(user);
		
		School school = schoolRep.findOne(user.getId());
		profile.setSchool(school);
		return profile;
	}

	
	
	@Override
	@Transactional
	public boolean updateUserInfo(UserProfile profile) throws BusinessCheckException{
		//校验用户名是否有效
		if(StringUtil.isBlank(profile.getAcctName())){
			throw new BusinessCheckException("账号不能为空");
		}else if(!Pattern.matches("([A-Za-z0-9_]{6,12})", profile.getAcctName())){
			throw new BusinessCheckException("账号格式错误");
		}
		
		UserInfo tmpUser = userInfoRep.getByAcctNameOrMobileOrEmail(profile.getAcctName());
		if(tmpUser != null && tmpUser.getId() != profile.getUserInfo().getId()){
			throw new BusinessCheckException("该账号已存在");
		}
		
		UserInfo user = userInfoRep.findOne(profile.getUserInfo().getId());
		
		//判断是否修改了登录账号(若原有的账号为空，则表示修改了账号)
		if(StringUtil.isBlank(user.getFwAccount().getAcctName())){
			user.getFwAccount().setAcctName(profile.getAcctName());
		}
		
		//判断是非修改了登录密码
		if(StringUtil.isNotBlank(profile.getPassword())){
			user.getFwAccount().setAcctPwd(encryptService.encrypt(profile.getPassword()));
		}
		
		//判断是否修改了支付密码
		if(StringUtil.isNotBlank(profile.getSecurityCode())){
			user.getFwAccount().setSecurityCode(encryptService.encrypt(profile.getSecurityCode()));
		}
		
		//判断是否修改了基金金额
		BigDecimal fund = profile.getUserInfo().getFwAccount().getFund();
		if(fund == null){
			fund = new BigDecimal(0);
		}
		user.getFwAccount().setFund(fund);
		
		
		// 修改用户详情信息
		user.setDistrict(profile.getUserInfo().getDistrict());
		user.setAge(profile.getUserInfo().getAge());
		user.setEmail(profile.getEmail());
		//user.setBirthday(birthday);
		user.setGender(profile.getUserInfo().getGender());
		user.setNickName(profile.getNickName());
		user.setMobile(profile.getMobile());
		//保存用户关注的标签
		user.setFollowSticker(profile.getUserInfo().getFollowSticker());
		user.setSection(""); 	// TODO
		
		
		
		userInfoRep.merge(user);	//保存用户信息
		profile.setUserInfo(user);
		return true;
	}
	
	@Override
	public PageEntity<UserInfo> getPage(PageEntity<UserInfo> myPage) {
		return userInfoRep.findByPage(myPage);
	}
	
	@Override
	public UserInfo findOneUser(Long id){
		return userInfoRep.findOne(id);
	}
	
	public boolean isUserExists(String userName){
		return false;
	}
	
	@Override
	public boolean updatePassword(Long id, String password, String newPassword) throws BusinessCheckException{
		
		String oldPassword = encryptService.encrypt(password);
		FwAccount account = accountRep.findOne(id);
		if(!StringUtil.equals(oldPassword, account.getAcctPwd())){
			throw new BusinessCheckException("当前密码错误");
		}
		
		//校验登录密码是否有效
		if(StringUtil.isBlank(newPassword)){
			throw new BusinessCheckException("新密码不能为空");
		}
		if(!RegexUtil.matchPassword(newPassword)){
			throw new BusinessCheckException("您输入的新密码格式不正确"); 
		}
		
		account.setAcctPwd(encryptService.encrypt(newPassword));
		
		accountRep.save(account);
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean updateSecurity(Long id, String password, String security, String newSecurity) throws BusinessCheckException{
		
		FwAccount account = accountRep.findOne(id);
		
		if(StringUtil.isNotBlank(password)){
			verifyUserService.verifyUserPassword(id, password); 
		}else{
			String oldSecurity = encryptService.encrypt(security);
			if(!StringUtil.equals(oldSecurity, account.getSecurityCode())){
				throw new BusinessCheckException("当前支付密码错误");
			}
		}
		
		if(StringUtil.isBlank(newSecurity)){
			throw new BusinessCheckException("新密码不能为空");
		}
		if(!RegexUtil.matchPassword(newSecurity)){
			throw new BusinessCheckException("您输入的新密码格式不正确"); 
		}
		
		account.setSecurityCode(encryptService.encrypt(newSecurity));
		
		accountRep.save(account);
		return true;
	}
	
	/**
	 * 
	 * @param id
	 * @param password
	 * @param mobile
	 * @return
	 * @throws BusinessCheckException
	 */
	@Override
	public boolean bindMobile(Long id, String password, String mobile, String verifyCode) throws BusinessCheckException{
		
		verifyUserService.verifyUserPassword(id, password);
		
		//校验登录密码是否有效
		if(StringUtil.isBlank(mobile)){
			throw new BusinessCheckException("电话号码不能为空");
		}
		if(!RegexUtil.isMobile(mobile)){
			throw new BusinessCheckException("您输入的手机号码格式不正确"); 
		}
		if(StringUtil.isBlank(verifyCode)){
			throw new BusinessCheckException("验证码不能为空"); 
		}
		
		//*****************************************************************************************************
		//* 检查验证码是否正确。
		//* 验证码存储在CacheUtil中，存储验证码的key值为前缀(MendaoConstant.VerifyCodePrefix) ＋ 电话号码
		//*****************************************************************************************************
		String memVerify = (String)CacheUtil.getValue(MendaoConstant.VerifyCodePrefix + mobile);
		if(!StringUtil.equals(memVerify, verifyCode)){
			throw new BusinessCheckException("验证码错误"); 
		}
		
		UserInfo user = userInfoRep.findOne(id);
		user.setMobile(mobile);
		
		userInfoRep.save(user);
		return true;
	}
	
	@Override
	public boolean bindEmail(Long id, String password, String email, String verifyCode) throws BusinessCheckException{
		
		//************************************************************************
		//* 校验用户密码及邮箱格式
		//************************************************************************
		if(StringUtil.isBlank(email)){
			throw new BusinessCheckException("邮箱地址不能为空");
		}
		if(!RegexUtil.isEmail(email)){
			throw new BusinessCheckException("您输入的邮箱格式不正确"); 
		}
		if(StringUtil.isBlank(verifyCode)){
			throw new BusinessCheckException("验证码不能为空"); 
		}
		verifyUserService.verifyUserPassword(id, password);
		
		//*****************************************************************************************************
		//* 检查验证码是否正确。
		//* 验证码存储在CacheUtil中，存储验证码的key值为前缀(MendaoConstant.VerifyCodePrefix) ＋ 邮箱地址
		//*****************************************************************************************************
		String memVerify = (String)CacheUtil.getValue(MendaoConstant.VerifyCodePrefix + email);
		if(!StringUtil.equals(memVerify, verifyCode)){
			throw new BusinessCheckException("验证码错误"); 
		}
				
		// TODO 创建邮箱验证码
		
		//************************************************************************
		//* 创建并发送绑定邮箱的邮件
		//************************************************************************
		StringBuffer content = new StringBuffer();
		try{
			EmailHandler.instance().SendEmail("门道教育网邮箱绑定", content.toString(), email);
		}catch(Exception e){
			throw new BusinessCheckException("邮件发送失败"); 
		}
		
		return true;
	}
	
	/**
	 * 用户付款
	 * @param userId
	 * @param targetId
	 * @param amount
	 * @return
	 * @throws BusinessCheckException
	 */
	@Override
	@Transactional
	public boolean payment(Long userId, Long targetId, BigDecimal amount) throws BusinessCheckException{
		
		FwAccount account = accountRep.findOne(userId);
		FwAccount target = accountRep.findOne(targetId);
		if(target == null){
			throw new BusinessCheckException("收款方账户错误");
		}
		if(account.getFund().compareTo(amount) < 0){
			throw new BusinessCheckException("您的余额不足");
		}
		
		//付款人账户变动信息
		FwAccountIO io0 = new FwAccountIO();
		io0.setAmount(new BigDecimal(0));
		io0.setFund(new BigDecimal(0).subtract(amount));
		io0.setCreateDate(new Date());
		io0.setTgAccount(target);
		io0.setFwAccount(account);
		io0.setIoType(AccountIOEnum.PAYMENT);
		account.setFund(account.getFund().subtract(amount));
		accountIORep.save(io0);
		accountRep.save(account);
		
		//收款人账户变动记录
		FwAccountIO io1 = new FwAccountIO();
		io1.setAmount(new BigDecimal(0));
		io1.setFund(amount);
		io1.setCreateDate(new Date());
		io1.setTgAccount(account);
		io1.setFwAccount(target);
		io1.setIoType(AccountIOEnum.GATHERING);
		target.setFund(target.getFund().add(amount));
		accountIORep.save(io1);
		accountRep.save(target);
		
		return true;
	}
	
	
	@Override
	public PageEntity<FwAccountIO> getPageOfAccountIO(PageEntity<FwAccountIO> myPage) {
		
		myPage = accountIORep.findByPage(myPage);
		if(myPage.getTotalpage() > 0){
			for(FwAccountIO a : myPage.getResult()){
				if(a.getTgAccount() != null){
					UserInfo user = userInfoRep.findOne(a.getTgAccount().getId());
					a.getTgAccount().setNickName(user.getNickName());
				}
			}
		}
		return myPage;
	}
	
	@Override
	@Transactional
	public boolean updateUserIcon(Long userId, String filePath) throws BusinessCheckException{
		UserInfo user = userInfoRep.findOne(userId);
		if(null == user){
			throw new BusinessCheckException("该用户不存在");
		}
		user.setIcon(filePath);
		userInfoRep.save(user);
		return true;
	}
	
	@Override
	@Transactional
	public boolean applyDrawing(Long id, Drawing drawing, String password) throws BusinessCheckException{
		UserInfo user = userInfoRep.findOne(id);
		verifyUserService.verifyUserSecurity(user.getId(), password);
		
		if(drawing.getAmount().compareTo(user.getFwAccount().getAmount()) > 0){
			throw new BusinessCheckException("余额不足");
		}
		FwAccountIO io = new FwAccountIO();
		io.setAmount(new BigDecimal(0).subtract(drawing.getAmount()));
		io.setFund(new BigDecimal(0));
		io.setCreateDate(new Date());
		io.setFwAccount(user.getFwAccount());
		io.setIoType(AccountIOEnum.DRAWING_APPLY);
		
		io.setDescription(JSONObject.fromObject(drawing).toString());
		FwAccount account = user.getFwAccount();
		account.setAmount(account.getAmount().subtract(drawing.getAmount()));
		accountIORep.save(io);
		accountRep.save(account);
		
		return true;
	}
	
	@Override
	@Transactional
	public void approveDrawing(Long id){
		FwAccountIO io = accountIORep.findOne(id);
		io.setIoType(AccountIOEnum.DRAWING_SUCCESS);
		accountIORep.save(io);
	}
}
