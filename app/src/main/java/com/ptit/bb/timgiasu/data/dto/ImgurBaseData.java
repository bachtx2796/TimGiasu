package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class ImgurBaseData {
    @SerializedName("data")
    private ImageDTO img;

    public ImageDTO getImg() {
        return img;
    }
}
