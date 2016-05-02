package com.mendao.framework.repository;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.Menu;
@Repository("menuRepository")
public interface MenuRepository extends BaseRepository<Menu, Long>  {
}
