package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import de.hdmstuttgart.bildbearbeiter.models.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.models.UnsplashSearcher;
import retrofit2.Call;

/**
 * The type Search view model.
 */
public class SearchViewModel extends ViewModel {
    private UnsplashSearcher model;

    /**
     * Instantiates and creates a mew {@link UnsplashSearcher} which searches the unsplash API
     */
    public SearchViewModel() {
        super();
        model = new UnsplashSearcher();
    }

    /**
     * Checks for an empty query and returns null, if this is not the case, it starts the search.
     *
     * @param query the query
     * @return Call with {@link SearchResponseResult}
     */
    public Call<SearchResponseResult> doSearch(String query) {
        return query.equals("") ? null : model.search(query);
    }

    /**
     * Gets bitmaps from search response.
     *
     * @param res the res
     * @return the bitmap from search response
     */
    public Bitmap getBitmapFromSearchResponse(SearchResponseResult.Photo res) {
        try {
            return model.getBitmapFromSearchResponse(res);
        } catch (Exception ex) {
            Log.e("Error", "Failed to get bitmap from search response", ex);
            return null;
        }
    }

    /**
     * Checks internet connection boolean.
     *
     * @param systemService the system service
     * @return true if there is an internet connection, false if there is none
     */
    public boolean checkInternetConnection(ConnectivityManager systemService) {
       return model.checkInternetConnection(systemService);
    }
}
