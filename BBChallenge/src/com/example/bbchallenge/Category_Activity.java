package com.example.bbchallenge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbchallenge.R;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Category_Activity extends Activity {

	public static final String BROADCAST_ACTION = "com.song.bbchallenge.CategoryRefresh";
	
	private ArrayList<JSONObject> categoryList = new ArrayList<JSONObject>();
	private Category_Adapter adapter;
	private String modifiedDate = "";
	private TextView last_modified;
	
	private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	String action = intent.getAction();
        	if(action.equals(BROADCAST_ACTION)) {
        		adapter.notifyDataSetChanged();
        	}
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		/*** Need better algorithm ***/
		String url = "http://sportsauthority.api.brandingbrand.com/.json";
		GetCategory background = new GetCategory(this);
		background.execute(url);
		
		if(!Helper.isConnenected(this)) {
			Toast.makeText(getApplicationContext(), "Check your mobile connection", Toast.LENGTH_LONG).show();
		}
		
		adapter = new Category_Adapter(Category_Activity.this, categoryList);
		ListView categoryListView = (ListView) findViewById(R.id.category);
		categoryListView.setAdapter(adapter);
		
		// Initialize textview
		last_modified = (TextView) findViewById(R.id.category_last_modified);
		
		// Set action when click the list view
		categoryListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {	
				try {
					// Get content from the arraylist
					JSONObject element = categoryList.get(position);
					//Toast.makeText(getApplicationContext(), element.getString("id"), Toast.LENGTH_LONG).show();
					
					// Ready to switch to product activity
					Intent intent = new Intent(Category_Activity.this, Product_List_Activity.class);
					intent.putExtra("category_id", element.getString("id"));
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		// Enable caching of all of application's HTTP requests
		try {
			File httpCacheDir = new File(Category_Activity.this.getCacheDir(), "http");
	        long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
	        HttpResponseCache.install(httpCacheDir, httpCacheSize);
		} catch (IOException e) {
			e.printStackTrace();
	    }
		
		registerReceiver(br, new IntentFilter(Category_Activity.BROADCAST_ACTION));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		HttpResponseCache cache = HttpResponseCache.getInstalled();
	     if(cache != null) {
	          // If cache is present, force buffered operations to the filesystem.
	          cache.flush();
	     }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()== R.id.category_cart) {
			 Intent intent = new Intent(Category_Activity.this, Cart_Activity.class);
			 startActivity(intent);
			 overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetCategory extends AsyncTask<String, Void, ArrayList<JSONObject>> {
		
		private ProgressDialog dialog;
        
		public GetCategory(Context context) {
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Get category list..");
            this.dialog.show();
		}

		@Override
		protected ArrayList<JSONObject> doInBackground(String... urls) {
			ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
			try {
				String jsonResult = Helper.httpGet(urls[0]);
				temp = Helper.handleJSONValue(jsonResult, "categories");
				modifiedDate = Helper.getModifiedDate(jsonResult);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return temp;
		}

		@Override
		protected void onPostExecute(ArrayList<JSONObject> result) {
			for(JSONObject jsonObject:result) {
				categoryList.add(jsonObject);
			}
			last_modified.setText("Last modified: "+modifiedDate);
			if (dialog.isShowing()) {
				dialog.dismiss();
				Intent intent = new Intent(BROADCAST_ACTION);
				sendBroadcast(intent);
			}
		}
	}
}
