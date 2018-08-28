package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class ImageDTO {
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }
}
