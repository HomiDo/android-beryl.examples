package org.beryl.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.content.res.Configuration;

/**
 * Handles in memory caching of an object. This object is thread safe.
 */
public class MemoryCache<T> implements ICache<T> {

	private static final int HARD_CACHE_CAPACITY = 40;
    
	private final HashMap<String, T> hardCache =
        new LinkedHashMap<String, T>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
		private static final long serialVersionUID = -695136752926135717L;

		@Override
        protected boolean removeEldestEntry(LinkedHashMap.Entry<String, T> eldest) {
            if (size() > HARD_CACHE_CAPACITY) {
                // Entries push-out of hard reference cache are transferred to soft reference cache
            	softCache.putIfAbsent(eldest.getKey(), new SoftReference<T>(eldest.getValue()));
                return true;
            } else
                return false;
        }
    };
    
	private final ConcurrentHashMap<String, SoftReference<T>> softCache = new ConcurrentHashMap<String, SoftReference<T>>();

	public MemoryCache() {

	}
	
	@Override
	public void compact() {
		softCache.clear();
	}

	public T get(String key) {
		
        synchronized (hardCache) {
            final T data = hardCache.get(key);
            if (data != null) {
            	hardCache.remove(key);
            	hardCache.put(key, data);
                return data;
            }
        }

        // Then try the soft reference cache
        final SoftReference<T> dataRef = softCache.get(key);
        if (dataRef != null) {
            final T data = dataRef.get();
            if (data != null) {
            	softCache.remove(key);
            	put(key, data);
                return data;
            } else {
            	// Dead object.
                softCache.remove(key);
            }
        }
        
        return null;
	}

	public void put(String key, T data) {
		synchronized (hardCache) {
            final T tryData = hardCache.get(key);
            if (tryData != null) {
            	hardCache.remove(key);
            }
            hardCache.put(key, data);
        }
	}

	public void onLowMemory() {
		softCache.clear();
		hardCache.clear();
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	}
}
