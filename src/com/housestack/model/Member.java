/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author choudhary
 */
@Entity
@Table(name = "tblmember")
@DynamicUpdate
@PrimaryKeyJoinColumn(name = "person_id")
public class Member extends Person {

    private LocalDate birthday;
    private String photo;
    private String alt_number;
    private String off_number;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_type")
    private Option id_Type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_type")
    private Option member_Type;
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room_id;
    private boolean visibility;

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAlt_number() {
        return alt_number;
    }

    public void setAlt_number(String alt_number) {
        this.alt_number = alt_number;
    }

    public String getOff_number() {
        return off_number;
    }

    public void setOff_number(String off_number) {
        this.off_number = off_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Option getId_Type() {
        return id_Type;
    }

    public void setId_Type(Option id_Type) {
        this.id_Type = id_Type;
    }

    public Option getMember_Type() {
        return member_Type;
    }

    public void setMember_Type(Option member_Type) {
        this.member_Type = member_Type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Room getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Room room_id) {
        this.room_id = room_id;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "Person{" + " id " + getId() + " name " + getName() + " Member{" + "birthday=" + birthday + ", photo=" + photo + ", alt_number=" + alt_number + ", off_number=" + off_number + ", email=" + email + ", id_Type=" + id_Type + ", member_Type=" + member_Type + ", username=" + username + ", password=" + password + ", room_id=" + room_id + ", visibility=" + visibility + " }" + '}';
    }

}