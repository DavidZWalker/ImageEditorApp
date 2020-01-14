package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;
import de.hdmstuttgart.bildbearbeiter.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.UnsplashSearcher;
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
        }
        catch (Exception ex) {
            return null;
        }
    }
}
