package de.hdmstuttgart.bildbearbeiter;

import java.util.List;


import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    public String id;

    @SerializedName("width")
    public Integer width;

    @SerializedName("height")
    public Integer height;
    @SerializedName("urls")

    public Urls urls;
    public class Urls {

        @SerializedName("raw")
        public String raw;

        @SerializedName("full")
        public String full;

        @SerializedName("regular")
        public String regular;

        @SerializedName("small")
        public String small;

        @SerializedName("thumb")
        public String thumb;

    }
}
