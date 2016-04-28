package com.mendao.business.dto;

import java.util.ArrayList;
import java.util.List;

public class BtTreeNode {
	private long itemid;
	private long prtid;
	private String text;
	private String[] tags;
	private String custAttr;

	public String getCustAttr() {
		return custAttr;
	}

	public void setCustAttr(String custAttr) {
		this.custAttr = custAttr;
	}

	private List<BtTreeNode> nodes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public List<BtTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<BtTreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public long getPrtid() {
		return prtid;
	}

	public void setPrtid(long prtid) {
		this.prtid = prtid;
	}

	public BtTreeNode(){
		//this.nodes = new ArrayList<BtTreeNode>();
	}
}
