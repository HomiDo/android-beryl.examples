package org.beryl.cache;

public class EmptyRetrieverCallback<T> implements IAsyncRetrieverCallback<T> {
	public void onError(Exception e) {}
	public void onRetrieved(T object) {}
}
