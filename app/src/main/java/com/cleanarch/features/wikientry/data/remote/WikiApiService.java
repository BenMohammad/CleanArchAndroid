package com.cleanarch.features.wikientry.data.remote;

import com.cleanarch.features.wikientry.data.local.WikiEntryTable;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiApiService {

    @GET("/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=")
    Flowable<WikiEntryApiResponse> getWikiEntry(@Query("titles") String title);
}
