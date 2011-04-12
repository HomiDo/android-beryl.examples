package org.beryl.cache;

import java.lang.ref.WeakReference;

import android.app.Activity;

/** Convenience callback for running code on the UI thread.
 * If the {@link android.app.Activity} dies before the callback is executed it'll simply do nothing. */
public abstract class UiAsyncRetrieverCallback<T>
	implements IAsyncRetrieverCallback<T> {
	private final WeakReference<Activity> activityRef;
	
	public UiAsyncRetrieverCallback(Activity activity) {
		activityRef = new WeakReference<Activity>(activity);
	}
	
	public void onError(final Exception e) {
		final Activity activity = activityRef.get();
		if(activity != null) {
			activity.runOnUiThread(new OnErrorHandler(e));
		}
	}

	public void onRetrieved(final T data) {
		final Activity activity = activityRef.get();
		if(activity != null) {
			activity.runOnUiThread(new OnRetrievedHandler(data));
		}
	}

	final class OnErrorHandler implements Runnable {
		final Exception exception;
		OnErrorHandler(Exception e) {
			this.exception = e;
		}
		
		public void run() {
			onUiError(exception);
		}
	}
	
	final class OnRetrievedHandler implements Runnable {
		final T data;
		OnRetrievedHandler(T data) {
			this.data = data;
		}
		
		public void run() {
			onUiRetrieved(data);
		}
	}
	
	public abstract void onUiError(final Exception ex);
	public abstract void onUiRetrieved(final T data);
}
