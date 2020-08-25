package com.example.dto;

public class UserVO {
    private int userid;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profile_image;
    private String phonenumber;

    public int getUserid() { return userid; }
    public void setUserid(int uesrid) { this.userid = userid; }

    public String getid() {
        return email;
    }
    public void setid(String id) {this.email = id; }

    public String getpassword() {
        return password;
    }
    public void setpassword(String password) { this.password = password; }

    public String getname() {
        return name;
    }
    public void setname(String name) { this.name = name; }

    public String getnickname() {
        return nickname;
    }
    public void setnickname(String nickname) {this.nickname = nickname; }

    public String getprofile_image() {
        return profile_image;
    }
    public void setprofile_image(String profile_image) {   this.profile_image = profile_image; }

    public String getphonenumber() {
        return phonenumber;
    }
    public void setphonenumber(String phonenumber) { this.phonenumber = phonenumber; }
}
