package com.mendao.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.BranchSchool;
import com.mendao.business.entity.School;
import com.mendao.business.entity.SchoolProduct;
import com.mendao.business.entity.Topic;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.repository.AttachmentRepository;
import com.mendao.business.repository.BranchSchoolRepository;
import com.mendao.business.repository.SchoolAttachmentRepository;
import com.mendao.business.repository.SchoolRepository;
import com.mendao.business.repository.UserInfoRepository;
import com.mendao.business.service.SchoolService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.util.EncryptService;
import com.mendao.util.Util;

import jodd.util.StringUtil;

@Service("schoolService")
public class SchoolServiceImpl extends BaseServiceImpl<School, Long> implements SchoolService{

	@Autowired
	SchoolRepository schoolRep;
	
	@Autowired
	AccountRepository accountRep;
	
	@Autowired
	UserInfoRepository userInfoRep;
	
	@Autowired
	BranchSchoolRepository branchRep;
	
	@Autowired
	SchoolAttachmentRepository schoolAttamentRep;
	
	@Autowired
	AttachmentRepository attachmentRep;
	
	@Autowired
	EncryptService encryptService;
	
	@Autowired
	VerifyUserService verifyService;
	
	@Override
	public PageEntity<School> getPage(PageEntity<School> myPage) {
		return schoolRep.findByPage(myPage);
	}

	/**
	 * 新增（修改）机构详情
	 */
	@Override
	@Transactional
	public void save(School school) {
		FwAccount account = null;
		if(null == school.getFwAccount().getId()){
			account = new FwAccount();
		}else{
			account = accountRep.findOne(school.getFwAccount().getId());
		}
		
		account.setAcctName(school.getFwAccount().getAcctName());
		account.setAmount(school.getFwAccount().getAmount());
		account.setStatus(1);
		if(!StringUtil.isBlank(school.getFwAccount().getAcctPwd())){
			account.setAcctPwd(encryptService.encrypt(school.getFwAccount().getAcctPwd()));
		}
		
		if(!StringUtil.isBlank(school.getFwAccount().getSecurityCode())){
			account.setSecurityCode(encryptService.encrypt(school.getFwAccount().getSecurityCode()));
		}
		accountRep.save(account);
		UserInfo user = userInfoRep.findOne(account.getId());
		if(user == null){
			user = new UserInfo();
			user.setFwAccount(account);
			user.setId(account.getId());
			user.setNickName(school.getSchoolName());
			userInfoRep.save(user);
		}
		school.setFwAccount(account);
		school.setId(account.getId());
		schoolRep.save(school);
	}

	/**
	 * 上传机构环境图片
	 * @param schoolId
	 * @param imgPath
	 */
	@Transactional
	public void addSchoolImage(Long schoolId, String imgPath){
		
	}
	
	/**
	 * 删除机构环境图片
	 * @param schoolId
	 * @param attachmentId
	 */
	@Transactional
	public void deleteSchoolImage(Long schoolId, String attachmentId){
		
	}
	
	/**
	 * 新增机构课程产品
	 * @param product
	 */
	@Transactional
	public void addProduct(SchoolProduct product){
		
	}
	
	/**
	 * 删除机构课程产品
	 * @param productId
	 */
	@Transactional
	public void deleteProduct(Long productId){
		
	}
	
	public void addBranchSchool(BranchSchool branch){
		
	}

	@Override
	public List<School> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return schoolRep.findListByHql("from School t where t.id in (" + param + ")");
		}
		return null;
	}

	@Override
	public void update(School school) {
		schoolRep.save(school);
	}
	//机构列表搜索分页显示
	@Override
	public PageEntity<School> getSchoolPage(PageEntity<School> pageEntity) {
		String categoryId = (String) pageEntity.getParams().get("categoryId");
		String schoolName = (String)pageEntity.getParams().get("schoolName");
		String orderType = (String)pageEntity.getParams().get("orderType");
		List<Object> queryParams = new ArrayList<Object>();
		StringBuffer countSql = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		countSql.append("SELECT  count(distinct a.id) from  bu_school a "+
				"JOIN bu_school_sticker s on s.school_id = a.id  JOIN bu_sticker bs on bs.id = s.sticker_id "+ 
				"JOIN bu_category d on bs.category_id = d.id ");
		sql.append("SELECT  distinct a.* from  bu_school a "+
					"JOIN bu_school_sticker s on s.school_id = a.id  JOIN bu_sticker bs on bs.id = s.sticker_id "+ 
					"JOIN bu_category d on bs.category_id = d.id ");
		if(categoryId == null){
			countSql.append("JOIN bu_category f on d.tree_path like concat(f.tree_path, '%') ");
			sql.append("JOIN bu_category f on d.tree_path like concat(f.tree_path, '%') ");
		}else{
			countSql.append("JOIN bu_category f on (f.id in (" + categoryId +") or f.parent_id in ("+categoryId+")) and d.tree_path like concat(f.tree_path, '%') ");
			sql.append("JOIN bu_category f on (f.id in (" + categoryId +") or f.parent_id in ("+categoryId+")) and d.tree_path like concat(f.tree_path, '%') ");
//			List<String> categories = new ArrayList<String>();
//			for(String c : categoryId.split(",")){
//				categories.add(c);
//			}
//			queryParams.add(categories);
		}
		if(schoolName != null && !schoolName.equals("")){
			countSql.append("where a.school_name like ?");
			sql.append("where a.school_name like ?");
			queryParams.add("%" + schoolName + "%");
		}
		if(orderType != null && !orderType.equals("")){
			if(orderType.equals("1")){
				countSql.append(" order by s.sort_seq asc ");
				sql.append(" order by s.sort_seq asc ");
			}else if(orderType.equals("2")){
				countSql.append(" order by a.comment_number desc,s.sort_seq asc ");
				sql.append(" order by a.comment_number desc,s.sort_seq asc ");
			}
		}else{
			countSql.append(" order by s.sort_seq asc ");
			sql.append(" order by s.sort_seq asc ");
		}
		
		Object total = schoolRep.getSingleResult(countSql.toString(), queryParams);
		pageEntity.setProcedure(sql.toString());
		if(((java.math.BigInteger) total).intValue() > 0){
			pageEntity = schoolRep.findByPageBySql(pageEntity, queryParams);
			pageEntity.setTotalpage(((java.math.BigInteger) total).intValue() / pageEntity.getPagesize() + 1);
		}else{
			pageEntity.setResult(new ArrayList<School>());
		}
		return pageEntity;
	}
}
