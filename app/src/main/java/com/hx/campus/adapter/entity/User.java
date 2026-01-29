package com.hx.campus.adapter.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class User implements Serializable {
    private Integer id;
    private String password;
    private String nickname;
    private String photo;
    private String sex;
    private String phone;
    private Integer balance;
    private Integer prestige;
    private Date reg_date;
    private String email;
    private int state;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getstate() {
        return state;
    }

    public void setstate(int state) {
        this.state = state;
    }

    public User(String phone, String password, String email) {
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String photo, String phone, String sex, Integer balance, Integer prestige, Date reg_date, String email, int state, int role, String nickname, String password) {
        this.photo = photo;
        this.phone = phone;
        this.sex = sex;
        this.balance = balance;
        this.prestige = prestige;
        this.reg_date = reg_date;
        this.email = email;
        this.state = state;
        this.role = role;
        this.nickname = nickname;
        this.password = password;
    }

    public User() {
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     */
    public String getSex() {
        return sex;
    }

    /**
     *
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     */

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     *
     */
    public Integer getPrestige() {
        return prestige;
    }

    /**
     *
     */
    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    /**
     *
     */
    public Date getReg_date() {
        return reg_date;
    }

    /**
     *
     */
    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                ", prestige=" + prestige +
                ", reg_date=" + reg_date +
                ", email='" + email + '\'' +
                ", state=" + state +
                '}';
    }
}
