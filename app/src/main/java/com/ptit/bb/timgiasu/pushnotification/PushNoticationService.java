package com.ptit.bb.timgiasu.pushnotification;

import com.google.gson.JsonElement;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PushNoticationService {

    @POST("send")
    Call<Object> pushNotification(@Body PushNotificationDTO pushNotificationDTO);
}
