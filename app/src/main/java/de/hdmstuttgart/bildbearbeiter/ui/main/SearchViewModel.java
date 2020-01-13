package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdmstuttgart.bildbearbeiter.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.UnsplashAPI;
import de.hdmstuttgart.bildbearbeiter.utilities.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewModel extends ViewModel {
    private Retrofit retrofit;
    private UnsplashAPI api;

    public SearchViewModel() {
        super();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(UnsplashAPI.class);
    }

    public Call<SearchResponseResult> doSearch(String query) {
        return query.equals("") ? null :
                api.getSearchResults(query,
                        Constants.UNSPLASH_PAGE,
                        Constants.UNSPLASH_PER_PAGE,
                        Constants.UNSPLASH_ACCESS_TOKEN);
    }

    public Bitmap getBitmapFromSearchResponse(SearchResponseResult.Photo res) {
        try {
            URL url = new URL(res.getUrls().getSmall());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        }
        catch (Exception ex) {
            return null;
        }
    }

}
