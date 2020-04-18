package com.cleanarch.features.wikientry.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WikiEntryTable {

    @PrimaryKey
    private int pageId;
    private String title;
    private String extract;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }
}
