package com.ptit.bb.timgiasu.data.dto;

import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;

public class NotificationDataDTO {

    private String type;
    private String idPost;
    private String idUserSent;
    private String content;

    public NotificationDataDTO() {
    }

    public NotificationDataDTO(String type, String idPost, String idUserSent, String content) {
        this.type = type;
        this.idPost = idPost;
        this.idUserSent = idUserSent;
        this.content = content;
    }

    public String getIdUserSent() {
        return idUserSent;
    }

    public void setIdUserSent(String idUserSent) {
        this.idUserSent = idUserSent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getType() {
        return type;
    }
}
