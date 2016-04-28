package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SchoolAttachment;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("schoolAttachRepository")
public interface SchoolAttachmentRepository extends BaseRepository<SchoolAttachment, Long>  {

}
