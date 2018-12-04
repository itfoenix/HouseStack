/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Documents;
import org.hibernate.Session;

/**
 *
 * @author choudhary
 */
public class DocumentsHibernate {

    public void insertDocuments(Documents documents) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(documents);
            session.getTransaction().commit();
        }
    }
}
