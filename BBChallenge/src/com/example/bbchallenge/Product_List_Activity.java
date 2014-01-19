package com.example.bbchallenge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbchallenge.R;

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
import android.widget.Toast;

public class Product_List_Activity extends Activity {
	
	public static final String BROADCAST_ACTION = "com.song.bbchallenge.ProductRefresh";
	
	private ArrayList<JSONObject> productList = new ArrayList<JSONObject>();
	private Product_List_Adapter adapter;
	
	private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	String action = intent.getAction();
        	if(action.equals(BROADCAST_ACTION)) {
        		adapter.notifyDataSetChanged();
        		unregisterReceiver(br);
        	}
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		
		// Get id from category activity
		Bundle extras = getIntent().getExtras();
		String id = extras.getString("category_id");
		//Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
	
		/*** Need better algorithm ***/
		String url = "http://sportsauthority.api.brandingbrand.com/categories/"+id+"/products.json";
		GetProduct background = new GetProduct(this);
		background.execute(url);
		
		if(!Helper.isConnenected(this)) {
			Toast.makeText(getApplicationContext(), "Check your mobile connection", Toast.LENGTH_LONG).show();
		}
		
		// Set custom listview adapter
		adapter = new Product_List_Adapter(Product_List_Activity.this, productList);
		ListView productListView = (ListView) findViewById(R.id.product_list);
		productListView.setEmptyView(findViewById(R.id.empty_list_view));
		productListView.setAdapter(adapter);
		
		// Set click action for product list
		// Will prompt a custom dialog for product detail
		productListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// Get content from the arraylist
				JSONObject element = productList.get(position);
				Intent intent = new Intent(Product_List_Activity.this, Product_Dialog.class);
				intent.putExtra("product_element", element.toString());
				startActivity(intent);
			}
		});
		
		registerReceiver(br, new IntentFilter(Product_List_Activity.BROADCAST_ACTION));
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product__list_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
	        case android.R.id.home:
	        	super.onBackPressed();
	        	overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	            return true;
	        case R.id.product_list_cart:
	        	Intent intent = new Intent(Product_List_Activity.this, Cart_Activity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}

	private class GetProduct extends AsyncTask<String, Void, ArrayList<JSONObject>> {
		
		private ProgressDialog dialog;
        
		public GetProduct(Context context) {
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Get product list..");
            this.dialog.show();
  		}

		@Override
		protected ArrayList<JSONObject> doInBackground(String... urls) {
			ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
			try {
				temp = Helper.handleJSONValue(Helper.httpGet(urls[0]), "products");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return temp;
		}

		@Override
		protected void onPostExecute(ArrayList<JSONObject> result) {
					
			for(JSONObject json:result) {
				productList.add(json);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
				Intent intent = new Intent(BROADCAST_ACTION);
				sendBroadcast(intent);
			}
			
		}
	}
}
