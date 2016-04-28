package com.mendao.business.dto;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 907692245506133707L;
	private Long parentId;
    private Long id;
    protected String nodeName;
    protected Object obj;
    protected TreeNode parentNode;
    protected List<TreeNode> childList;
    
    private String sortSeq;
    public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}

	protected int level = 0;
    public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public TreeNode() {
        initChildList();
    }
 
    public TreeNode(TreeNode parentNode) {
        this.getParentNode();
        initChildList();
    }
 
    public boolean isLeaf() {
        if (childList == null) {
            return true;
        } else {
            if (childList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }
 
    /* 插入一个child节点到当前节点中 */
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        childList.add(treeNode);
    }
 
    public void initChildList() {
        if (childList == null)
            childList = new ArrayList<TreeNode>();
    }
 
    public boolean isValidTree() {
        return true;
    }
 
    /* 返回当前节点的父辈节点集合 */
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.getParentNode();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }
 
    /* 返回当前节点的晚辈集合 */
    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }
 
    /* 返回当前节点的孩子集合 */
    public List<TreeNode> getChildList() {
        return childList;
    }
 
    /* 删除节点和它下面的晚辈 */
    public void deleteNode() {
        TreeNode parentNode = this.getParentNode();
        Long id = this.getId();
 
        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }
    }
 
    /* 删除当前节点的某个子节点 */
    public void deleteChildNode(Long childId) {
        List<TreeNode> childList = this.getChildList();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (child.getId() == childId) {
                childList.remove(i);
                return;
            }
        }
    }
 
    /* 动态的插入一个新的节点到当前树中 */
    public boolean insertJuniorNode(TreeNode treeNode) {
        Long juniorParentId = treeNode.getParentId();
        if (this.parentId == juniorParentId) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getChildList();
            int childNumber = childList.size();
            boolean insertFlag;
 
            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }
 
    /* 找到一颗树中某个节点 */
    public TreeNode findTreeNodeById(Long id) {
        if (this.id == id)
            return this;
        if (childList.isEmpty() || childList == null) {
            return null;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = childList.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }
 
    /* 遍历一棵树，层次遍历 */
    public void traverse() {
        if (id < 0)
            return;
        print(this.id);
        if (childList == null || childList.isEmpty())
            return;
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            child.traverse();
        }
    }
 
    public void print(String content) {
        System.out.println(content);
    }
 
    public void print(Long content) {
        System.out.println(String.valueOf(content));
    }
 
    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
 
    public Long getParentId() {
        return parentId;
    }
 
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public TreeNode getParentNode() {
        return parentNode;
    }
 
    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }
 
    public String getNodeName() {
        return nodeName;
    }
 
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
 
    public Object getObj() {
        return obj;
    }
 
    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    public static void main(String[] args){
    	TreeNode node = new TreeNode();
    	node.setId(0L);
    	node.setNodeName("root");
    	
    	TreeNode l1_1 = new TreeNode();
    	l1_1.setId(1L);
    	l1_1.setNodeName("Level_1_1");
    	l1_1.setParentId(node.getId());
    	l1_1.setParentNode(node);
    	node.addChildNode(l1_1);
    	
    	TreeNode l1_2 = new TreeNode();
    	l1_2.setId(2L);
    	l1_2.setNodeName("Level_1_2");
    	l1_2.setParentId(node.getId());
    	l1_2.setParentNode(node);
    	node.addChildNode(l1_2);
    	
    	TreeNode l1_1_1 = new TreeNode();
    	l1_1_1.setId(3L);
    	l1_1_1.setNodeName("Level_1_1_1");
    	l1_1_1.setParentId(l1_1.getId());
    	l1_1_1.setParentNode(l1_1);
    	l1_1.addChildNode(l1_1_1);
    	
    	TreeNode l1_1_2 = new TreeNode();
    	l1_1_2.setId(3L);
    	l1_1_2.setNodeName("Level_1_1_2");
    	l1_1_2.setParentId(l1_1.getId());
    	l1_1_2.setParentNode(l1_1);
    	l1_1.addChildNode(l1_1_2);
    	
    	StringBuffer bf = new StringBuffer();
    	//createTree(node, bf, "");
    	System.out.println(bf.toString());
    }
}
