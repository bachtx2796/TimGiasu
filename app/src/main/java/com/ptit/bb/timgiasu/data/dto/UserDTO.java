package com.ptit.bb.timgiasu.data.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDTO {

    private String id;
    protected String name;
    protected String email;
    protected String city;
    protected String phoneNo;
    protected String gender;
    protected String dob;
    protected String address;
    protected String avatar;
    private boolean isTutor = false;
    private List<String> classes;
    private List<String> subjects;
    private String time;
    private long salary;
    private List<String> uris;
    private HashMap<String,Integer> ratings;
    private String devicetoken;
    private List<String> receviepost;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email, String city, String phoneNo, String gender, String dob, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        classes = new ArrayList<>();
        subjects = new ArrayList<>();
        time = "";
        salary = 0;
        uris = new ArrayList<>();
        ratings = new HashMap<>();
        receviepost = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public void setTutor(boolean tutor) {
        isTutor = tutor;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String mtime) {
        this.time = mtime;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public HashMap<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Integer> ratings) {
        this.ratings = ratings;
    }

    public List<String> getReceviepost() {
        return receviepost;
    }

    public void setReceviepost(List<String> receviepost) {
        this.receviepost = receviepost;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isTutor=" + isTutor +
                ", classes=" + classes +
                ", subjects=" + subjects +
                ", time='" + time + '\'' +
                ", salary=" + salary +
                ", uris=" + uris +
                '}';
    }

    public String getDeviceToken() {
        return devicetoken;
    }

    public void setDeviceToken(String deviceToken) {
        this.devicetoken = deviceToken;
    }
}
