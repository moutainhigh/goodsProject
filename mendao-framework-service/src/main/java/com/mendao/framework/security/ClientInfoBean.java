package com.mendao.framework.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.mendao.framework.entity.FwAccount;



public class ClientInfoBean implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 8810206692236045600L;

	private String requestURL;

	private String actionURL;

	private String actionMethod;

	private String contextPath;

	private String serverHost;

	private int serverPort;

	private Calendar accessCalendar;

	private String actionId;

	private String hostIp;

	private int hostPort;

	private Long menuId;


	private boolean isLogged;

	private String requestMethod;

	public Map<String,Object> envParamMap = new HashMap<String,Object>();

	Locale locale;

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}


//	private List<FwAction> permitAction;
//
//	public List<FwAction> getPermitAction() {
//		return permitAction;
//	}
//
//	public void setPermitAction(List<FwAction> permitAction) {
//		this.permitAction = permitAction;
//	}

	private FwAccount operator;

	private String actualActionPath;

	public String getActualActionPath() {
		return actualActionPath;
	}

	public void setActualActionPath(String actualActionPath) {
		this.actualActionPath = actualActionPath;
	}

	public FwAccount getOperator() {
		return operator;
	}

	public void setOperator(FwAccount operator) {
		this.operator = operator;
	}

//	private FwAction srcAction;
//
//
//	public FwAction getSrcAction() {
//		return srcAction;
//	}
//
//	public void setSrcAction(FwAction srcAction) {
//		this.srcAction = srcAction;
//	}

	public int getHostPort() {
		return hostPort;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	public Calendar getAccessCalendar() {
		return accessCalendar;
	}

	public void setAccessCalendar(Calendar accessCalendar) {
		this.accessCalendar = accessCalendar;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionName) {
		this.actionURL = actionName;
	}


	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String toString() {

		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");*/
		Date time = Calendar.getInstance().getTime();
		if(time==null){
			time = new Date();
		}
		return "actionURL=" + this.actionURL + "\n" + "accessCalendar="
				+ time + "\n"
				+ "actionId=" + this.actionId + "\n"
				+ "hostIp=" + this.hostIp;
	}

	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}


	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
