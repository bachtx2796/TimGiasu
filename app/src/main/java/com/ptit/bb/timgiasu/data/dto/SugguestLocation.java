package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BB on 4/7/2018.
 */

public class SugguestLocation {

  @SerializedName("description")
  private String description;
  @SerializedName("structured_formatting")
  private StructureFormattingDTO structuredFormatting;

  private String key;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public SugguestLocation(String description, StructureFormattingDTO structuredFormatting) {
    this.description = description;
    this.structuredFormatting = structuredFormatting;
  }

  public StructureFormattingDTO getStructuredFormatting() {
    return structuredFormatting;
  }

  public void setStructuredFormatting(StructureFormattingDTO structuredFormatting) {
    this.structuredFormatting = structuredFormatting;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
