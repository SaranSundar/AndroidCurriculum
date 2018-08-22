package com.sszg.youtube.RecyclerView;

public class Video {

    private String author_title, video_title, author_img_url, video_url, video_img_url;

    public Video(String video_img_url, String video_url, String author_img_url, String author_title, String video_title) {

        this.author_title = author_title;
        this.video_title = video_title;
        this.author_img_url = author_img_url;
        this.video_url = video_url;
        this.video_img_url = video_img_url;
    }

    public String getAuthor_title() {
        return author_title;
    }

    public void setAuthor_title(String author_title) {
        this.author_title = author_title;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getAuthor_img_url() {
        return author_img_url;
    }

    public void setAuthor_img_url(String author_img_url) {
        this.author_img_url = author_img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_img_url() {
        return video_img_url;
    }

    public void setVideo_img_url(String video_img_url) {
        this.video_img_url = video_img_url;
    }

}