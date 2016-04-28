package com.mendao.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Topic;
import com.mendao.business.repository.TopicRepository;
import com.mendao.business.service.TopicService;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.Util;

/**
 * 话题service实现类
 * @author warden
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService{

	@Autowired
	private TopicRepository topicRep;
	/**
	 * 获取话题列表分页
	 */
	@Override
	public PageEntity<Topic> getPage(PageEntity<Topic> myPage) {
		return topicRep.findByPage(myPage);
	}
	
	@Override
	public PageEntity<Topic> getPageUsingProcedure(PageEntity<Topic> myPage) {
//		if(StringUtil.isBlank(myPage.getProcedure())){
//			return topicRep.findByPage(myPage);
//		}else{
			myPage.setProcedure("{call search_topic(?,?,?,?,?)}");
			List<Object> params = new ArrayList<Object>();
			String keywords = StringUtil.defaultIfBlank((String)myPage.getParams().get("keywords"));
			Long sticker = (Long)myPage.getParams().get("sticker");
			params.add(keywords);
			params.add(sticker);
			params.add(myPage.getOrderBy());
			params.add(myPage.getCurrentpage() == 0 ? 1 : myPage.getCurrentpage());
			params.add(myPage.getPagesize() < 1 ? 10 : myPage.getPagesize());
			
			List<Object> cntParams = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(distinct t.id) "
					+ "from co_topic t join co_topic_sticker ts on t.id = ts.topic_id "
					+ "where t.examined = 1 and t.status = 0 ");
			if(sticker > 0L){
				cntParams.add(sticker);
				sql.append("and ts.sticker_id = ? ");
			}
			if(StringUtil.isNotBlank(keywords)){
				cntParams.add("%" + keywords + "%");
				sql.append("and t.subject like ?");
			}
			Object total = topicRep.getSingleResult(sql.toString(), cntParams);
			if(((java.math.BigInteger) total).intValue() > 0){
				myPage = topicRep.findByPageUsingProcedure(myPage, params);
				myPage.setTotalpage(((java.math.BigInteger) total).intValue() / myPage.getPagesize() + 1);
			}else{
				myPage.setResult(new ArrayList<Topic>());
			}
			return myPage;
//		}
		
		//return pageBean;
	}
	/**
	 * 保存话题
	 */
	@Override
	public void save(Topic topic) {
		topicRep.save(topic);
	}
	/**
	 * 根据ID查找话题
	 */
	@Override
	public Topic findById(Long id) {
		return topicRep.findOne(id);
	}
	/**
	 * 根据ids查找
	 */
	@Override
	public List<Topic> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return topicRep.findListByHql("from Topic t where t.id in (" + param + ")");
		}
		return null;
	}

	@Override
	public List<Topic> findList(){
		StringBuffer sb = new StringBuffer();
		sb.append("set @row=0;set @stk=0;");
		sb.append("select t.* ");
		sb.append(", case when @stk = f.sticker then @row:=@row+1 else @row:=1 end rownum, @stk:=f.sticker stk ");
		sb.append("from co_opic t join ");
		sb.append("(select ts.id, ts.topic_id, min(s.sort_seq) sticker ");
		sb.append("from co_topic_sticker ts inner join bu_sticker s on ts.sticker_id = s.id ");
		sb.append("group by ts.topic_id) f on t.id = f.topic_id order by rownum;");
		
		List<Topic> list = topicRep.findAllBySql(Topic.class, "{call search_topics()}");
		return list;
	}

	@Override
	public List<Topic> findRelationTopic(PageEntity<Topic> myPage) {
		List<Object> queryParams = new ArrayList<Object>();
		String sql = "select distinct c.* from co_topic c join co_topic_sticker ts on ts.topic_id = c.id where c.id != ? and c.id in (select t.topic_id from co_topic_sticker t where t.sticker_id = ts.sticker_id ) order by (c.views+1) *(c.comment_number+1)*(c.ups+1) desc";
		queryParams.add(myPage.getParams().get("topicId"));
		myPage.setProcedure(sql);
		myPage = topicRep.findByPageBySql(myPage, queryParams);
		return myPage.getResult();
	}
}
