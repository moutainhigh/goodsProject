package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SchoolProduct;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("schoolProductRepository")
public interface SchoolProductRepository extends BaseRepository<SchoolProduct, Long>  {

}
