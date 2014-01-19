package com.example.bbchallenge;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbchallenge.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Product_Dialog extends Activity {
	
	public static final String BROADCAST_ACTION = "com.song.bbchallenge.DetailRefresh";
	private String returnValue;
	
	private BroadcastReceiver br = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BROADCAST_ACTION)) {
				updateUI(returnValue);
				unregisterReceiver(br);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_product);
		
		// Get id from category activity
		Bundle extras = getIntent().getExtras();
		String element = extras.getString("product_element");
		try {
			JSONObject json = new JSONObject(element);
			
			ImageView thumb = (ImageView) findViewById(R.id.detail_thumb_image);
			TextView title = (TextView) findViewById(R.id.detail_title);
			
			title.setText(json.getString("title"));
			
			// Download image from web
			new ImageDownloader(thumb).execute(json.getJSONObject("image").getString("full"));
			
			// Update component content from /products/{id} API
			String id = json.getJSONObject("identifiers").getString("id");
			String url = "http://sportsauthority.api.brandingbrand.com/products/"+id+".json";
			GetProductDetails bgTask = new GetProductDetails();
			bgTask.execute(url);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Toast.makeText(Product_Dialog.this, element, Toast.LENGTH_LONG).show();
		
		registerReceiver(br, new IntentFilter(Product_Dialog.BROADCAST_ACTION));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product__dialog, menu);
		return true;
	}
	
	public void updateUI(String input) {
		TextView last_modified = (TextView) findViewById(R.id.detail_last_modified);
		TextView description = (TextView) findViewById(R.id.detail_description);
		TextView variation = (TextView) findViewById(R.id.detail_variations);
		
		try {
			// parse JSON data
			// decide what to display here
			JSONObject returnJSON = new JSONObject(input);
			JSONArray temp = returnJSON.getJSONObject("description")
					.getJSONArray("bullets");
			if (temp.length()==0) {
				description.setText("No description for this product");
			} else {
				description.setText("Description: "+temp);
			}
			variation.setText(returnJSON.getJSONArray("variations").getJSONObject(0)
					.getJSONObject("availability").getJSONObject("online").getString("value"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		last_modified.setText("Last modified: "+Helper.getModifiedDate(returnValue));
	}
	
	private class GetProductDetails extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String result = "";
			result = Helper.httpGet(urls[0]);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			returnValue = result;
			Intent intent = new Intent(BROADCAST_ACTION);
			sendBroadcast(intent);
		}		
	}
}
