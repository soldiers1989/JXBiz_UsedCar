package com.etong.android.util;

import java.io.Serializable;

public class SerializableObject implements Serializable {

	private Object object;

	public Object getObject() {
		return object;

	}

	public void setObject(Object object) {
		this.object = object;
	}
}
