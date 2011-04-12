package org.beryl.cache;

import android.content.ComponentCallbacks;

public interface ICache<T> extends ComponentCallbacks {
	void put(String key, T data);
	T get(String key);
	void compact();
}
