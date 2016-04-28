package com.mendao.business.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.dto.MenuItem;
import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Attachment;
import com.mendao.business.entity.School;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.repository.SchoolRepository;
import com.mendao.business.repository.UserInfoRepository;
import com.mendao.business.service.VerifyUserService;
import com.mendao.common.handler.EmailHandler;
import com.mendao.common.util.StringUtil;
import com.mendao.constant.MendaoConstant;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.framework.repository.SourceRepository;
import com.mendao.ronglian.util.enums.RonglianMsgEnum;
import com.mendao.util.CacheUtil;
import com.mendao.util.EncryptService;
import com.mendao.util.PropertiesUtil;
import com.mendao.util.RandomUtil;
import com.mendao.util.RegexUtil;

@Service
public class VerifyUserServiceImpl implements VerifyUserService{
	@Autowired
	EncryptService encryptService;
	
	@Autowired
	AccountRepository accountRep;
	
	@Autowired 
	UserInfoRepository userInfoRep;
	
	@Autowired 
	SourceRepository sourceRep;
	
	@Autowired
	SchoolRepository schoolRep;
	
	
	/**
	 * 登录操作
	 */
	@Override
	public UserProfile login(String userName, String password) throws BusinessCheckException{
		if(StringUtil.isBlank(userName)){
			throw new BusinessCheckException("用户账户不能为空");
		}
		if (StringUtil.isBlank(password)) {
			throw new BusinessCheckException("用户密码不能为空");
		}
		
		// 校验用户是否存在
		UserInfo user = null;
		if(RegexUtil.isMobile(userName)){
			user = userInfoRep.getAvailableByMobile(userName);
			if(null == user){
				throw new BusinessCheckException("账号或密码错误");
			}
		}else if(RegexUtil.isEmail(userName) && null != userInfoRep.getOneByEmail(userName)){
			user = userInfoRep.getAvailableByEmail(userName);
			if(null == user){
				throw new BusinessCheckException("账号或密码错误");
			}
		}else{
			user = userInfoRep.getAvailableByAcctName(userName);
			if(null == user){
				throw new BusinessCheckException("账号或密码错误");
			}
		}
		
		verifyUserPassword(user.getId(), password);
		
		UserProfile profile = new UserProfile();
		profile.setUserInfo(user);
		
		//初始化用户菜单、权限
		initUserPermission(profile);
		
		School school = schoolRep.findOne(profile.getUserInfo().getFwAccount().getId());
		profile.setSchool(school);
		
		return profile;
	}
	
	/**
	 * 注册用户
	 */
	@Transactional
	@Override
	public boolean register(UserProfile profile) throws BusinessCheckException{
		
		//校验用户名格式
		if(StringUtil.isBlank(profile.getMobile())){
			throw new BusinessCheckException("账号不能为空");
		}
		//校验登录密码是否有效
		if(StringUtil.isBlank(profile.getPassword())){
			throw new BusinessCheckException("密码不能为空");
		}
		if(!RegexUtil.matchPassword(profile.getPassword())){
			throw new BusinessCheckException("您输入的密码格式不正确"); //用户账户为6至12个字符
		}
		
		// 检查用户账号是否存在
		verifyMobileWhenRegister(profile.getMobile());
		
		// 检查昵称是否存在
		verifyNickNameWhenRegister(profile.getNickName());	
		
		// TODO 检查短信验证码。错误提示：您输入的短信验证码不正确
		
		
		// TODO 校验其它字段
		
		//设置账户信息
		FwAccount account = profile.getUserInfo().getFwAccount();
		if(null == account.getFund()){
			account.setFund(new BigDecimal(0));
		}
		if(null == account.getAmount()){
			account.setAmount(new BigDecimal(0));
		}
		account.setAcctPwd(encryptService.encrypt(profile.getPassword()));
		account.setStatus(1); 	//注册用户默认可用
		account.setSecurityCode(encryptService.encrypt(profile.getPassword())); 	//支付密码默认为登录密码
		accountRep.save(account);
		
		
		//设置用户详细信息
		UserInfo user = profile.getUserInfo();
		//user.setFwAccount(account);
		user.setId(account.getId()); 	//用户ID与账号ID相同

		userInfoRep.save(user);	//保存用户信息
		return true;
	}
	
