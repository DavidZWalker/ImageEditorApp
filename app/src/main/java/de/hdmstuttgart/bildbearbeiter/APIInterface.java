package de.hdmstuttgart.bildbearbeiter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("photos/random")
    Call<Photo> getRandomPicture(@Query("client_id") String apiKey);
}
