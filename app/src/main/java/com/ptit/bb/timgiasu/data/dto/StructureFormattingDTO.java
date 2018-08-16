package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BB on 4/23/2018.
 */

public class StructureFormattingDTO {

  @SerializedName("main_text")
  private String mainText;

  public String getMainText() {
    return mainText;
  }
}
