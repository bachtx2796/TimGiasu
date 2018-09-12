package com.ptit.bb.timgiasu.data.dto;

public class MessageDTO {

    private String content;
    private String idSender;
    private long time;

    public MessageDTO() {
    }

    public MessageDTO(String content, String idSender, long time) {
        this.content = content;
        this.idSender = idSender;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getIdSender() {
        return idSender;
    }

    public long getTime() {
        return time;
    }
}
