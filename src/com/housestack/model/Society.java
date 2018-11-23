/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author choudhary
 */
@Entity
@Table(name = "tblSociety")
@DynamicUpdate
public class Society extends RecursiveTreeObject<Society> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int society_id;
    private String society_name;
    private String cont_number;
    private String area, city, zip_Code, states, country;
    @OneToOne
    @JoinColumn(name = "society_type")
    private Option type;
    private String creation_year;
    private String founder, builder;
    private String regi_number;
    private boolean visibility;

    public Society() {
    }

    public int getSociety_id() {
        return society_id;
    }

    public void setSociety_id(int society_id) {
        this.society_id = society_id;
    }

    public String getSociety_name() {
        return society_name;
    }

    public void setSociety_name(String society_name) {
        this.society_name = society_name;
    }

    public String getCont_number() {
        return cont_number;
    }

    public void setCont_number(String cont_number) {
        this.cont_number = cont_number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_Code() {
        return zip_Code;
    }

    public void setZip_Code(String zip_Code) {
        this.zip_Code = zip_Code;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Option getType() {
        return type;
    }

    public void setType(Option type) {
        this.type = type;
    }

    public String getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(String creation_year) {
        this.creation_year = creation_year;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getRegi_number() {
        return regi_number;
    }

    public void setRegi_number(String regi_number) {
        this.regi_number = regi_number;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return society_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.society_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Society other = (Society) obj;
        if (this.society_id != other.society_id) {
            return false;
        }
        return true;
    }

}
