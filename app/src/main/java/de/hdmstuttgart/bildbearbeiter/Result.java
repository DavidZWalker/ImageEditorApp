package de.hdmstuttgart.bildbearbeiter;

import java.util.List;


import com.google.gson.annotations.SerializedName;

public class Result {
    public String id;
    public Integer width;
    public Integer height;
    public Urls urls;

    public class Urls {

        public String raw;
        public String full;
        public String regular;
        public String small;
        public String thumb;

    }

}
