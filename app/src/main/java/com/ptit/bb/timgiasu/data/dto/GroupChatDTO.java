package com.ptit.bb.timgiasu.data.dto;

public class GroupChatDTO {

    private String id;
    private String idOwner;
    private String idClient;
    private String idPost;
    private String lastMsg;

    public GroupChatDTO() {
    }

    public GroupChatDTO(String id, String idOwner, String idClient, String idPost, String lastMsg) {
        this.id = id;
        this.idOwner = idOwner;
        this.idClient = idClient;
        this.idPost = idPost;
        this.lastMsg = lastMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

}
