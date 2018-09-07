package com.ptit.bb.timgiasu.data.dto;

import java.util.ArrayList;
import java.util.List;

public class PostDTO {

    private String id;
    private String idUser;
    private List<String> uris = new ArrayList<>();
    private String address;
    private List<String> classes;
    private List<String> subjects;
    private String salary;
    private String status;
    private long timecreate;
    private String time;


    public PostDTO(String id, String idUser, List<String> uris, String address, List<String> classes, List<String> subjects, String salary, String status,long timecreate,String time) {
        this.id = id;
        this.idUser = idUser;
        this.uris = uris;
        this.address = address;
        this.classes = classes;
        this.subjects = subjects;
        this.salary = salary;
        this.status = status;
        this.timecreate = timecreate;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getObjects() {
        return subjects;
    }

    public void setObjects(List<String> objects) {
        this.subjects = objects;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(long timecreate) {
        this.timecreate = timecreate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id='" + id + '\'' +
                ", idUser='" + idUser + '\'' +
                ", uris=" + uris +
                ", address='" + address + '\'' +
                ", classes=" + classes +
                ", objects=" + subjects +
                ", salary='" + salary + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
