/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author choudhary
 */
@Entity
@Table(name = "tblperson")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    private String name;
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender")
    private Option gender;
    private String cont_number;
    private String per_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Option getGender() {
        return gender;
    }

    public void setGender(Option gender) {
        this.gender = gender;
    }

    public String getCont_number() {
        return cont_number;
    }

    public void setCont_number(String cont_number) {
        this.cont_number = cont_number;
    }

    public String getPer_type() {
        return per_type;
    }

    public void setPer_type(String per_type) {
        this.per_type = per_type;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", address=" + address + ", gender=" + gender + ", cont_number=" + cont_number + ", per_type=" + per_type + '}';
    }

}
