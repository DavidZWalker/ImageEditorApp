package de.hdmstuttgart.bildbearbeiter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Search response result is the "layout" of the response received by the unsplash API.
 */
public class SearchResponseResult {
    @SerializedName("results")
    @Expose
    private List<Photo> results = null;

    /**
     * Gets all photos.
     *
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return results;
    }

    /**
     * The type Photo.
     */
    public class Photo {
        @SerializedName("urls")
        @Expose
        private Urls urls;

        /**
         * Gets urls.
         *
         * @return the urls
         */
        public Urls getUrls() {
            return urls;
        }

    }

    /**
     * The type Urls.
     */
    public class Urls {
        @SerializedName("small")
        @Expose
        private String small;

        /**
         * Gets small resolution.
         *
         * @return the small
         */
        public String getSmall() {
            return small;
        }
    }
}
