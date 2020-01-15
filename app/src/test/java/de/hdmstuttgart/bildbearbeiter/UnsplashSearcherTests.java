package de.hdmstuttgart.bildbearbeiter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

import de.hdmstuttgart.bildbearbeiter.models.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.models.UnsplashSearcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnsplashSearcherTests {

    private static UnsplashSearcher searcher;

    @BeforeClass
    public static void init() {
        searcher = new UnsplashSearcher();
    }

    @Test
    public void searcher_exists() {
        assertNotNull(searcher);
    }

    @Test
    public void searchQuery_works() {
        String query = "Zebra";
        Call<SearchResponseResult> response = searcher.search(query);
        assertNotNull(response);
    }

    @Test
    public void getBitmapFromSearchResult_positive() {
        String query = "Zebra";
        Call<SearchResponseResult> response = searcher.search(query);
        final boolean[] success = {false};
        response.enqueue(new Callback<SearchResponseResult>() {
            @Override
            public void onResponse(Call<SearchResponseResult> call, Response<SearchResponseResult> response) {
                List<SearchResponseResult.Photo> resPhots =  response.body().getPhotos();
                success[0] = true;
                assertFalse(resPhots.isEmpty());
                try {
                    assertNotNull(searcher.getBitmapFromSearchResponse(resPhots.get(0)));
                } catch (IOException e) {
                    assertFalse(true);
                }
            }

            @Override
            public void onFailure(Call<SearchResponseResult> call, Throwable t) {
            }
        });
    }
}
