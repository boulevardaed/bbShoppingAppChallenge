package com.example.bbchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Helper {
	
	public static ArrayList<JSONObject> handleJSONValue(String input, String key) throws JSONException {
		ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject(input);
		JSONArray categories = json.getJSONArray(key);
		for(int i=0; i<categories.length(); i++) {
			jsonList.add(categories.getJSONObject(i));
		}
		return jsonList;
	}
	
	public static boolean isConnenected(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else{
			return false;
		}
	}
	
	public static String getModifiedDate(String input) {
		String unix_timestamp;
		try {
			unix_timestamp = new JSONObject(input).getString("last_modified");
			String s = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
				.format(new java.util.Date(Long.parseLong(unix_timestamp+"000")));
			return s;
		} catch (JSONException e) {
			e.printStackTrace();
			return "Unknown";
		}
	}
	
	public static String httpGet(String url) {
		
		StringBuffer result = new StringBuffer();
		
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			
			// Value of stale indicates how old the cache content can be
			urlConnection.addRequestProperty("Cache-Control", "max-stale=" + 60 * 60);
			// Enable HttpResponseCache
			urlConnection.setUseCaches(true);
			
			BufferedReader rd = new BufferedReader(
	                new InputStreamReader(urlConnection.getInputStream()));
			
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();	
	}
}
