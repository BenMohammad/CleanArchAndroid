package com.cleanarch;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private Application application;

    AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApp() {
        return application;
    }

    @Singleton
    @Provides
    CleanArchDatabase provideDb() {
        return Room.databaseBuilder(application, CleanArchDatabase.class, "cleanarch.db").build();

    }}
