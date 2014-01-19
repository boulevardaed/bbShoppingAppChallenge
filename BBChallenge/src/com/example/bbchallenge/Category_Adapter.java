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
import android.widget.TextView;

public class Category_Adapter extends ArrayAdapter<JSONObject>{
	
	private final Context context;
	private final ArrayList<JSONObject> categoryList;
	
	public Category_Adapter(Context context, List<JSONObject> values) {
		super(context, R.layout.activity_category, values);
		this.context = context;
		this.categoryList = (ArrayList<JSONObject>) values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View contentView = inflater.inflate(R.layout.adapter_category, parent, false);
		
		try {
			TextView title = (TextView) contentView.findViewById(R.id.category_text);
			title.setText(categoryList.get(position).getString("title"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return contentView;
	}
	
}
