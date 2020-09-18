package com.rahul.wiprolastfmtask.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {

    public String name;
    public String artist;
    public String url;
    public List<Image> image;
    public String streamable;
    public String mbid;

    public class Image implements Serializable {
        @SerializedName("#text")
        public String text;
        public String size;
    }
}
