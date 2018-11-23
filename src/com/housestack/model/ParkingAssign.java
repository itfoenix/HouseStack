/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
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
@Table(name = "tblparking_assign")
@DynamicUpdate
public class ParkingAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parking")
    private Parking parking_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle_id;
    private LocalDate sdate;
    private LocalDate edate;
    private String description;
    private boolean visibility;

    public ParkingAssign() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parking getParking_id() {
        return parking_id;
    }

    public void setParking_id(Parking parking_id) {
        this.parking_id = parking_id;
    }

    public Vehicle getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Vehicle vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getSdate() {
        return sdate;
    }

    public void setSdate(LocalDate sdate) {
        this.sdate = sdate;
    }

    public LocalDate getEdate() {
        return edate;
    }

    public void setEdate(LocalDate edate) {
        this.edate = edate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

}
