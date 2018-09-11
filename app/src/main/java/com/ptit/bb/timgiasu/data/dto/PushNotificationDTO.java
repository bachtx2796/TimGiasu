package com.ptit.bb.timgiasu.data.dto;

public class PushNotificationDTO {

    private String to;
    private NotificationDataDTO data;

    public PushNotificationDTO() {
    }

    public PushNotificationDTO(String to, NotificationDataDTO data) {
        this.to = to;
        this.data = data;
    }
}
