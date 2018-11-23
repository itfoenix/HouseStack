/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author choudhary
 */
@Entity
@Table(name = "tblroom")
@DynamicUpdate
public class Room extends RecursiveTreeObject<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type")
    private Option room_type;
    private String room_name;
    private String room_size;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building")
    private Building building_id;
    private int floor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wing")
    private Option wing;
    private boolean room_tenant;
    private boolean visibility;

    public Room() {
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Option getRoom_type() {
        return room_type;
    }

    public void setRoom_type(Option room_type) {
        this.room_type = room_type;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_size() {
        return room_size;
    }

    public void setRoom_size(String room_size) {
        this.room_size = room_size;
    }

    public Building getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Building building_id) {
        this.building_id = building_id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Option getWing() {
        return wing;
    }

    public void setWing(Option wing) {
        this.wing = wing;
    }

    public boolean isRoom_tenant() {
        return room_tenant;
    }

    public void setRoom_tenant(boolean room_tenant) {
        this.room_tenant = room_tenant;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return room_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.room_id;
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
        final Room other = (Room) obj;
        if (this.room_id != other.room_id) {
            return false;
        }
        return true;
    }

}
