package com.jukte.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseParser {

	public static List<String> parseRespnse(JSONObject jObject)
	{
		List<String> urls=new ArrayList<String>();
		try {
			JSONArray jArray=jObject.getJSONArray("data");
			for(int i=0;i<jArray.length();i++)
			{
				JSONObject object=jArray.getJSONObject(i);
				JSONObject imagesObj=object.getJSONObject("images");
				JSONObject urlObject=imagesObj.getJSONObject("thumbnail");
				String url=urlObject.getString("url");
				urls.add(url);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urls;
	}
	
}
