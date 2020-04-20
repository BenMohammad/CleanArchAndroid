package com.cleanarch.di;

import com.cleanarch.CleanArchDatabase;
import com.cleanarch.features.wikientry.data.WikiEntryRepo;
import com.cleanarch.features.wikientry.data.WikiEntryRepoImpl;
import com.cleanarch.features.wikientry.data.local.WikiEntryDao;
import com.cleanarch.features.wikientry.data.remote.WikiApiService;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WikiEntryModule {

    @WikiEntryScope
    @Provides
    WikiEntryDao provideWikiEntryDao(CleanArchDatabase db) {
        return db.wikiEntryDao();
    }

    @WikiEntryScope
    @Provides
    WikiApiService provideWikiApiService() {
        return new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WikiApiService.class);
    }

    @WikiEntryScope
    @Provides
    WikiEntryRepo provideWikiEntryRepo(CleanArchDatabase db, WikiApiService wikiApiService) {
        return new WikiEntryRepoImpl(db, wikiApiService);
    }

}
