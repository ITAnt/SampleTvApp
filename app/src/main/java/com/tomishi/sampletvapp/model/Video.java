package com.tomishi.sampletvapp.model;

public class Video  {
    private static final String TAG = Video.class.getSimpleName();

    private long mId;
    private String mTitle;
    private String mStudio;

    public Video() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getStudio() {
        return mStudio;
    }

    public void setStudio(String studio) {
        this.mStudio = studio;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + mId +
                ",title='" + mTitle + '\'' +
                '}';
    }
}
