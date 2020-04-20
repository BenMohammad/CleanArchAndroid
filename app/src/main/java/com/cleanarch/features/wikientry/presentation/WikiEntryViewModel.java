package com.cleanarch.features.wikientry.presentation;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanarch.CleanArchApp;
import com.cleanarch.CleanArchDatabase;
import com.cleanarch.R;
import com.cleanarch.features.wikientry.entities.WikiEntry;
import com.cleanarch.features.wikientry.usecases.GetWikiEntryUseCase;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class WikiEntryViewModel extends AndroidViewModel {

    private MutableLiveData<WikiEntry> wikiEntry;
    private final static String TAG = WikiEntryViewModel.class.getSimpleName();

    @Inject
    Lazy<GetWikiEntryUseCase> getWikiEntryUseCase;

    public WikiEntryViewModel(Application application) {
        super(application);
        ((CleanArchApp) application).buildWikiEntryComponent().inject(this);
    }

    LiveData<WikiEntry> getWikiEntry() {
        if(wikiEntry == null) {
            wikiEntry = new MutableLiveData<>();
        }
        return wikiEntry;
    }

    void loadWikiEntry(String title) {

        getWikiEntryUseCase.get().execute(
                new GetWikiEntryUseCase.Input(title, AndroidSchedulers.mainThread()),
                new UseCaseSubscriber());

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getWikiEntryUseCase.get().cancel();
        Log.d(TAG, "onCleared");
    }


    private class UseCaseSubscriber extends DisposableSubscriber<WikiEntry> {
        @Override
        public void onNext(WikiEntry wikiEntry) {
            Log.d(TAG, "onNext Received response");
            WikiEntryViewModel.this.wikiEntry.setValue(wikiEntry);
        }

        @Override
        public void onError(Throwable t) {
            Log.d(TAG, "Received error " + t.toString());
            WikiEntryViewModel.this.wikiEntry.setValue( new WikiEntry(-1, "", getApplication().getString(R.string.no_results_found)));
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete");
        }
    }}
