package com.mendao.cms.enums;

/**
 * 远程调用接口枚举类
 * 
 * @author HarrisonHan
 *
 */
public enum RemoteServiceEnum {
    /**
     * 砍价活动查询
     */
    ACTIVITY_QUERY("ACTIVITY_QUERY", "/api/queryActivity"),

    /**
     * 砍价活动单个加载.
     */
    ACTIVITY_LOAD("ACTIVITY_LOAD", "/api/getActivity"),
    
    /**
     * 新增砍价活动.
     */
    ACTIVITY_SAVE("ACTIVITY_SAVE","/api/saveActivity"),
    /**
     * 修改砍价活动.
     */
    ACTIVITY_EDIT("ACTIVITY_EDIT","/api/editActivity"),
    /**
     * 修改砍价活动.
     */
    ACTIVITY_DELETE("ACTIVITY_DELETE","/api/deleteActivity"),
    /**
     * 砍价活动发起信息查询.
     */
    ACTIVITYOWNER_QUERY("ACTIVITYOWNER_QUERY","/api/queryOwner"),
    /**
     * 砍价活动记录查询.
     */
    ACTIVITYRECORD_QUERY("ACTIVITYRECORD_QUERY","/api/queryRecord");

    private String key;

    private String value;

    RemoteServiceEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
