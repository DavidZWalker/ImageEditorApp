package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import de.hdmstuttgart.bildbearbeiter.models.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.models.UnsplashSearcher;
import retrofit2.Call;

public class SearchViewModel extends ViewModel {
    private UnsplashSearcher model;

    public SearchViewModel() {
        super();
        model = new UnsplashSearcher();
    }

    public Call<SearchResponseResult> doSearch(String query) {
        return query.equals("") ? null : model.search(query);
    }

    public Bitmap getBitmapFromSearchResponse(SearchResponseResult.Photo res) {
        try {
            return model.getBitmapFromSearchResponse(res);
        } catch (Exception ex) {
            Log.e("Error", "Failed to get bitmap from search response", ex);
            return null;
        }
    }

    public boolean checkInternetConnection(ConnectivityManager systemService) {
       return model.checkInternetConnection(systemService);
    }
}
