package com.cleanarch.features.wikientry.usecases;

import android.util.Log;

import com.cleanarch.base.usecases.UseCase;
import com.cleanarch.features.wikientry.data.WikiEntryRepo;
import com.cleanarch.features.wikientry.entities.WikiEntry;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class GetWikiEntryUseCase extends UseCase<GetWikiEntryUseCase.Input, WikiEntry> {

    private static final String TAG = GetWikiEntryUseCase.class.getSimpleName();
    private WikiEntryRepo wikiEntryRepo;

    @Inject
    GetWikiEntryUseCase(WikiEntryRepo wikiEntryRepo) {
        this.wikiEntryRepo = wikiEntryRepo;
    }

    @Override
    public void execute(Input input, DisposableSubscriber<WikiEntry> subscriber) {
        Flowable.just(input.title)
                .flatMap(title -> wikiEntryRepo.getWikiEntry(title))
                .subscribeOn(Schedulers.io())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);

        Log.d(TAG, "called subscribe on WikiEntry");

        disposables.add(subscriber);
    }

    public static class Input {

        private String title;
        private Scheduler observerOnScheduler;

        public Input(String title, Scheduler observerOnScheduler) {
            this.title = title;
            this.observerOnScheduler = observerOnScheduler;
        }
    }
}
