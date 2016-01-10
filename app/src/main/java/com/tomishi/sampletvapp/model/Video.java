package com.tomishi.sampletvapp.model;

public class Video  {
    private static final String TAG = Video.class.getSimpleName();

    private long mId;
    private String mTitle;
    private String mStudio;
    private String mDescription;
    private int mResourceId;

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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getResourceId () {
        return mResourceId;
    }

    public void setResourceId(int id) {
        mResourceId = id;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + mId +
                "resId=" + mResourceId +
                ",title='" + mTitle + '\'' +
                ",description='" + mDescription + '\'' +
                '}';
    }
}
