/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Building;
import com.housestack.model.Option;
import com.housestack.model.Parking;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author choudhary
 */
public class ParkingHibernate {

    public List<Parking> getAllParking() {
        List<Parking> parkingList = new ArrayList<>();
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Parking where visibility = 0");
            parkingList = query.getResultList();
        }
        return parkingList;
    }

    public List<Parking> getAllParkingOfBuilding(Building b) {
        List<Parking> parkingList = new ArrayList<>();
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Parking where building_id = :b and visibility = 0");
            query.setParameter(1, b);
            parkingList = query.getResultList();
        }
        return parkingList;
    }

    public List<Parking> getAllParkingByType(Option o) {
        List<Parking> parkingList = new ArrayList<>();
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Parking where slot_Type = :s and visibility = 0");
            query.setParameter("s", o);
            parkingList = query.getResultList();
        }
        return parkingList;
    }

    public void insertParking(Parking parking) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(parking);
            session.getTransaction().commit();
        }
    }

    public void deleteParking(Parking parking) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            parking = session.find(Parking.class, parking.getId());
            parking.setVisibility(true);
            session.update(parking);
            session.getTransaction().commit();
        }
    }
}
