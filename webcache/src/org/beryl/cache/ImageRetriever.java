package org.beryl.cache;

import android.graphics.Bitmap;

/** Abstracts the retrieval of images.
 * Loaded images are stored in a local cache. If they are not available they'll be retrieved from the file system or
 * the internet.
 */
public class ImageRetriever implements IAsyncRetriever<Bitmap> {

	public final EmptyRetrieverCallback<Bitmap> EmptyCallback = new EmptyRetrieverCallback<Bitmap>();
	private Bitmap defaultBitmap;
	
	private final MemoryCache<Bitmap> bitmapCache = new MemoryCache<Bitmap>();
	private final WebBitmapRetriever webRetriever = new WebBitmapRetriever();
	public void setDefault(Bitmap bitmap) {
		defaultBitmap = bitmap;
	}
	
	public Bitmap get(String key) {
		final Bitmap tryGetCacheBitmap = bitmapCache.get(key);
		
		if(tryGetCacheBitmap != null)
			return tryGetCacheBitmap;
		
		return defaultBitmap;
	}

	public Bitmap get(String key, IAsyncRetrieverCallback<Bitmap> callback) {
		final Bitmap result = get(key);
		
		if(result == defaultBitmap) {
			queueSlowRequest(key, callback);
		}
		
		return result;
	}
	
	private void queueSlowRequest(String key, IAsyncRetrieverCallback<Bitmap> callback) {
		// TODO Auto-generated method stub
		
	}
}
