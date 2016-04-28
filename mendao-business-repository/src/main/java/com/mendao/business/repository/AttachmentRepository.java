package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Attachment;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("attachmentRepository")
public interface AttachmentRepository extends BaseRepository<Attachment, Long>  {

   

}
