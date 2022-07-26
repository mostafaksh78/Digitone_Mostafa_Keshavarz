package com.mostafa.myapplication.DataModels;

public class Property {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    private String extend;

    public Property(String title, String extend) {
        this.title = title;
        this.extend = extend;
    }
}
