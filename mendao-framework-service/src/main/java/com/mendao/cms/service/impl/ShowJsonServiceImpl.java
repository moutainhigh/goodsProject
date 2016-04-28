package com.mendao.cms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.cms.service.ShowJsonService;





@Service
@Transactional
public class ShowJsonServiceImpl implements ShowJsonService {
//	@Autowired
//	private ShowJsonDao dao;
//	
//	private final static  String dayFormat = "%Y%m%d";
//	
//	private final static  String monthFormat = "%Y%m";
//	
//	private final static String month = "month";
//	
//	@Transactional(readOnly = true)
//	public List<ShowJsonEntity> showPhoneJsonEntity( String isView) {
//		StringBuffer bf = new StringBuffer();
//		 bf.append(" SELECT DATE_FORMAT(t.adddate,?) AS datad ,COUNT(*) FROM phonerecord t GROUP BY  DATE_FORMAT(t.adddate,?) ORDER BY DATE_FORMAT(t.adddate,?) desc LIMIT 31 ");
//		 List<Object[]> results = this.dao.querySqlResult(bf.toString(),StringUtil.equals(isView, month)?monthFormat:dayFormat);
//		 List<ShowJsonEntity> resutJsonEn = new ArrayList<ShowJsonEntity>();
//		 ShowJsonEntity sj = null;
//		 Map<String,Integer> resutMap = new HashMap<String,Integer>();
//		 if(results!= null && results.size()>0){
//			 for(Object[] obj : results){
//				 resutMap.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
//			 }
//			 for(String dateStr : StringUtil.equals(isView, month)?this.getNearestMonth(new Date(), 15):this.getNearestDay(new Date(), 15)){
//				 sj = new ShowJsonEntity();
//				  sj.setName(dateStr);
//				  sj.setValue(resutMap.get(dateStr) == null ?0:resutMap.get(dateStr));
//				  resutJsonEn.add(sj);
//			 }
//		 }
//		 return resutJsonEn ;
//	}
//	@Transactional(readOnly = true)
//	public List<ShowJsonEntity> showMemberJsonEntity( String isView) {
//		StringBuffer bf = new StringBuffer();
//		 bf.append(" SELECT DATE_FORMAT(t.`registerDate`,?) AS datad ,COUNT(*) FROM member t GROUP BY  DATE_FORMAT(t.`registerDate`,?) ORDER BY DATE_FORMAT(t.`registerDate`,?) DESC LIMIT 31 ");
//		 List<Object[]> results = this.dao.querySqlResult(bf.toString(),StringUtil.equals(isView, month)?monthFormat:dayFormat);
//		 List<ShowJsonEntity> resutJsonEn = new ArrayList<ShowJsonEntity>();
//		 ShowJsonEntity sj = null;
//		 Map<String,Integer> resutMap = new HashMap<String,Integer>();
//		 if(results!= null && results.size()>0){
//			 for(Object[] obj : results){
//				 resutMap.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
//			 }
//			 for(String dateStr : StringUtil.equals(isView, month)?this.getNearestMonth(new Date(), 15):this.getNearestDay(new Date(), 15)){
//				 sj = new ShowJsonEntity();
//				  sj.setName(dateStr);
//				  sj.setValue(resutMap.get(dateStr) == null ?0:resutMap.get(dateStr));
//				  resutJsonEn.add(sj);
//			 }
//		 }
//		 return resutJsonEn ;
//	}
//	@Transactional(readOnly = true)
//   public List<ShowJsonEntity> showOrderJsonEntity( String isView){
//		StringBuffer bf = new StringBuffer();
//		 bf.append(" SELECT DATE_FORMAT(t.`adddate`,?) AS datad,t.`state` ,COUNT(*) FROM tradingrecord t GROUP BY  DATE_FORMAT(t.`adddate`,?),t.`state`  ORDER BY DATE_FORMAT(t.`adddate`,?) DESC LIMIT 63 ");
//		 List<Object[]> results = this.dao.querySqlResult(bf.toString(),StringUtil.equals(isView, month)?monthFormat:dayFormat);
//		 List<ShowJsonEntity> resutJsonEn = new ArrayList<ShowJsonEntity>();
//		 ShowJsonEntity sj = null;
//		 Map<String,String> resutMap = new HashMap<String,String>();
//		 if(results!= null && results.size()>0){
//			 for(Object[] obj : results){
//				 resutMap.put(String.valueOf(obj[0])+obj[1],String.valueOf(obj[2]));
//			 }
//			 for(String dateStr : StringUtil.equals(isView, month)?this.getNearestMonth(new Date(), 15):this.getNearestDay(new Date(), 15)){
//				 sj = new ShowJsonEntity();
//				  sj.setName(dateStr);
//				  sj.setOrderType("1");
//				  sj.setValue(resutMap.get(dateStr+1) == null ?0:Integer.parseInt(resutMap.get(dateStr+1)));
//				  resutJsonEn.add(sj);
//				  sj = new ShowJsonEntity();
//				  sj.setName(dateStr);
//				  sj.setOrderType("2");
//				  sj.setValue(resutMap.get(dateStr+2) == null ?0:Integer.parseInt(resutMap.get(dateStr+2)));
//				  resutJsonEn.add(sj);
//				  sj = new ShowJsonEntity();
//				  sj.setName(dateStr);
//				  sj.setOrderType("3");
//				  sj.setValue(resutMap.get(dateStr+3) == null ?0:Integer.parseInt(resutMap.get(dateStr+3)));
//				  resutJsonEn.add(sj);
//			 }
//		 }
//		 return resutJsonEn ;
//	}
//	
//	private List<String> getNearestMonth(Date newDate,int  spaceDate){
//		List<String> dateList = new ArrayList<String>();
//		  if(newDate != null){
//			  while(spaceDate > 0){
//				  dateList.add(DateUtil.format(DateUtil.addMonth(newDate, -spaceDate),"yyyyMM"));
//				  spaceDate--;
//			  }
//			  
//		  }
//        return dateList;
//	}
//	
//	private  List<String> getNearestDay(Date newDate,int  spaceDate){
//		List<String> dateList = new ArrayList<String>();
//		  if(newDate != null){
//			  while(spaceDate > 0){
//				  dateList.add(DateUtil.format(DateUtil.addDays(newDate, -spaceDate),"yyyyMMdd"));
//				  spaceDate--;
//			  }
//			  
//		  }
//        return dateList;
//	}


}