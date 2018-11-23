/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Building;
import com.housestack.model.Society;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 * All Building related Hibernate Queries.
 *
 * @author Prashant
 */
public class BuildingHibernate {

    /**
     * This is a no parameterized constructor of class BuildingHibernate
     *
     */
    public BuildingHibernate() {
    }

    /**
     * This method is used to insert Building details.
     *
     * @param building It has a parameter of type Building.
     */
    public void insertBuilding(Building building) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(building);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Building details. It updates all the
     * details of Building if any value is not specified it set's it as NULL.
     *
     * @param buildingIt has a parameter of type Building.
     */
    public void updateBuilding(Building building) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(building);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Building. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param building It has a parameter of type Building
     */
    public void deleteBuilding(Building building) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            building = session.find(Building.class, building.getBuilding_Id());
            building.setVisibility(true);
            session.update(building);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get all the inserted Buildings whose visibility =
     * 0 i.e false.
     *
     * @return It return a List of Building.
     */
    public List<Building> getAllBuilding() {
        List<Building> buildingList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Building where visibility = 0");
            buildingList = query.getResultList();
        }
        return buildingList;
    }

    /**
     * This method is used to get Building details from building name.
     *
     * @param buildingName It has a parameter of type String.
     * @return It returns the object of Building;
     */
    public Building getBuildingByBuildingName(String buildingName) {
        Building building;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Building where name = :n");
            query.setParameter("n", buildingName);
            building = (Building) query.getSingleResult();
        }
        return building;
    }

    /**
     * This method is used to get all the Buildings of a particular Society.
     *
     * @param societyName It has a parameter of type String.
     * @return It returns the objects of Building in a List.
     */
    public List<Building> getAllBuildingsInSociety(String societyName) {
        List<Building> buildingList;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Building> cq = cb.createQuery(Building.class);
            Root<Building> root = cq.from(Building.class);
            Join<Building, Society> joins = root.join("society_id");
            cq.where(cb.equal(joins.get("name"), societyName));
            buildingList = session.createQuery(cq).getResultList();
        }
        return buildingList;
    }
}
