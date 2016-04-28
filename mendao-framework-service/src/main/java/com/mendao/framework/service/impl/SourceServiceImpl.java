package com.mendao.framework.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.repository.SourceRepository;
import com.mendao.framework.service.SourceService;
@Service
public class SourceServiceImpl  implements SourceService{

	@Autowired
	private SourceRepository sourceRepository ;
	
	@Transactional
	@Override
	public void saveSource(FwSource source){
		
		if(source.getParent() != null && source.getParent().getId() != null 
				&& source.getParent().getId() > 0){
			FwSource parent = sourceRepository.findOne(source.getParent().getId());
			//设置树目录
			source.setTreeLevel(parent.getTreeLevel() + 1);
			source.setTreePath(parent.getTreePath() + "," + parent.getId());
		}else{
			//设置树目录
			source.setTreeLevel(1);
			source.setTreePath("");
			source.setParent(null);
		}
		
		sourceRepository.save(source);
	}

	@Override
	public List<FwSource> getMenuSources() {
		return sourceRepository.findListByHql("select t from FwSource t where t.status='A' and t.id>1 order by t.style asc");
	}

	@Override
	public List<FwSource> getAllSourceDataAsTree() {
		return this.sourceRepository.findListByHql("select t from FwSource t order by t.sortSeq ");
	}

	@Override
	public FwSource findOne(Long id) {
		return sourceRepository.findOne(id);
	}

	@Override
	public PageEntity<FwSource> findPage(PageEntity<FwSource> pageBean) {
		return sourceRepository.findByPage(pageBean);
	}
}
