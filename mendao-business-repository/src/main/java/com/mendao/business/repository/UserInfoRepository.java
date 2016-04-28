package com.mendao.business.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.UserInfo;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("userInfoRepository")
public interface UserInfoRepository extends BaseRepository<UserInfo, Long>  {

    @Query("select t from UserInfo t where t.fwAccount.acctName = :acctName and t.fwAccount.status = 1")
    public UserInfo getUserInfoByAcctName(@Param("acctName") String acctName);
    
    @Query("select t from UserInfo t where (t.fwAccount.acctName = :acctName or t.mobile = :acctName or t.email = :acctName)")
    public UserInfo getByAcctNameOrMobileOrEmail(@Param("acctName") String acctName);
    
    /**
     * 根据用户账号查找用户（包括已禁用的用户）
     * @param acctName
     * @return
     */
    @Query("select t from UserInfo t where t.fwAccount.acctName = :acctName")
    public UserInfo getOneByAcctName(@Param("acctName") String acctName);
    
    /**
     * 根据用户账号查找可用的用户
     * @param acctName
     * @return
     */
    @Query("select t from UserInfo t where t.fwAccount.acctName = :acctName and t.fwAccount.status = 1")
    public UserInfo getAvailableByAcctName(@Param("acctName") String acctName);
    
    /**
     * 根据用户手机号查找用户（包括已禁用的用户）
     * @param mobile
     * @return
     */
    @Query("select t from UserInfo t where t.mobile = :mobile")
    public UserInfo getOneByMobile(@Param("mobile") String mobile);
    
    /**
     * 根据用户手机号查找可用的用户
     * @param mobile
     * @return
     */
    @Query("select t from UserInfo t where t.mobile = :mobile and t.fwAccount.status = 1")
    public UserInfo getAvailableByMobile(@Param("mobile") String mobile);
    
    /**
     * 根据用户电子邮箱查找用户（包括已禁用的用户）
     * @param email
     * @return
     */
    @Query("select t from UserInfo t where t.email = :email")
    public UserInfo getOneByEmail(@Param("email") String email);
    
    /**
     * 根据用户电子邮箱查找可用的用户
     * @param email
     * @return
     */
    @Query("select t from UserInfo t where t.email = :email and t.fwAccount.status = 1")
    public UserInfo getAvailableByEmail(@Param("email") String email);
    
    
    /**
     * 根据用户昵称查找用户（包括已禁用的用户）
     * @param mobile
     * @return
     */
    @Query("select t from UserInfo t where t.nickName = :nickName")
    public UserInfo getOneByNickName(@Param("nickName") String nickName);
}
