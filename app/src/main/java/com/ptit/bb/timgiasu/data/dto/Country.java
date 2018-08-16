package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("LocalizedName")
    private String localizedName;
    @SerializedName("ID")
    private String iD;
    @SerializedName("EnglishName")
    private String englishName;

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getID() {
        return iD;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    @Override
    public String toString() {
        return
                "Country{" +
                        "LocalizedName = '" + localizedName + '\'' +
                        ",ID = '" + iD + '\'' +
                        ",EnglishName = '" + englishName + '\'' +
                        "}";
    }
}
