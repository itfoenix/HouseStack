/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Society;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 * All Society related Hibernate Queries.
 *
 * @author Prashant
 */
public class SocietyHibernate {

    /**
     * This is a no parameterized constructor of class SocietyHibernate.
     *
     */
    public SocietyHibernate() {
    }

    /**
     * This method is used to insert Society Details from society variable
     * passed as argument.
     *
     * @param society It has a parameter of type Society.
     */
    public void insertSociety(Society society) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(society);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Society Details. In this method all the
     * details are updated.
     *
     * @param society It has a parameter of type Society.
     */
    public void updateSociety(Society society) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(society);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Society details. It simply updates the
     * value of visibility to 1. But while updating it only updates visibility
     * column.
     *
     * @param society It has a parameter of type Society.
     */
    public void deleteSociety(Society society) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            society = session.find(Society.class, society.getSociety_id());
            society.setVisibility(true);
            session.update(society);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get all the societies which are inserted in a form
     * of List of Society.
     *
     * @return It return a List of type Society.
     */
    public List<Society> getAllSociety() {
        List<Society> societyList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Society where visibility = 0");
            societyList = query.getResultList();
        }
        return societyList;
    }

}
