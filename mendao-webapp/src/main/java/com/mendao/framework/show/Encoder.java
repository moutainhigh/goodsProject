package com.mendao.framework.show;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public abstract class Encoder extends HashMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Encoder() {
		super(1);
	}

	public Object get(Object o) {
		return encode(o);
	}

	/**
	 * Encode the specified parameters.
	 * 
	 * @param o
	 *            The parameter to encode.
	 * 
	 * @return The encoded value.
	 */
	protected abstract String encode(Object o);
}
