package com.sszg.chinesenovelreader;

public class Book {
    private String coverPictureURL, title;

    public String getCoverPictureURL() {
        return coverPictureURL;
    }

    public String getTitle() {
        return title;
    }

    public Book(String coverPictureURL, String title) {

        this.coverPictureURL = coverPictureURL;
        this.title = title;
    }
}
