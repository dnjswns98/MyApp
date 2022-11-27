package com.example.myapp;

public class Memberinfo {
    private String id;
    private String pwd;
    private String nickname;
    private String username;
    private String phoneNum;
    private String imageURL;
    private String status;
    private String search;

    public Memberinfo(String id, String nickname, String username, String phoneNum, String imageURL, String status, String search){
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.phoneNum = phoneNum;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
    }
    public Memberinfo() {

    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }


}
