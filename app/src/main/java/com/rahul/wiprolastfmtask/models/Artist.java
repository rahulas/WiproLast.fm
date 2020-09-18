package com.rahul.wiprolastfmtask.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Artist implements Serializable {

    public String name;
    public String mbid;
    public String url;
    public String listeners;
    public String streamable;
    public List<Image> image;


    public class Image implements Serializable {
        @SerializedName("#text")
        public String text;
        public String size;
    }

}
