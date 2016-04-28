package com.mendao.framework.base.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageEntity<T> {

	private static int SPLIT_PAGE_NUM = 5; // 页码按照5个为一组进行拆分

	/**
	 * 每页大小
	 */
	private int pagesize;

	/**
	 * 当前页码
	 */
	private int currentpage;

	/**
	 * 总页数
	 */
	private int totalpage;

	/**
	 * 查询参数
	 */
	private Map<String, Object> params;

	/**
	 * 查询的结果集合
	 */
	private List<T> result;

	/**
	 * 查询条件字符串
	 */
	@SuppressWarnings("unused")
	private String querystr;

	/**
	 * 页码范围
	 */
	@SuppressWarnings("unused")
	private List<Integer> pageRange;
	/**
	 * 起始查询
	 */
	private Integer firstRange;
	/**
	 * 排序语句
	 */
	private String orderBy = " order by o.id desc";

	private String groupBy = "";

	/**
	 * 
	 */
	private String pageUrl = "";

	public PageEntity() {
	};

	public static int getSPLIT_PAGE_NUM() {
		return SPLIT_PAGE_NUM;
	}

	public static void setSPLIT_PAGE_NUM(int sPLIT_PAGE_NUM) {
		SPLIT_PAGE_NUM = sPLIT_PAGE_NUM;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setQuerystr(String querystr) {
		this.querystr = querystr;
	}

	public void setPageRange(List<Integer> pageRange) {
		this.pageRange = pageRange;
	}

	public String getQuerystr() {
		StringBuffer params = new StringBuffer();
		if (this.params != null && this.params.size() > 0) {
			for (String key : this.getParams().keySet()) {
				params.append(ParamsUtil.SPLIT + ParamsUtil.QUERY + key + "=" + this.getParams().get(key));
			}
		}
		return params.toString();
	}

	public List<Integer> getPageRange() {
		List<Integer> pageRange = new ArrayList<Integer>();
		int beginRange = 1;
		int endRange = 5;
		if (this.totalpage <= PageEntity.SPLIT_PAGE_NUM) {
			for (int i = 1; i <= this.totalpage; i++) {
				pageRange.add(i);
			}
		} else {
			int value = this.currentpage / PageEntity.SPLIT_PAGE_NUM;
			if (value > 0) {
				beginRange = (beginRange + (value * PageEntity.SPLIT_PAGE_NUM)) - 1;
				endRange = endRange + (value * PageEntity.SPLIT_PAGE_NUM);
				if (endRange > this.totalpage) {
					endRange = this.totalpage;
				}
			}
			for (int i = beginRange; i <= endRange; i++) {
				pageRange.add(i);
			}
		}
		return pageRange;
	}

	public Integer getFirstRange() {

		return firstRange;
	}

	public void setFirstRange(Integer firstRange) {
		this.firstRange = firstRange;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	
	private String procedure;

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
}
