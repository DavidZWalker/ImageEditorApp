package de.hdmstuttgart.bildbearbeiter.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * The interface Unsplash api it contains all necessary attributes to make a valid request to unsplash API.
 */
public interface UnsplashAPI {
    /**
     * Gets search results.
     *
     * @param query    the query to send to the Unsplash API
     * @param page     the page result number
     * @param per_page images per page
     * @param auth     the authentication token
     * @return the search results
     */
    @GET("search/photos")
    Call<SearchResponseResult> getSearchResults(@Query("query") String query, @Query("page") String page, @Query("per_page") String per_page, @Header("Authorization") String auth);
}
