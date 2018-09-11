package com.ptit.bb.timgiasu.data.dto;

public class NotificationDataDTO {

    private String idUserSent;
    private String content;

    public NotificationDataDTO() {
    }

    public NotificationDataDTO(String idUserSent, String content) {
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
}
