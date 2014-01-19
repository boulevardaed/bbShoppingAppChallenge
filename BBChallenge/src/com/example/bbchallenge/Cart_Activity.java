package com.example.bbchallenge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class Cart_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		// Enable back arrow on Actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart_, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
	        case android.R.id.home:
	        	super.onBackPressed();
	        	overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}

}