	/**
	 * 
	 * @param target 荣联短信模版key值（register、forgetpwd、bindmobile）
	 * @param mobile
	 * @param userId
	 * @return
	 * @throws BusinessCheckException
	 */
	@Override
	public String getVerifyCode(String target, String mobile, Long userId) throws BusinessCheckException{

		RonglianMsgEnum ronglianMsg = RonglianMsgEnum.convert(target);
		switch (ronglianMsg) {
		case REGISTER:
			verifyMobileWhenRegister(mobile);
			break;
		case FORGET_PWD:
			verifyMobileWhenForgetPwd(mobile);
			break;
		case BIND_MOBILE:
			verifyMobileWhenBind(mobile, userId);
			break;
		default:
			break;
		}

		String templateid = PropertiesUtil.getProperty(ronglianMsg.getCode());
		String verifyCode = 9999 + "";//RandomUtil.randomNumber(4);
		
		// TODO 添加短信发送的参数
		String[] args = new String[]{};	
//		if(!RonglianUtil.instance().sendMsg(mobile, templateid, args)){
//			throw new BusinessCheckException("验证码发送失败，请重新发送");
//		}
		CacheUtil.setValue(MendaoConstant.VerifyCodePrefix + mobile, MendaoConstant.VerifyCodeInterval, verifyCode.toString());
		return verifyCode;
	}
	
