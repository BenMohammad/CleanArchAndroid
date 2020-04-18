package com.cleanarch;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cleanarch.features.wikientry.data.local.WikiEntryDao;
import com.cleanarch.features.wikientry.data.local.WikiEntryTable;

@Database(entities = {WikiEntryTable.class}, version = 1)
public abstract class CleanArchDatabase extends RoomDatabase {

    public abstract WikiEntryDao wikiEntryDao();
}
