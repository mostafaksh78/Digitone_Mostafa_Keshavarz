package com.mostafa.myapplication;

import android.view.View;

public abstract class ItemTouchedListener<T> implements View.OnClickListener {
    public ItemTouchedListener<T>  setT(T t) {
        this.t = t;
        return this;
    }

    private T t;

    public ItemTouchedListener() {

    }

    public abstract void onItemClick(T t);
    @Override
    public void onClick(View v) {
        onItemClick(t);
    }

    public View.OnClickListener getListener(T film) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(film);
            }
        };
    }
}
