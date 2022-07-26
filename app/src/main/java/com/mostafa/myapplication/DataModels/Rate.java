package com.mostafa.myapplication.DataModels;

public class Rate {
    public static final String VALUE_KEY = "Value";
    public static final String SOURCE_KEY = "Source";

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String source;
    private String value;

    public Rate(String rate) {
        String[] split = rate.split("@@");
        if (split.length == 2) {
            source = split[0];
            value = split[1];
        }else if (split.length > 2){
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                builder.append(split[i]);
            }
            source = builder.toString();
            value = split[split.length -1];
        }else {
            source = rate;
            value = "0";
        }
    }

    public Rate(String source, String value) {
        this.source =source;
        this.value = value;

    }

    @Override public String toString() {

        return source + "@@" + value;
    }
}
