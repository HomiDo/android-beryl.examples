package org.beryl.cache;

public interface IAsyncRetriever<T> {
	
	/** Attempts to retrieve data only through fast methods.
	 * If the data is not available the default object will be returned.
	 */
	T get(String key);
	
	/** Attempts to retrieve data quickly. If the data is not available quickly the default object is returned.
	 * The actual data will be retrieved on a separate thread and sent back once it is available.
	 * Callbacks are executed on a different thread. If you want to force the callback to run on an {@link android.app.Activity}'s
	 * thread. Then subclass {@link org.beryl.cache.UiAsyncRetrieverCallback} instead.
	 */
	T get(String key, IAsyncRetrieverCallback<T> callback);
}
