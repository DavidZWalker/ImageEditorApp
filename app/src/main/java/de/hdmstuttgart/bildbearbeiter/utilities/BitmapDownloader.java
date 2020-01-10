package de.hdmstuttgart.bildbearbeiter.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BitmapDownloader {
    private List<String> urlStrings;
    private List<URL> urls;
    public List<Bitmap> results;
    private String resolution;

    public BitmapDownloader(List<String> urlString, String resolution) {
        this.urlStrings = urlString;
        for (String s : urlStrings) {
            createURL(s);
        }
        this.resolution = resolution;
    }

    public void createURL(String urlString) {
        try {
            this.urls.add(new URL(urlString));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String getResolution() {
        return resolution;
    }

    public List<Bitmap> downloadBitmaps() {
        BitmapTask bitmapTask = new BitmapTask();
        bitmapTask.execute();
        return results;
    }

    private class BitmapTask extends AsyncTask<String, Integer, Long> {

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

        }

        protected Long doInBackground(String... string) {
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            long totalSize = 0;
            for (URL u : urls) {

                try {
                    //resolution via parameter
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    results.add(bitmap);
                    totalSize++;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }


    }

}
