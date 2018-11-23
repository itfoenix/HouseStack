/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author choudhary
 */
@Entity
@Table(name = "tblBuilding")
@DynamicUpdate
public class Building extends RecursiveTreeObject<Building> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int building_Id;
    private String building_name;
    private String building_num;
    private int floors;
    private int rooms;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "society_id")
    private Society society_id;
    private boolean visibility;

    public Building() {
    }

    public int getBuilding_Id() {
        return building_Id;
    }

    public void setBuilding_Id(int building_Id) {
        this.building_Id = building_Id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_num() {
        return building_num;
    }

    public void setBuilding_num(String building_num) {
        this.building_num = building_num;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public Society getSociety_id() {
        return society_id;
    }

    public void setSociety_id(Society society_id) {
        this.society_id = society_id;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return building_name + "    (" + society_id.getSociety_name() + ")";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.building_Id;
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
        final Building other = (Building) obj;
        if (this.building_Id != other.building_Id) {
            return false;
        }
        return true;
    }

}
