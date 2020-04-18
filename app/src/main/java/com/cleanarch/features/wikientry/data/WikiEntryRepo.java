package com.cleanarch.features.wikientry.data;

import com.cleanarch.features.wikientry.entities.WikiEntry;

import io.reactivex.Flowable;

public interface WikiEntryRepo {

    Flowable<WikiEntry> getWikiEntry(String title);
}
