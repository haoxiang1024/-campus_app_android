package com.hx.campus.adapter.entity;

import java.util.Date;
import java.util.Objects;

public class SearchInfo {

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

    private Lostfoundtype lostfoundtype;

    private Integer user_id;

    private String nickname;

    public SearchInfo() {
    }

    public SearchInfo(Integer id, String title, String img, Date pub_date, String content, String place, String phone, String state, Integer stick, Integer lostfoundtype_id, Lostfoundtype lostfoundtype, Integer user_id, String nickname) {
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
        this.lostfoundtype = lostfoundtype;
        this.user_id = user_id;
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

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
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

    public Integer getLostfoundtype_id() {
        return lostfoundtype_id;
    }

    public void setLostfoundtype_id(Integer lostfoundtype_id) {
        this.lostfoundtype_id = lostfoundtype_id;
    }

    public Lostfoundtype getLostfoundtype() {
        return lostfoundtype;
    }

    public void setLostfoundtype(Lostfoundtype lostfoundtype) {
        this.lostfoundtype = lostfoundtype;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchInfo that = (SearchInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(img, that.img) && Objects.equals(pub_date, that.pub_date) && Objects.equals(content, that.content) && Objects.equals(place, that.place) && Objects.equals(phone, that.phone) && Objects.equals(state, that.state) && Objects.equals(stick, that.stick) && Objects.equals(lostfoundtype_id, that.lostfoundtype_id) && Objects.equals(lostfoundtype, that.lostfoundtype) && Objects.equals(user_id, that.user_id) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, img, pub_date, content, place, phone, state, stick, lostfoundtype_id, lostfoundtype, user_id, nickname);
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", pub_date=" + pub_date +
                ", content='" + content + '\'' +
                ", place='" + place + '\'' +
                ", phone='" + phone + '\'' +
                ", state='" + state + '\'' +
                ", stick=" + stick +
                ", lostfoundtype_id=" + lostfoundtype_id +
                ", lostfoundtype=" + lostfoundtype +
                ", user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
