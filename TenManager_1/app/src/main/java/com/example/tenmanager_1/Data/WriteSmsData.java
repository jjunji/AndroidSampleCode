package com.example.tenmanager_1.Data;

/**
 * Created by 전지훈 on 2017-11-10.
 */

public class WriteSmsData {
    private long id;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "WriteSmsData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
