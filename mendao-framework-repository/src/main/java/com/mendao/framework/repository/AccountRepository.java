package com.mendao.framework.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwAccount;
@Repository("accountRepository")
public interface AccountRepository extends BaseRepository<FwAccount, Long>  {


    @Query("select t from FwAccount t where t.acctName = :acctName and t.status = 1")
    public List<FwAccount> findFwAccountByAcctName(@Param("acctName") String acctName);

}
