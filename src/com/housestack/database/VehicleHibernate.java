package com.housestack.database;

import com.housestack.model.Option;
import com.housestack.model.Vehicle;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * All Vehicle related Hibernate Queries
 *
 * @author Prashant
 */
public class VehicleHibernate {

    /**
     * This is a no Parameterized constructor.
     */
    public void VehicleHibernate() {
    }

    /**
     * This method is used to insert Vehicle information in database.
     *
     * @param vehicle It has a parameter of type Vehicle.
     */
    public void insertVehicle(Vehicle vehicle) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.save(vehicle);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Vehicle details. It updates all the details
     * of Vehicle if any value is not specified it set's it as NULL.
     *
     * @param vehicle It has a parameter of type Vehicle.
     */
    public void updateVehicle(Vehicle vehicle) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(vehicle);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Vehicle. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param vehicle It has a parameter of type Vehicle.
     */
    public void deleteVehicle(Vehicle vehicle) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            vehicle = session.find(Vehicle.class, vehicle);
            vehicle.setVisibility(true);
            session.update(vehicle);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get Vehicle of a particular type. i.e. 2-Wheeler
     * or 4-Wheeler.
     *
     * @param option It has a parameter of type String.
     * @return It return objects of Vehicle in a List object.
     */
    public List<Vehicle> getVehicleByType(String option) {
        List<Vehicle> vehicleList;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
            Root<Vehicle> root = cq.from(Vehicle.class);
            Join<Vehicle, Option> joins = root.join("room_type");
            cq.where(cb.equal(joins.get("name"), option));
            vehicleList = session.createQuery(cq).getResultList();
        }
        return vehicleList;
    }

}
