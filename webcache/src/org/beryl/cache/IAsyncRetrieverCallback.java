package org.beryl.cache;

public interface IAsyncRetrieverCallback<T> {
	void onError(Exception e);
	void onRetrieved(T object);
}
