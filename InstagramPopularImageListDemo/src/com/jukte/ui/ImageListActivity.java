package com.jukte.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

public class ImageListActivity extends Activity {

	
	private static final String clientId="c3305b6a7ecf4c399bb1fde0775741fb";
	private ListView imageList;
	private ImageListAdapter adapter;
	private List<String> urls;
	private Context con;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);
		init();
		setUpList();
		loadData();
	}
	
	public void showProgressDialog(final String message) {

		if (progressDialog == null) {
			progressDialog = new ProgressDialog(con);
			progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			progressDialog.setCancelable(true);
		}
		progressDialog.setMessage(message);
		progressDialog.show();
		

	}
	private void dismissProgressDialog() {
		progressDialog.dismiss();
	}
	
	JsonHttpResponseHandler myHandler=new JsonHttpResponseHandler()
	{
		@Override
		public void onFailure(Throwable arg0, JSONObject json) {
			// TODO Auto-generated method stub
			dismissProgressDialog();
		}

		@Override
		public void onStart() {
			showProgressDialog("Loading...");
		}

		@Override
        public void onSuccess(JSONObject jsonResponse) {
			urls.clear();
			urls.addAll(ResponseParser.parseRespnse(jsonResponse));
			adapter.notifyDataSetChanged();
			dismissProgressDialog();
		}

		
	};
	
	
	private void loadData() {
		
			 String url="?client_id="+clientId;
			 AppRestClient.get(url, null, myHandler);
	}

	private void setUpList() {
		// TODO Auto-generated method stub
		adapter=new ImageListAdapter(ImageListActivity.this, 0, urls);
		imageList.setAdapter(adapter);
	}

	private void init() {
		// TODO Auto-generated method stub
		con=ImageListActivity.this;
		imageList=(ListView)findViewById(R.id.listview);
		urls=new ArrayList<String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_image_list, menu);
		return true;
	}

}
