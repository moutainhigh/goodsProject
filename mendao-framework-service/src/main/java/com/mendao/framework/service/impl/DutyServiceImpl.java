package com.mendao.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwDuty;
import com.mendao.framework.entity.FwDutySource;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.repository.DutyActionRepository;
import com.mendao.framework.repository.DutyRepository;
import com.mendao.framework.service.DutyService;
import com.mendao.util.EncryptService;
import com.mendao.util.Util;

@Service
public class DutyServiceImpl implements DutyService {

	@Autowired
	private DutyRepository dutyRepository;

	@Autowired
	private DutyActionRepository dutyActionRepository;

	public PageEntity<FwDuty> getPage(PageEntity<FwDuty> myPage) {

		return dutyRepository.findByPage(myPage);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	public void saveRole(FwDuty duty) {
		dutyRepository.save(duty);
	}

//	/**
//	 * 保存用户.
//	 *
//	 * @param account 用户对象
//	 * @return 返回保存是否成功
//	 */
//	@Autowired
//	private EncryptService encryptServiceImpl;

	/**
	 * 分配保存方法.
	 *
	 * @param roleId 角色id
	 * @param operationIds 操作动作数组
	 */
	@Transactional
	public void assignOperation(final Long roleId, final Long[] operationIds) {
		FwDuty duty = dutyRepository.findOne(roleId);
		duty.getFwDutySources().clear();
		this.dutyRepository.merge(duty);
		for (int i = 0; i < operationIds.length; i++) {
			if (operationIds[i] == null) {
				continue;
			}
			FwDutySource dutyAction = new FwDutySource();
			dutyAction.setFwDuty(duty);
			FwSource fwSource = new FwSource();
			fwSource.setId(operationIds[i]);
			dutyAction.setFwSource(fwSource);
			this.dutyActionRepository.save(dutyAction);
		}
	}

	public List<FwDuty> getAvailableRoles() {
		return dutyRepository.findListByHql("select t from Duty t where t.status=1");
	}

	public FwDuty getRoleById(Long roleId) {
		FwDuty fwDuty = dutyRepository.findOne(roleId);
		return fwDuty;
	}


	public List<FwDuty> findDatasByIds(String[] ids) {
		String param = Util.getIdSQLParam(ids);
		if (param != null) {
			return dutyRepository.findListByHql("from FwDuty t where t.id in (" + param + ")");
		}
		return null;
	}

	@Transactional
	public void deleteAll(List<FwDuty> list) {
		dutyRepository.delete(list);
	}

	/**
	 * 根据操作类型,更新用户列表所有用户.
	 *
	 * @param userList
	 *            用户对象列表
	 * @param type
	 *            操作类型
	 */
	public void updateUser(final List<FwAccount> userList, final String type) {
		// // 如果类型为空退出
		// if (type != null && type.trim().length() > 0) {
		// // 循环用户对象列表
		// for (FwAccount user : userList) {
		// // 如果操作类型为重置密码
		// if ("resetPwd".equals(type)) {
		// // 设置用户密码为111111,并且用算法加密
		// user.setAcctPwd(encryptServiceImpl.encrypt("111111"));
		// } else if ("active".equals(type)) {
		// // 设置用户状态为A
		// user.setStatus("A");
		// } else if ("inactive".equals(type)) {
		// // 设置用户状态为D
		// user.setStatus("D");
		// }
		// // 更新用户对象
		// hibernateTemplate.update(user);
		// }
		// }
	}
}
