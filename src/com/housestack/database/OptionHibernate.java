/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Option;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 * All Option related Hibernate Queries.
 *
 * @author Prashant
 */
public class OptionHibernate {

    /**
     * This is a no Parameterized constructor.
     */
    public OptionHibernate() {
    }

    /**
     * This method is used to insert Options which are connected to almost every
     * module.
     *
     * @param option It has a parameter of type Option.
     */
    public void insertOption(Option option) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(option);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Option details. It updates all the details
     * of Option if any value is not specified it set's it as NULL.
     *
     * @param option It has a parameter of type Option.
     */
    public void updateOption(Option option) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(option);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Option. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param option It has a parameter of type Option.
     */
    public void deleteOption(Option option) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            option = session.find(Option.class, option.getId());
            option.setVisibility(true);
            session.update(option);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get all the inserted Options whose visibility = 0
     * i.e false.
     *
     * @return It returns List of Option.
     */
    public List<Option> getAllOption() {
        List<Option> optionList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from option where visibility = 0");
            optionList = query.getResultList();
        }
        return optionList;
    }

    /**
     * This method is used to get the Option of particular type
     *
     * @param type It has a parameter of type Integer.
     * @return It returns the objects of Option in a List.
     */
    public List<Option> getOptionOfType(int type) {
        List<Option> optionList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Option where type = :t");
            query.setParameter("t", type);
            optionList = query.getResultList();
        }
        return optionList;
    }
}
