package com.cleanarch.features.wikientry.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import com.cleanarch.CleanArchApp;
import com.cleanarch.R;
import com.cleanarch.features.wikientry.usecases.GetWikiEntryUseCase;

public class WikiEntryActivity extends AppCompatActivity {

    private static final String TAG = WikiEntryActivity.class.getSimpleName();
    private ContentLoadingProgressBar progressBar;
    private WikiEntryViewModel wikiEntryViewModel;
    private EditText title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CleanArchApp)getApplication()).buildWikiEntryComponent();

        setContentView(R.layout.activity_main);
        title = findViewById(R.id.entryTitle);
        TextView extract = findViewById(R.id.entryDetails);
        Button submitBtn = findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(submitBtnOnClickListener);

        progressBar = findViewById(R.id.progressBar);
        wikiEntryViewModel = ViewModelProviders.of(this).get(WikiEntryViewModel.class);
        wikiEntryViewModel.getWikiEntry().observe(this, wikiEntry -> {
            Log.d(TAG, "received update from Activity");
            extract.setText(wikiEntry.getExtract());
            progressBar.hide();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((CleanArchApp)getApplication()).releaseWikiEntryComponent();
    }

    private View.OnClickListener submitBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar.show();
            InputMethodManager imm = (InputMethodManager) WikiEntryActivity.this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            wikiEntryViewModel.loadWikiEntry(title.getText().toString());
        }
    };
}
