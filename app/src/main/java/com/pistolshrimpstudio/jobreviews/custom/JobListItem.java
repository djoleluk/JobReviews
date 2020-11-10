package com.pistolshrimpstudio.jobreviews.custom;

import android.graphics.Bitmap;

public class JobListItem {
    private String mTitle;
    private Bitmap mImageBitmap;

    public JobListItem(String title, Bitmap imageBitmap) {
        mTitle = title;
        mImageBitmap = imageBitmap;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }
}
