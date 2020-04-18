package com.cleanarch.features.wikientry.entities;

public class WikiEntry {

    private int pageId;
    private String title;
    private String extract;

    public WikiEntry(int pageId, String title, String extract) {
        this.pageId = pageId;
        this.title = title;
        this.extract = extract;
    }

    public int getPageId() {
        return pageId;
    }

    public String getTitle() {
        return title;
    }

    public String getExtract() {
        return extract;
    }
}
