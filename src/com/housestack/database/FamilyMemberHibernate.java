/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.FamilyMember;
import org.hibernate.Session;

/**
 *
 * @author choudhary
 */
public class FamilyMemberHibernate {

    public FamilyMemberHibernate() {
    }

    public void insertFamilyMember(FamilyMember familyMember) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(familyMember);
            session.getTransaction().commit();
        }
    }

}
