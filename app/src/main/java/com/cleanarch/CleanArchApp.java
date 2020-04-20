package com.cleanarch;

import android.app.Application;

import com.cleanarch.di.DaggerWikiEntryComponent;
import com.cleanarch.di.WikiEntryComponent;
import com.cleanarch.di.WikiEntryModule;

public class CleanArchApp extends Application {

    private AppComponent appComponent;
    private WikiEntryComponent wikiEntryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public WikiEntryComponent buildWikiEntryComponent() {
        wikiEntryComponent = DaggerWikiEntryComponent.builder()
                .appComponent(appComponent)
                .wikiEntryModule(new WikiEntryModule())
                .build();

        return wikiEntryComponent;
    }

    public void releaseWikiEntryComponent() {
        wikiEntryComponent = null;
    }

    public WikiEntryComponent getWikiEntryComponent() {
        return wikiEntryComponent;
    }
}
