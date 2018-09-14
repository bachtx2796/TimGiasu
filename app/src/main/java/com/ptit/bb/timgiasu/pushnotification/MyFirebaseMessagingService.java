package com.ptit.bb.timgiasu.pushnotification; /**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.Utils.GlobalStuff;
import com.ptit.bb.timgiasu.Utils.NotificationUtils;
import com.ptit.bb.timgiasu.data.dto.NotificationDataDTO;
import com.ptit.bb.timgiasu.screen.main.MainActivity;
import com.ptit.bb.timgiasu.screen.splash.SplashActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static final String REQUEST = "request";
    public static final String MSG = "message";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (!remoteMessage.getData().isEmpty()) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                //          JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(remoteMessage);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }
    }

    private void handleDataMessage(RemoteMessage remoteMessage) {
//        if (AppUtils.isForeGround(getApplicationContext())) {
//            //Do nothing
//        } else {
        Map<String, String> data = remoteMessage.getData();
        NotificationDataDTO notificationDTO = new NotificationDataDTO(data.get("type"), data.get("idPost"), data.get("idUserSent"), data.get("content"));
        if (notificationDTO.getType().equals(MyFirebaseMessagingService.REQUEST)) { // noti kieu co ng request
            showNotification(notificationDTO);
        } else if (notificationDTO.getType().equals(MyFirebaseMessagingService.MSG) && !AppUtils.isForeGround(getApplicationContext())) { // noti kieu msg va app dang ko hien
            showNotification(notificationDTO);
        }
    }

    private void showNotification(NotificationDataDTO notificationDTO) {
        Gson gson = new Gson();
        String json = gson.toJson(notificationDTO);
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.putExtra(REQUEST, json);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), GlobalStuff.getFreshInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtils.showNotification(getApplicationContext(), notificationDTO.getIdUserSent(), notificationDTO.getContent(), pendingIntent);
    }


}