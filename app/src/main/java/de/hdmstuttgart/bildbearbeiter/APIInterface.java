package de.hdmstuttgart.bildbearbeiter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("photos/random")
    Call<SearchResponseResult> getRandomPicture(@Query("client_id") String apiKey);

    @GET("search/photos")
    Call<SearchResponseResult> getSearchResults(@Query("query") String query, @Query("page") String page, @Query("per_page") String per_page, @Header("Authorization") String auth);
}
