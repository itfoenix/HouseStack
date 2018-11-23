/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Committee;
import com.housestack.model.Member;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * All Committee related Hibernate Queries.
 *
 * @author choudhary
 */
public class CommitteeHibernate {

    /**
     * This is a no parameterized Constructor.
     */
    public CommitteeHibernate() {
    }

    /**
     * This method is used to insert Committee details.
     *
     * @param committee It has a parameter of type Committee.
     */
    public void insertCommittee(Committee committee) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.save(committee);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Committee details. It updates all the
     * details of Committee if any value is not specified it set's it as NULL.
     *
     * @param committee It has a parameter of type Committee.
     */
    public void updateCommittee(Committee committee) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(committee);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to set CommitteeMember. It only updates value of
     * column member_id.
     *
     * @param committee It has a parameter of type Committee.
     */
    public void updateCommitteeMember(Committee committee) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            Member member = committee.getMember_id();
            committee = session.find(Committee.class, committee.getId());
            committee.setMember_id(member);
            session.update(committee);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Committee. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param committee It has a parameter of type Committee.
     */
    public void deleteCommittee(Committee committee) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            committee = session.find(Committee.class, committee.getId());
            committee.setVisibility(true);
            session.update(committee);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get all the inserted Committees whose visibility =
     * 0 i.e false.
     *
     * @return It returns List of Committee.
     */
    public List<Committee> getAllCommittee() {
        List<Committee> committeeList;
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Committee where visibility = 0");
            committeeList = query.getResultList();
            session.getTransaction().commit();
        }
        return committeeList;
    }

    /**
     * This method is used to get Committee Object of a particular designation.
     *
     * @param designation It has a parameter of type String.
     * @return It returns an object of Committee.
     */
    public Committee getCommitteeByDesignation(String designation) {
        Committee committee;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Committee where designation = :d");
            query.setParameter("d", designation);
            committee = (Committee) query.getSingleResult();
        }
        return committee;
    }

    /**
     * This method is used to get Committee by Member Name.
     *
     * @param memberName It has a parameter of type String.
     * @return It returns an object of Committee.
     */
    public Committee getCommitteeByName(String memberName) {
        Committee committee;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Committee> cq = cb.createQuery(Committee.class);
            Root<Committee> root = cq.from(Committee.class);
            Join<Committee, Member> joins = root.join("member_id");
            cq.where(cb.equal(joins.get("name"), memberName));
            committee = session.createQuery(cq).getSingleResult();
        }
        return committee;
    }
}
