/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.database;

import com.housestack.model.Building;
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
 * All Room related Hibernate Queries.
 *
 * @author Prashant
 */
public class RoomHibernate {

    /**
     * This is a no parameterized Constructor.
     */
    public RoomHibernate() {
    }

    /**
     * This method is used to insert Room details.
     *
     * @param room It has a parameter of type Room.
     */
    public void insertRoom(Room room) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.saveOrUpdate(room);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to update Room details. It updates all the details of
     * Room if any value is not specified it set's it as NULL.
     *
     * @param room It has a parameter of type Room.
     */
    public void updateRoom(Room room) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            session.update(room);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete Room. It only updates value of column
     * visibility from false to true i.e. 0 to 1.
     *
     * @param room It has a parameter of type Room.
     */
    public void deleteRoom(Room room) {
        try (Session session = HibernateUnit.createSession()) {
            session.beginTransaction();
            room = session.find(Room.class, room.getRoom_id());
            room.setVisibility(true);
            session.update(room);
            session.getTransaction().commit();
        }
    }

    /**
     * This method is used to get all the inserted Room whose visibility = 0 i.e
     * false.
     *
     * @return It returns List of Room.
     */
    public List<Room> getAllRoom() {
        List<Room> roomList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Room where visibility = 0");
            roomList = query.getResultList();
        }
        return roomList;
    }

    /**
     * This method is used to get the Building details of a particular Room.
     *
     * @param room It has a parameter of type Room.
     * @return The return type is Building.
     */
    public Building getRoomBuilding(Room room) {
        Building building;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Room where room_id = :i and visibility = 0");
            query.setParameter("i", room.getRoom_id());
            building = ((Room) query.getSingleResult()).getBuilding_id();
        }
        return building;
    }

    /**
     * This method is used to get Room details by Room Number.
     *
     * @param name It has a parameter of type String.
     * @return It returns the object of Room.
     */
    public Room getRoomByRoomNumber(String name) {
        Room room;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Room where room_name = : r and visibility = 0");
            query.setParameter("r", name);
            room = (Room) query.getSingleResult();
        }
        return room;
    }

    /**
     * This method is to get All the rooms of a particular building.
     *
     * @param buildingName It has a parameter of type String.
     * @return It returns the object of Room in List.
     */
    public List<Room> getAllRoomsByBuilding(String buildingName) {
        List<Room> roomList;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            Join<Room, Building> joins = root.join("building_id");
            cq.where(cb.equal(joins.get("name"), buildingName));
            roomList = session.createQuery(cq).getResultList();
        }
        return roomList;
    }

    /**
     * This method is used to get List of Room of a particular Option i.e. Wing,
     * room type, etc..
     *
     * @param option It has a parameter of type String.
     * @return It returns object of Room in a List.
     */
    public List<Room> getAllRoomsByOption(String option) {
        List<Room> roomList;
        try (Session session = HibernateUnit.createSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            Join<Room, Option> joins = root.join("room_type");
            cq.where(cb.equal(joins.get("name"), option));
            roomList = session.createQuery(cq).getResultList();
        }
        return roomList;
    }

    /**
     * This method is used to get List of all Tenant Room or List of all
     * Non-Tenant Room based of the value of parameter. If false is passed as
     * parameter it will return all the Non-Tenant Room, if true is passed as
     * parameter it will return all the Tenant Room.
     *
     * @param tenant It has a parameter of type boolean.
     * @return It returns the objects of Room in List object.
     */
    public List<Room> getAllTenantRooms(boolean tenant) {
        List<Room> roomList;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Room where room_tenant = :t and visibility = 0");
            query.setParameter("t", tenant);
            roomList = query.getResultList();
        }
        return roomList;
    }

    /**
     * This method is used to check if a particular Room is a Tenant Room or
     * Non-Tenant Room.
     *
     * @param room It has a parameter of type Room.
     * @return It return boolean value.
     */
    public boolean isTenant(Room room) {
        Room rooms;
        try (Session session = HibernateUnit.createSession()) {
            Query query = session.createQuery("from Room where visibility = 0");
            rooms = (Room) query.getSingleResult();
        }
        return rooms.isRoom_tenant();
    }
}
