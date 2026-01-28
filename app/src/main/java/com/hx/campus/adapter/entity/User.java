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

    public User(Integer id, String password, String nickname, String photo, String sex, String phone, Integer balance, Integer prestige, Date reg_date) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.photo = photo;
        this.sex = sex;
        this.phone = phone;
        this.balance = balance;
        this.prestige = prestige;
        this.reg_date = reg_date;
    }

    public User() {
    }

    public User(int id, String nickname, String photo, String sex, String phone, Date reg_date) {
        this.id = id;
        this.nickname = nickname;
        this.photo = photo;
        this.sex = sex;
        this.phone = phone;
        this.reg_date = reg_date;
    }


  
    public Integer getId() {
        return id;
    }

  
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

  
    public void setPassword(String password) {
        this.password = password;
    }

  
    public String getNickname() {
        return nickname;
    }

  
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

  
    public String getPhoto() {
        return photo;
    }

  
    public void setPhoto(String photo) {
        this.photo = photo;
    }

  
    public String getSex() {
        return sex;
    }

  
    public void setSex(String sex) {
        this.sex = sex;
    }

  
    public String getPhone() {
        return phone;
    }

  
    public void setPhone(String phone) {
        this.phone = phone;
    }

  

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

  
    public Integer getPrestige() {
        return prestige;
    }

  
    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

  
    public Date getReg_date() {
        return reg_date;
    }

  
    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(nickname, user.nickname) && Objects.equals(photo, user.photo) && Objects.equals(sex, user.sex) && Objects.equals(phone, user.phone) && Objects.equals(balance, user.balance) && Objects.equals(prestige, user.prestige) && Objects.equals(reg_date, user.reg_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, nickname, photo, sex, phone, balance, prestige, reg_date);
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
                '}';
    }
}
