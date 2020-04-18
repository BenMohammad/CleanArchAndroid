package com.cleanarch.features.wikientry.data;

import android.util.Log;

import com.cleanarch.CleanArchDatabase;
import com.cleanarch.features.wikientry.data.local.WikiEntryDao;
import com.cleanarch.features.wikientry.data.local.WikiEntryDao_Impl;
import com.cleanarch.features.wikientry.data.local.WikiEntryTable;
import com.cleanarch.features.wikientry.data.remote.WikiApiService;
import com.cleanarch.features.wikientry.data.remote.WikiEntryApiResponse;
import com.cleanarch.features.wikientry.entities.WikiEntry;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class WikiEntryRepoImpl implements WikiEntryRepo {

    private static final String TAG = WikiEntryDao_Impl.class.getSimpleName();
    private CleanArchDatabase cleanArchDatabase;
    private WikiApiService wikiApiService;

    @Inject
    public WikiEntryRepoImpl(CleanArchDatabase cleanArchDatabase, WikiApiService wikiApiService) {
        this.cleanArchDatabase = cleanArchDatabase;
        this.wikiApiService = wikiApiService;
    }

    @Override
    public Flowable<WikiEntry> getWikiEntry(String title) {
        Flowable<WikiEntry> local = fetchFromLocal(title);
        Flowable<WikiEntry> remote = fetchFromRemote(title);

        return Flowable.merge(local, remote).firstElement().toFlowable();
    }

    private Flowable<WikiEntry> fetchFromLocal(String title) {
        Flowable<List<WikiEntryTable>> entries = cleanArchDatabase.wikiEntryDao().getByTitle(title);
         return entries.flatMap(wikiEntryTables -> {

             WikiEntryTable firstEntry = wikiEntryTables.get(0);
             Log.d(TAG, "Found and sending data from local");

             return Flowable.just(new WikiEntry(firstEntry.getPageId(),
                     firstEntry.getTitle(), firstEntry.getExtract()));
         });


    }

    private Flowable<WikiEntry> fetchFromRemote(String title) {
        Log.d(TAG, "fetch from Remote");

        Flowable<WikiEntryApiResponse> getRequest = wikiApiService.getWikiEntry(title);

        return getRequest.flatMap(wikiEntryApiResponse -> {
            Log.d(TAG, "received response from remote");

            Iterator<WikiEntryApiResponse.Pageval> pageValidator = wikiEntryApiResponse.query.pages.values().iterator();
            WikiEntryApiResponse.Pageval pageVal = pageValidator.next();
            if(invalidResult(pageVal)) {
                Log.d(TAG, "Sending error");
                return Flowable.error(new NoResultFound());
            } else {

                WikiEntry wikiEntry = new WikiEntry(pageVal.pageid, pageVal.title, pageVal.extract);
                cleanArchDatabase.beginTransaction();
                try {
                    WikiEntryTable newEntry = new WikiEntryTable();
                    newEntry.setPageId(wikiEntry.getPageId());
                    newEntry.setTitle(wikiEntry.getTitle());
                    newEntry.setExtract(wikiEntry.getExtract());

                    WikiEntryDao entryDao = cleanArchDatabase.wikiEntryDao();
                    entryDao.insert(newEntry);
                    cleanArchDatabase.setTransactionSuccessful();
                } finally {
                    cleanArchDatabase.endTransaction();
                }
                Log.d(TAG, "added new entry into db");

                Log.d(TAG, "Sending entry from remote");
                return Flowable.just(wikiEntry);
            }
        });
    }

    private boolean invalidResult(WikiEntryApiResponse.Pageval pageVal) {
        return pageVal.pageid == null || pageVal.pageid <= 0 || pageVal.title == null ||
                pageVal.title.isEmpty() || pageVal.extract == null|| pageVal.extract.isEmpty();
    }

}
