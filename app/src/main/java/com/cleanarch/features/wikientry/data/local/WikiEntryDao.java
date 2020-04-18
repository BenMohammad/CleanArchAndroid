package com.cleanarch.features.wikientry.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface WikiEntryDao {

    @Query("SELECT * FROM WikiEntryTable")
    Flowable<List<WikiEntryTable>> getAll();

    @Query("SELECT * FROM WikiEntryTable WHERE title = :wikiTitle COLLATE NOCASE")
    Flowable<List<WikiEntryTable>> getByTitle(String wikiTitle);

    @Query("SELECT * FROM WikiEntryTable WHERE pageid IN (:wikiPageIds)")
    Flowable<List<WikiEntryTable>> loadAllByIds(int[] wikiPageIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WikiEntryTable wikiEntry);

    @Delete
    void delete(WikiEntryTable wikiEntry);
}
