package com.hx.campus.adapter.entity;

import java.io.Serializable;
import java.util.Date;


public class LostFound implements Serializable {
    private Integer id;

    private String title;

    private String img;

    private Date pub_date;

    private String content;

    private String place;

    private String phone;

    private String state;

    private Integer stick;

    private Integer lostfoundtype_id;

    private Integer user_id;
    private String nickname;
    private  String type;
    private LostFoundType lostfoundtype;

    public LostFound(Integer id, String title, String img, Date pub_date, String content, String place, String phone, String state, Integer stick, Integer lostfoundtype_id, Integer user_id, String nickname, String type, LostFoundType lostfoundtype) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.pub_date = pub_date;
        this.content = content;
        this.place = place;
        this.phone = phone;
        this.state = state;
        this.stick = stick;
        this.lostfoundtype_id = lostfoundtype_id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.type = type;
        this.lostfoundtype = lostfoundtype;
    }

    public LostFoundType getLostfoundtype() {
        return lostfoundtype;
    }

    public void setLostfoundtype(LostFoundType lostfoundtype) {
        this.lostfoundtype = lostfoundtype;
    }

    public LostFound() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getImg() {
        return img;
    }


    public void setImg(String img) {
        this.img = img;
    }


    public Date getPubDate() {
        return pub_date;
    }


    public void setPubDate(Date pubDate) {
        this.pub_date = pubDate;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getPlace() {
        return place;
    }


    public void setPlace(String place) {
        this.place = place;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public Integer getStick() {
        return stick;
    }


    public void setStick(Integer stick) {
        this.stick = stick;
    }


    public Integer getLostfoundtypeId() {
        return lostfoundtype_id;
    }


    public void setLostfoundtypeId(Integer lostfoundtype_id) {
        this.lostfoundtype_id = lostfoundtype_id;
    }


    public Integer getUserId() {
        return user_id;
    }


    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }



}