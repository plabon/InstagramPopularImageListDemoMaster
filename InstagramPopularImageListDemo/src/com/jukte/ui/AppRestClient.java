package com.jukte.ui;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AppRestClient {
	 private static final String BASE_URL = "https://api.instagram.com/v1/media/popular";

	  private static AsyncHttpClient client = new AsyncHttpClient();
	  
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  
		  client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
	  
	  public static void post(Context con,String url, HttpEntity entity,String contentType, AsyncHttpResponseHandler responseHandler) {
		  client.post(con,getAbsoluteUrl(url), entity,contentType, responseHandler);
	  }
}
