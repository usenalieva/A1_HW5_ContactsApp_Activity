package com.example.contacts_app;

import android.net.Uri;

import java.io.Serializable;

public class TitleModel implements Serializable {
    public String title;
    public String number;
    public String imageView;
    public Uri imageURI;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public void setImageURI(Uri imageURI) {
        this.imageURI = imageURI;
    }
}