	/**
	 * 根据邮箱地址
	 * @param target
	 * @param email
	 * @param userId
	 * @return
	 * @throws BusinessCheckException
	 */
	@Override
	public String getVerifyCodeByEmail(String target, String email, Long userId) throws BusinessCheckException{ 
		if(!RegexUtil.isEmail(email)){
			throw new BusinessCheckException("您输入的邮箱地址格式不正确");
		}
		if(target.equals("forgetpwd")){
			//verifyEmailWhenForget(email);
			if(null == userInfoRep.getOneByEmail(email)){
				throw new BusinessCheckException("您输入的邮箱地址未被注册");
			}
		}else if(target.equals("bindemail")){
			UserInfo user = userInfoRep.getOneByEmail(email);
			if(null != user && user.getId() != userId){
				throw new BusinessCheckException("您输入的邮箱地址已被绑定");
			}
		}
		String verifyCode = RandomUtil.randomNumber(4);
		String content = "您的邮箱验证码为：" + verifyCode;
		try {
			EmailHandler.instance().SendEmail("门道教育网验证码", content, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessCheckException("邮件服务器异常，请稍后重试");
		}
		CacheUtil.setValue(MendaoConstant.VerifyCodePrefix + email, MendaoConstant.VerifyCodeInterval, verifyCode.toString());
		
		return verifyCode;
	}
	
	@Override
	public boolean verifyUserPassword(Long id, String password) throws BusinessCheckException{
		String oldPassword = encryptService.encrypt(password);
		FwAccount account = accountRep.findOne(id);
		if(!StringUtil.equals(oldPassword, account.getAcctPwd())){
			throw new BusinessCheckException("当前密码错误");
		}
		return true;
	}
	
	@Override
	public boolean verifyUserSecurity(Long id, String security) throws BusinessCheckException{
		String oldPassword = encryptService.encrypt(security);
		FwAccount account = accountRep.findOne(id);
		if(!StringUtil.equals(oldPassword, account.getSecurityCode())){
			throw new BusinessCheckException("当前密码错误");
		}
		return true;
	}
	
	@Override
	public boolean resetPassword(String target, String password) throws BusinessCheckException{
		FwAccount acct = null;
		if(RegexUtil.isEmail(target)){
			UserInfo user = userInfoRep.getOneByEmail(target);
			if(user == null){
				throw new BusinessCheckException("您的邮箱地址有误");
			}else if(user.getFwAccount().getStatus() == 0){
				throw new BusinessCheckException("您的账号已被冻结，请联系门道客服");
			}
			acct = user.getFwAccount();
		}else if(RegexUtil.isMobile(target)){
			UserInfo user = userInfoRep.getOneByMobile(target);
			if(user == null){
				throw new BusinessCheckException("您的电话号码有误");
			}else if(user.getFwAccount().getStatus() == 0){
				throw new BusinessCheckException("您的账号已被冻结，请联系门道客服");
			}
			acct = user.getFwAccount();
		}
		
		acct.setAcctPwd(encryptService.encrypt(password));
		accountRep.save(acct);
		
		return true;
	}
	
	/**
	 * 验证用户昵称是否有效
	 * @param nickName
	 * @return
	 * @throws BusinessCheckException
	 */
	protected boolean verifyNickNameWhenRegister(String nickName) throws BusinessCheckException{
		
		if(nickName.replaceAll("[\u4e00-\u9fa5]", "00").length() > 16){
			throw new BusinessCheckException("您输入的昵称格式不正确");
		}else if(null != userInfoRep.getOneByNickName(nickName)){
			throw new BusinessCheckException("您输入的昵称已存在");
		}
		
		return true;
	}
	
	/**
	 * 注册时检查电话号码是否已经存在
	 * @param userName
	 * @return
	 * @throws BusinessCheckException 
	 */
	protected boolean verifyMobileWhenRegister(String mobile) throws BusinessCheckException{
		if(!RegexUtil.isMobile(mobile)){
			throw new BusinessCheckException("您输入的手机号码格式不正确");
		}else if(null != userInfoRep.getOneByMobile(mobile)){
			throw new BusinessCheckException("您输入的手机号码已被注册");
		}
		return true;
	}
	
	/**
	 * 忘记密码时检查电话号码是否已经存在
	 * @param mobile
	 * @return
	 * @throws BusinessCheckException 
	 */
	protected boolean verifyMobileWhenForgetPwd(String mobile) throws BusinessCheckException{
		if(!RegexUtil.isMobile(mobile)){
			throw new BusinessCheckException("您输入的手机号码格式不正确");
		}else if(null == userInfoRep.getOneByMobile(mobile)){
			throw new BusinessCheckException("您输入的绑定手机号码不正确");
		}
		return true;
	}
	
	/**
	 * 验证绑定的手机号码是否有效（当输入的手机号码已经被另一个人绑定时，该输入的号码无效）
	 * @param mobile
	 * @param userId
	 * @return
	 * @throws BusinessCheckException
	 */
	protected boolean verifyMobileWhenBind(String mobile, Long userId) throws BusinessCheckException{
		if(!RegexUtil.isMobile(mobile)){
			throw new BusinessCheckException("您输入的手机号码格式不正确");
		}
		UserInfo user = userInfoRep.getOneByMobile(mobile);
		if(user != null && user.getId() != userId){
			throw new BusinessCheckException("您输入的手机号码已被注册");
		}
		return true;
	}
	
	/**
	 * 忘记密码时检查邮箱是否已注册
	 * @param email
	 * @return
	 * @throws BusinessCheckException
	 */
	protected boolean verifyEmailWhenForget(String email) throws BusinessCheckException{
		if(!RegexUtil.isEmail(email)){
			throw new BusinessCheckException("您输入的邮箱地址格式不正确");
		}else if(null == userInfoRep.getOneByEmail(email)){
			throw new BusinessCheckException("您输入的邮箱地址未被注册");
		}
		return true;
	}
	
	/**
	 * 设置用户权限（包括菜单、权限）
	 * @param profile
	 */
	private void initUserPermission(UserProfile profile){
		Map<Long,String> sourceMap = new HashMap<Long, String>();
		
		// 获取用户的所有权限（超级用户默认所有权限）
		String hql = "";
		if(profile.isSuperUser()){
			hql  = "select t from FwSource t order by t.sortSeq";
		}else{
			hql = "select distinct s from FwSource s,FwDutySource d,FwAccountDuty a "
					+ " where s.id = d.fwSource.id and d.fwDuty.id=a.fwDuty.id "
					+ " and a.fwAccount.id=" + profile.getId() + " and a.fwDuty.status=1 and s.status = 1 order by s.sortSeq";
		}
		List<FwSource> sources = sourceRep.findListByHql(hql);
		
		// 生成菜单根目录ROOT
		MenuItem rootMenu = new MenuItem();
		rootMenu.setId(0L);
		rootMenu.setNodeName("根节点");
		
		for(FwSource s: sources){
			if(s.getIsMenu() == 1){
				MenuItem menu = new MenuItem();
				menu.setId(s.getId());
				menu.setUrl(s.getSourceAction());
				menu.setNodeName(s.getSourceName());
				if(StringUtil.isBlank(s.getTreePath())){
					//若为主菜单，则直接加入Root节点
					menu.setParentId(rootMenu.getId());
					menu.setParentNode(rootMenu);
					rootMenu.addChildNode(menu);
				}else{
					//若为子菜单，先找到父菜单，再将菜单加入父菜单中
					String parentId = s.getTreePath().substring(s.getTreePath().lastIndexOf(",") + 1);
					MenuItem parent = (MenuItem)rootMenu.findTreeNodeById(Long.valueOf(parentId));
					
					menu.setParentId(parent.getId());
					menu.setParentNode(parent);
					parent.addChildNode(menu);
				}
			}
			
			// 权限Map。用于用户操作时的权限验证
			sourceMap.put(s.getId(), s.getSourceAction());
		}
		
		profile.setSourceMap(sourceMap);
		profile.setRootMenu(rootMenu);
	}
}
