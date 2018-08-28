package com.ptit.bb.timgiasu.data;

import com.ptit.bb.timgiasu.data.dto.ImgurBaseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ImgurlService {
    @FormUrlEncoded
    @POST("upload")
    Call<ImgurBaseData> pushImageToImgur(@Field("image") String imageBase64);
}
