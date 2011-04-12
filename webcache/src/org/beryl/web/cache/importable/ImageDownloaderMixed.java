package org.beryl.web.cache.importable;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDownloaderMixed {

	public Bitmap downloadImage(String imageUrl) {
		Bitmap result = null;
		try
		{
			InputStream is = getRemoteImageStream(imageUrl);
			result = BitmapFactory.decodeStream(is);
			
		} catch(Exception e) {
			result = null;
		}
		return result;
	}
	
	public byte[] getRemoteImageAsByteArray(String imageUrl) {
		byte[] result = null;
		
		try {
			final DefaultHttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(imageUrl);
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					result = EntityUtils.toByteArray(entity);
				}
			}
		} catch(Exception e) {}
		
		return result;
	}
	
	
	public InputStream getRemoteImageStream(String imageUrl) {
		InputStream result = null;
		try
		{
			URL link = new URL(imageUrl);
			URLConnection con = link.openConnection();
			
			result = con.getInputStream();
			
		} catch(Exception e) {
			result = null;
		}
		return result;
	}
}
