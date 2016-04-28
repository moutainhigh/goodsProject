package com.mendao.framework.repository;

import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwLogin;
@Repository("loginRepository")
public interface LoginRepository extends BaseRepository<FwLogin, Long>  {

}
