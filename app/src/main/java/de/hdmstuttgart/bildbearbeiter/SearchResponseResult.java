package de.hdmstuttgart.bildbearbeiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    public List<Photo> getResults() {
        return results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

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

        public Urls getUrls() {
            return urls;
        }

        public void setUrls(Urls urls) {
            this.urls = urls;
        }
    }

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

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

}
