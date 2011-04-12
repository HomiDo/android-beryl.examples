package org.beryl.cache;

import android.graphics.Bitmap;

//http://www.tutorialforandroid.com/2009/10/how-to-insert-image-data-to-sqlite.html
public class WebBitmapRetriever implements IAsyncRetriever<Bitmap> {

	/** Always returns null. There's no instant internet connection. */
	public Bitmap get(String key) {
		return null;
	}

	@Override
	public Bitmap get(String key, IAsyncRetrieverCallback<Bitmap> callback) {
		// TODO Auto-generated method stub
		return null;
	}

}
