package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashSearcher {
    private Retrofit retrofit;
    private UnsplashAPI api;

    private static final String UNSPLASH_ACCESS_KEY = "7823a3e208b0868365e231a019b604cf2df2e3c70e1e084d8758b6696164208a";
    private static final String UNSPLASH_SECRET_KEY = "44d14fd815a6fc7d501f87e95a98ae5b8ce5276f6f36cd4b4568f23703e1bb45";
    private static final String UNSPLASH_BASE_URL = "https://api.unsplash.com/";

    private static final String UNSPLASH_ACCESS_TOKEN = "Bearer 4d69c93af58f8f56799f704d95f66f67c3172af4ab0ee0479ae6e58ac95171fe";
    private static final String UNSPLASH_REFRESH_TOKEN = "f126eb8b0708fc83f9f392aef1ed070715791141dddf567530ab191940e1e789";
    private static final String UNSPLASH_PAGE = "1";
    private static final String UNSPLASH_PER_PAGE = "30";

    public UnsplashSearcher() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(UnsplashAPI.class);
    }

    public Call<SearchResponseResult> search(String query) {
        return api.getSearchResults(query,
                UNSPLASH_PAGE,
                UNSPLASH_PER_PAGE,
                UNSPLASH_ACCESS_TOKEN);
    }

    public Bitmap getBitmapFromSearchResponse(SearchResponseResult.Photo res) throws IOException {
        URL url = new URL(res.getUrls().getSmall());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
    }

    public boolean checkInternetConnection(ConnectivityManager systemService) {
        boolean internetAvailable = false;
        if(systemService == null){
            return false;
        }
        Network[] networks = systemService.getAllNetworks();
        if(networks.length>0){
            for(Network network :networks){
                NetworkCapabilities nc = systemService.getNetworkCapabilities(network);
                if(nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    internetAvailable = true;
                }
            }
        }
        return internetAvailable;
    }
}
