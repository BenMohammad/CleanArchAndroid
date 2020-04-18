package com.cleanarch.features.wikientry.data.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class WikiEntryApiResponse {

    @SerializedName("batchcomplete")
    @Expose
    public String batchcomplete;

    @SerializedName("query")
    @Expose
    public Query query;

    public class Query {
        @SerializedName("pages")
        @Expose
        public HashMap<String, Pageval> pages;
    }

    public class Pageval {
        @SerializedName("pageid")
        @Expose
        public Integer pageid;

        @SerializedName("ns")
        @Expose
        public Integer ns;

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("extract")
        @Expose
        public String extract;

    }}
