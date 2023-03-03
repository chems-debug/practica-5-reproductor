package com.example.reproductordevideoscrespo;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class VideoItem {
    private String title;
    private String path;
    private Bitmap thumbnail;

    public VideoItem(String title, String path) {
        this.title = title;
        this.path = path;
        this.thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path.split("/")[4];
    }

    public String getFullPath() {
        return path;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
}