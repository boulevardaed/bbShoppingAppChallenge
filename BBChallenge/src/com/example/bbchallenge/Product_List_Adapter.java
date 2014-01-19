package com.example.bbchallenge;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbchallenge.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Product_List_Adapter extends ArrayAdapter<JSONObject>{
	
	private final Context context;
	private final ArrayList<JSONObject> productList;
	
	public Product_List_Adapter(Context context, List<JSONObject> values) {
		super(context, R.layout.activity_product_list, values);
		this.context = context;
		this.productList = (ArrayList<JSONObject>) values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View contentView = inflater.inflate(R.layout.adapter_product, parent, false);
		try {
			ImageView thumb = (ImageView) contentView.findViewById(R.id.list_thumb_small);
			TextView title = (TextView) contentView.findViewById(R.id.product_text);
			TextView condition = (TextView) contentView.findViewById(R.id.product_condition);
			TextView price = (TextView) contentView.findViewById(R.id.product_price);
			TextView unit = (TextView) contentView.findViewById(R.id.product_unit);
		
			// Set text content
			JSONObject tempJson = productList.get(position);
			title.setText(tempJson.getString("title"));
			condition.setText("Condition: "+tempJson.getString("condition"));
			price.setText(tempJson.getJSONObject("price").getString("value"));
			unit.setText(tempJson.getJSONObject("price").getString("currency")+" ");
			
			// Set thumb
			new ImageDownloader(thumb).execute(tempJson.getJSONObject("image").getJSONObject("thumbs")
					.getString("small"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return contentView;
	}
}
