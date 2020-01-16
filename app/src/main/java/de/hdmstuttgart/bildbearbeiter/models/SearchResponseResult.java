package de.hdmstuttgart.bildbearbeiter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Search response result is the "layout" of the response received by the unsplash API.
 */
public class SearchResponseResult {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
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
     * Gets size of the photo list
     *
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * The type Photo.
     */
    public class Photo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("height")
        @Expose
        private Integer height;
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

        /**
         * Sets urls.
         *
         * @param urls the urls
         */
        public void setUrls(Urls urls) {
            this.urls = urls;
        }
    }

    /**
     * The type Urls.
     */
    public class Urls {

        @SerializedName("raw")
        @Expose
        private String raw;
        @SerializedName("full")
        @Expose
        private String full;
        @SerializedName("regular")
        @Expose
        private String regular;
        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("thumb")
        @Expose
        private String thumb;

        /**
         * Gets full resolution.
         *
         * @return the full
         */
        public String getFull() {
            return full;
        }

        /**
         * Sets full resolution.
         *
         * @param full the full
         */
        public void setFull(String full) {
            this.full = full;
        }

        /**
         * Gets regular resolution.
         *
         * @return the regular
         */
        public String getRegular() {
            return regular;
        }

        /**
         * Sets regular resolution.
         *
         * @param regular the regular
         */
        public void setRegular(String regular) {
            this.regular = regular;
        }

        /**
         * Gets small resolution.
         *
         * @return the small
         */
        public String getSmall() {
            return small;
        }

        /**
         * Sets small resolution.
         *
         * @param small the small
         */
        public void setSmall(String small) {
            this.small = small;
        }

        /**
         * Gets thumb resolution.
         *
         * @return the thumb
         */
        public String getThumb() {
            return thumb;
        }

        /**
         * Sets thumb resolution.
         *
         * @param thumb the thumb
         */
        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

}
