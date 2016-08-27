package com.mendao.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.repository.FShowProductRepository;
import com.mendao.business.repository.PKindRepository;
import com.mendao.business.repository.ProductPicRepository;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	DProductRepository dProductRepository;
	
	@Autowired
	FProductRepository fProductRepository;
	
	@Autowired
	PKindRepository pKindRespository;
	
	@Autowired
	FShowProductRepository fShowProductRepository;
	
	@Autowired
	ProductPicRepository productPicRepository;
	/**
	 * 产品列表 代理
	 */
	@Override
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity) {
		return dProductRepository.findByPage(pageEntity);
	}
	
	/**
	 * 添加代理产品
	 */
	public DProduct addDProduct(DProduct dProduct){
		return dProductRepository.save(dProduct);
	}
	
	/**
	 * 通过id查找代理产品
	 */
	public DProduct findDProductById(Long id){
		return dProductRepository.findOne(id);
	}
	
	/**
	 * 修改代理产品
	 */
	public void updateDProduct(DProduct dProduct){
		dProductRepository.merge(dProduct);
	}
	
	/**
	 * 修改分销产品
	 */
	public void updateFProduct(FProduct fProduct){
		fProductRepository.merge(fProduct);
	}
	/**
	 * 删除产品 代理
	 */
	@Override
	public void deleteDProductById(Long id) {
		//删除分销图片
		productPicRepository.deletePicByDProductId1(id);
		//删除分销产品
		fProductRepository.deleteFProductById(id);
		//删除可见关联
		fShowProductRepository.deleteByDProductId(id);
		//代理产品删除图片
		productPicRepository.deletePicByDProductId(id);
		//删除代理产品
		dProductRepository.deleteDProductById(id);
	}
	
	public void deleteFProductById(Long id){
		//删除代理产品(物理删除)
		fProductRepository.delete(id);
	}
	
	/**
	 * 产品列表 分销
	 */
	@Override
	public PageEntity<FProduct> getFProductPage(PageEntity<FProduct> pageEntity) {
		return fProductRepository.findByPage(pageEntity);
	}
	/**
	 * 根据sql查询    分销分页
	 */
	@Override
	public PageEntity<FProduct> getFProductPageBySql(PageEntity<FProduct> pageEntity) {
		StringBuffer sql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		sql.append("select t.* from t_f_product t left join t_shop_user user on t.create_user_id=user.id left join t_d_product d on d.id = t.d_product  where d.delete_flag = 0 and d.status = 1 and t.delete_flag = 0 and t.on_sale = 1 ");
		if(pageEntity.getParams().get("pName") != null){
			sql.append(" and t.p_name like '%");
			sql.append(pageEntity.getParams().get("pName"));
			sql.append("%' ");
		}
		if(pageEntity.getParams().get("modifyUserId.id") != null){
			sql.append(" and t.modify_user_id = ");
			sql.append(pageEntity.getParams().get("modifyUserId.id"));
		}
		if(pageEntity.getParams().get("createUserId.endDate") != null){
			sql.append(" and user.end_date > '");
			sql.append(pageEntity.getParams().get("createUserId.endDate"));
			sql.append("'");
		}
		if(pageEntity.getParams().get("kindId") != null){
			List<PKind> kindList = pKindRespository.getListByName(String.valueOf(pageEntity.getParams().get("kindId")));
			if(kindList != null && kindList.size() > 0){
				sql.append(" and (");
				for(int i=0;i<kindList.size();i++){
					if(i == 0){
						sql.append(" t.kind_id like '%");
						sql.append(kindList.get(i).getId());
						sql.append("%'");
					}else{
						sql.append(" or t.kind_id like '%");
						sql.append(kindList.get(i).getId());
						sql.append("%'");
					}
				}
				sql.append(" )");
			}
		}
		pageEntity.setProcedure(sql.toString());
		return fProductRepository.findByPageBySql(pageEntity, list);
	}

	/**
	 * 查询类目
	 */
	@Override
	public PageEntity<PKind> getPKindPage(PageEntity<PKind> pageEntity) {
		return pKindRespository.findByPage(pageEntity);
	}
	/**
	 * 添加类目
	 */
	@Override
	public PKind addPKind(PKind pKind) {
		return pKindRespository.save(pKind);
	}
	/**
	 * 通过id查找类目
	 */
	@Override
	public PKind findById(Long id) {
		return pKindRespository.findOne(id);
	}
	
	/**
	 * 修改类目
	 */
	@Override
	public void update(PKind pKind) {
		pKindRespository.merge(pKind);;
	}
	/**
	 * 删除类目
	 */
	@Override
	public void deletePKindById(Long id) {
		pKindRespository.delete(id);
	}
	
	/**
	 * 查询所有的类目
	 */
	public List<PKind> queryAllPropertiesByCreateId(Long id){
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where create_id = " + id;
		return pKindRespository.findAllBySql(PKind.class, hql);
	}

	/**
	 * 批量修改产品状态
	 */
	@Override
	public void updateProductStatus(Integer status,String ids) {
		String[] idString = ids.split(",");
		List<Long> idsList = new ArrayList<Long>();
		for(int i = 0; i < idString.length; i++){
			idsList.add(Long.parseLong(idString[i]));
		}
		dProductRepository.updateProductStatusByIds(status, idsList);
	}

	@Override
	public List<ShopUser> getAllDaiLiByCurrentUserId(Long id) {
		return fProductRepository.queryDailiByFenxiao(id);
	}

	@Override
	public boolean updateFProductOnSale(Integer onSale, String ids) {
		try{
			if(ids.length() > 0){
				String[] idsArray = ids.split(",");
				List<Long> idsList = new ArrayList<Long>();
				for(int i = 0; i < idsArray.length; i++){
					idsList.add(Long.parseLong(idsArray[i]));
				}
				if(onSale <= 2){
					fProductRepository.updateFProductOnSaleByIds(onSale, idsList);
				}else if(onSale == 3){
					fProductRepository.updateFProductDeleteFlagByIds(-1, idsList);
				}else if(onSale == 4){
					fProductRepository.updateFProductDeleteFlagByIds(0, idsList);
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public FProduct getFProductById(Long id) {
		return fProductRepository.findOne(id);
	}

	@Override
	public List<FProduct> getByModifyUserId(Long modifyUserId, Long id,int limit) {
		return fProductRepository.getByModifyUserId(modifyUserId, id);
	}

	@Override
	public List<PKind> queryAllByYewuId(Long id) {
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where create_id = "+id+" or create_id in (select parent_id from t_user_relation where child_id = "+id+") group by kind_name" ;
		return pKindRespository.findAllBySql(PKind.class, hql);
	}

	@Override
	public List<PKind> getKindByIds(String showKind) {
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where id in ("+showKind+")";
		return pKindRespository.findAllBySql(PKind.class, hql);
	}

	@Override
	public int getOnSoleProductNum(Long userId) {
		return fProductRepository.getOnSoleProductNum(userId);
	}

	@Override
	public int getProductCountByUserId(Long id) {
		List<DProduct> list = dProductRepository.getAllByUserId(id);
		if(list != null && list.size() > 0){
			return list.size();
		}
		return 0;
	}

	@Override
	public int getDownTimeProductCountByUserId(Long id) {
		List<DProduct> list = dProductRepository.getAllByUserId(id);
		if(list != null && list.size() > 0){
			int i =0;
			for(DProduct dp:list){
				if(dp.getDownTime() != null && dp.getDownTime().getTime() > new Date().getTime()){
					i++;
				}
			}
			return i;
		}
		return 0;
	}

	@Override
	public void xiajiaProduct(Long id) {
		//删除好友代理产品图片
		productPicRepository.deletePicByDProductId1(id);
		//删除可见关联
		fShowProductRepository.deleteByDProductId(id);
		//删除好友代理产品
		fProductRepository.deleteByDProductId(id);
	}

	@Override
	public int getNotChangeProduct(Long id) {
		return fProductRepository.getNotChangeProduct(id);
	}
}
