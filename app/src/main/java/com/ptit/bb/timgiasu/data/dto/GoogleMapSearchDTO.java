package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by HaiLS on 10/11/2017.
 */

public class GoogleMapSearchDTO {

  @SerializedName("results")
  private ArrayList<LocationSearchResult> mListSearchResult;
  @SerializedName("status")
  private String status;

  public ArrayList<LocationSearchResult> getListSearchResult() {
    return mListSearchResult;
  }

  public void setListSearchResult(ArrayList<LocationSearchResult> listSearchResult) {
    this.mListSearchResult = listSearchResult;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
