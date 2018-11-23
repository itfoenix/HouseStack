/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Session creation class.
 *
 * @author Prashant
 */
public class HibernateUnit {

    /**
     * This is a no parameterized constructor
     *
     */
    public HibernateUnit() {
    }

    /**
     * This method is used to create Session. It registers the service in
     * StandardServiceRegistry by providing the path of hibernate.'cfg'.'xml'.
     * Then it builds the SessionFactory and using the SessionFactory it creates
     * the Session
     *
     * @return It returns Session;
     */
    public static Session createSession() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sf = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
        Session session = sf.openSession();
        return session;
    }
}
