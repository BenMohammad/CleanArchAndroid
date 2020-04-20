package com.cleanarch.di;


import com.cleanarch.AppComponent;
import com.cleanarch.features.wikientry.presentation.WikiEntryViewModel;

import dagger.Component;

@WikiEntryScope
@Component(modules = WikiEntryModule.class, dependencies = AppComponent.class)
public interface WikiEntryComponent {
    void inject(WikiEntryViewModel target);
}
