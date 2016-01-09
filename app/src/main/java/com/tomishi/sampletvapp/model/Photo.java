package com.tomishi.sampletvapp.model;

public class Photo {
    private int mId = 0;

    public Photo() {
    }

    public Photo(int id) {
        setId(id);
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
