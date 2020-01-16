package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Unsplash searcher creates a request to Unsplash API using Retrofit and Gson.
 */
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

    /**
     * Instantiates a new Unsplash call.
     * <p>
     * It creates  a Gson Object for JSON parsing and Retrofit for making the HTTP call.
     */
    public UnsplashSearcher() {
        Log.d("UnsplashSearcher", "Init Gson...");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Log.d("UnsplashSearcher", "Init Retrofit...");
        retrofit = new Retrofit.Builder()
                .baseUrl(UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Log.d("UnsplashSearcher", "Creating retrofit api interface...");
        api = retrofit.create(UnsplashAPI.class);
    }

    /**
     * Starts the call to the unsplash api.
     *
     * @param query the query
     * @return the call
     */
    public Call<SearchResponseResult> search(String query) {
        Log.d("UnsplashSearcher", "Searching Unsplash for '" + query + "'...");
        if (query == null || query.isEmpty()) return null;
        return api.getSearchResults(query,
                UNSPLASH_PAGE,
                UNSPLASH_PER_PAGE,
                UNSPLASH_ACCESS_TOKEN);
    }

    /**
     * Gets bitmap from search response.
     *
     * @param res the resolution of the image
     * @return the bitmap from search response
     * @throws IOException if decoding is unsuccessful
     */
    public Bitmap getBitmapFromSearchResponse(SearchResponseResult.Photo res) throws IOException {
        Log.d("UnsplashSearcher", "Attempting to retrieve bitmap from search result...");
        URL url = new URL(res.getUrls().getSmall());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
    }

    /**
     * Checks if there is an internet connection available, if not displays a Snackbar.
     *
     * @param systemService the system service
     * @return the boolean
     */
    public boolean checkInternetConnection(ConnectivityManager systemService) {
        Log.d("UnsplashSearcher", "Checking for internet connection...");
        boolean internetAvailable = false;
        if (systemService == null) {
            Log.d("UnsplashSearcher", "No internet connection detected! Device is offline.");
            return false;
        }
        Network[] networks = systemService.getAllNetworks();
        if (networks.length > 0) {
            for (Network network : networks) {
                NetworkCapabilities nc = systemService.getNetworkCapabilities(network);
                if (nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    Log.d("UnsplashSearcher", "Internet connection detected! Device is online.");
                    internetAvailable = true;
                }
            }
        }
        return internetAvailable;
    }
}
