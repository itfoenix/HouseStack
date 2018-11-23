/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Member;
import com.housestack.model.Option;
import com.housestack.model.Room;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 * All Member Related Hibernate Queries.
 *
 * @author Prashant
 */
public class MemberHibernate {

    /**
     * This is a no parameterized Constructor.
     */
    public MemberHibernate() {
    }

    /**
     * This method is used to insert Member details.
     *
     * @param member It has a parameter of type Member.
     */
    public void insertMember(Member member) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.persist(member);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get List of all the Members.
     *
     * @return It returns objects of Member in List object.
     */
    public List<Member> getAllMembers() {
        List<Member> memberList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Member where visibility = 0");
            memberList = query.getResultList();
        }
        return memberList;
    }

    /**
     * This method is used to update Member details. It updates all the details
     * of Member if any value is not specified it set's it as NULL.
     *
     * @param member It has a parameter of type Member.
     */
    public void updateMember(Member member) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(member);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Member. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param member It has a parameter of type Member.
     */
    public void deleteMember(Member member) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            member = session.find(Member.class, member);
            member.setVisibility(true);
            session.update(member);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get complete Member details of Member's with name
     * given as parameter.
     *
     * @param name It has a parameter of type String.
     * @return It returns objects of Members in List object.
     */
    public List<Member> getMember(String name) {
        List<Member> memberList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Member where name = :n and visibility = 0");
            query.setParameter("n", name);
            memberList = query.getResultList();
        }
        return memberList;
    }

    /**
     * This method is used to get all the member of certain type e.g. Tenant
     * Member or Owner
     *
     * @param option This parameter is of type String.
     * @return It return objects of Members in a List object.
     */
    public List<Member> getMemberByType(String option) {
        List<Member> memberList;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Member> cq = cb.createQuery(Member.class);
            Root<Member> root = cq.from(Member.class);
            Join<Member, Option> joins = root.join("member_Type");
            cq.where(cb.equal(joins.get("name"), option));
            memberList = session.createQuery(cq).getResultList();
        }
        return memberList;
    }

}
