package com.example.bbchallenge;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bmp = null;
        try {
        	HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        	
        	// Value of stale indicates how old the cache content can be
        	urlConnection.addRequestProperty("Cache-Control", "max-stale=" + 60 * 60);
        	// Enable HttpResponseCache
        	urlConnection.setUseCaches(true);
        	
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
