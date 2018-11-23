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
@Table(name = "tblparking")
@DynamicUpdate
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building_id;
    private String slot_Name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slot_type")
    private Option slot_Type;
    private String description;
    private boolean status;
    private boolean visibility;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Building getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Building building_id) {
        this.building_id = building_id;
    }

    public String getSlot_Name() {
        return slot_Name;
    }

    public void setSlot_Name(String slot_Name) {
        this.slot_Name = slot_Name;
    }

    public Option getSlot_Type() {
        return slot_Type;
    }

    public void setSlot_Type(Option slot_Type) {
        this.slot_Type = slot_Type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

}
