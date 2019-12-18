package utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BitmapDownloader {
    private List<java.net.URL> URL;
    public List<Bitmap> results;

    public void setURL(List<java.net.URL> URL) {
        this.URL = URL;
    }

    public List<Bitmap> downloadBitmaps(){
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
            /*for (int i = 0; i < 5; i++) {

                try {
                    URL url = new URL(urls.get("THUMB"));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    bitmapList.add(bitmap);
                    totalSize++;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            searchAdapter.setBitmapList(bitmapList);*/

            return totalSize;
        }


    }
    //get urls
    //publish progress
}
