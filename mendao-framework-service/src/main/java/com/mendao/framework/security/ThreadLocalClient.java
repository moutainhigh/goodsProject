package com.mendao.framework.security;


public class ThreadLocalClient {
	private static final ThreadLocal<ClientInfoBean> CONTAINER = new ThreadLocal<ClientInfoBean>(){
		protected synchronized ClientInfoBean initialValue() {
			      return new ClientInfoBean();
		}
	};
	 /**
     * Put the form bean to ThreadLocal.
     */
    public static void put(ClientInfoBean object) {
        CONTAINER.set(object);
    }

    /**
     * Get the current thread's form bean.
     */
    public static ClientInfoBean get() {
        return CONTAINER.get();
    }

    public static void remove() {
    	CONTAINER.remove();
    }
}
