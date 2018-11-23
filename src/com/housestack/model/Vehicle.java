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
@Table(name = "tblvehicle")
@DynamicUpdate
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String vehicle_number;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type")
    private Option vehicle_type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person_id;
    private boolean visibility;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getVehical_number() {
        return vehicle_number;
    }

    /**
     *
     * @param vehicle_number
     */
    public void setVehical_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    /**
     *
     * @return
     */
    public Option getVehical_type() {
        return vehicle_type;
    }

    /**
     *
     * @param vehicle_type
     */
    public void setVehical_type(Option vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    /**
     *
     * @return
     */
    public Person getPerson_id() {
        return person_id;
    }

    /**
     *
     * @param person_id
     */
    public void setPerson_id(Person person_id) {
        this.person_id = person_id;
    }

    /**
     *
     * @return
     */
    public boolean isVisibility() {
        return visibility;
    }

    /**
     *
     * @param visibility
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

}
