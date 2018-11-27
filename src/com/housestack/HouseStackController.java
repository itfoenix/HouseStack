/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack;

import com.housestack.database.OptionHibernate;
import com.housestack.database.SocietyHibernate;
import com.housestack.model.Option;
import com.housestack.model.Society;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author choudhary
 */
public class HouseStackController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
//        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//        try (SessionFactory sf = new MetadataSources(ssr).buildMetadata().buildSessionFactory()) {
//            Session session = sf.openSession();
//            session.beginTransaction();

//        Society Details
//        Society so = new Society();
//        so.setSociety_name("Hidden Leaf Village");
//        so.setArea("Sakinaka");
//        so.setBuilder("Prashant");
//        so.setCity("Mumbai");
//        so.setCont_number("8082314468");
//        so.setCountry("India");
//        so.setCreation_year("2018");
//        so.setFounder("Naruto");
//        so.setRegi_number("A-102145HLV012");
//        so.setStates("Maharashtra");
//        so.setType("Housing");
//        so.setZip_Code("400072");
//////        Building Details
//        Building s = new Building();
//        s.setBuilding_name("Shreya");
//        s.setBuilding_num("7");
//        s.setFloors(6);
//        s.setRooms(21);
//        s.setSociety_id(so);
////
////        Building s2 = new Building();
////        s2.setBuilding_name("reya");
////        s2.setBuilding_num("8");
////        s2.setFloors(6);
////        s2.setRooms(21);
////        s2.setSociety_id(so);
////
////        SocietyHibernate sh = new SocietyHibernate();
////        sh.insertSociety(so);
////
//////        BuildingHibernate bh = new BuildingHibernate();
//////        bh.insertBuilding(s2);
//////        bh.insertBuilding(s);
//////            Query query = session.createQuery("from Building");
//////            List<Building> bList = query.getResultList();
//////            for (Building b : bList) {
//////                System.out.println(b);
//////            }
//////
//////            Query query1 = session.createQuery("from Building where building_Id = :i");
//////            query1.setParameter("i", 1);
//////            try {
//////                Building building = (Building) query1.getSingleResult();
//////                System.out.println(building);
//////            } catch (Exception ex) {
//////                System.out.println(ex);
//////            }
//////            1 = Wing
//////            2 = RoomType
//////            3 = Gender
//////            4 = ID-Proof
//////            5 = MemberType
//////            6 = VehicalType
//////            7 = ParkingType
//////            8 = Designation
//                9 = SocietyType
        Option op16 = new Option();
        op16.setName("Building");
        op16.setType(9);
        op16.setDate(LocalDate.now());

        Option op17 = new Option();
        op17.setName("Chawl");
        op17.setType(9);
        op17.setDate(LocalDate.now());
        Option op2 = new Option();
        op2.setName("A-Wing");
        op2.setType(1);
        op2.setDate(LocalDate.now());
        Option op3 = new Option();
        op3.setName("B-Wing");
        op3.setType(1);
        op3.setDate(LocalDate.now());
        Option op = new Option();
        op.setName("1BHK");
        op.setType(2);
        op.setDate(LocalDate.now());
        Option op1 = new Option();
        op1.setName("2BHK");
        op1.setType(2);
        op1.setDate(LocalDate.now());
        Option op4 = new Option();
        op4.setName("Male");
        op4.setType(3);
        op4.setDate(LocalDate.now());
        Option op5 = new Option();
        op5.setName("Female");
        op5.setType(3);
        op5.setDate(LocalDate.now());
        Option op6 = new Option();
        op6.setName("Aadhar Card");
        op6.setType(4);
        op6.setDate(LocalDate.now());
        Option op7 = new Option();
        op7.setName("Pan Card");
        op7.setType(4);
        op7.setDate(LocalDate.now());
        Option op8 = new Option();
        op8.setName("Owner");
        op8.setType(5);
        op8.setDate(LocalDate.now());
        Option op9 = new Option();
        op9.setName("Tenant");
        op9.setType(5);
        op9.setDate(LocalDate.now());
        Option op10 = new Option();
        op10.setName("2 Wheeler");
        op10.setType(6);
        op10.setDate(LocalDate.now());
        Option op11 = new Option();
        op11.setName("4 Wheeler");
        op11.setType(6);
        op11.setDate(LocalDate.now());
        Option op12 = new Option();
        op12.setName("Members");
        op12.setType(7);
        op12.setDate(LocalDate.now());
        Option op13 = new Option();
        op13.setName("Guests");
        op13.setType(7);
        op13.setDate(LocalDate.now());
        Option op14 = new Option();
        op14.setName("Secretory");
        op14.setType(8);
        op14.setDate(LocalDate.now());
        Option op15 = new Option();
        op15.setName("Member");
        op15.setType(8);
        op15.setDate(LocalDate.now());
////
        OptionHibernate oh = new OptionHibernate();
        oh.insertOption(op2);
        oh.insertOption(op3);
        oh.insertOption(op);
        oh.insertOption(op1);
        oh.insertOption(op4);
        oh.insertOption(op5);
        oh.insertOption(op6);
        oh.insertOption(op7);
        oh.insertOption(op8);
        oh.insertOption(op9);
        oh.insertOption(op10);
        oh.insertOption(op13);
        oh.insertOption(op14);
        oh.insertOption(op15);
        oh.insertOption(op11);
        oh.insertOption(op12);
        oh.insertOption(op16);
        oh.insertOption(op17);

//         Society Details
//        Society so = new Society();
//        so.setSociety_name("Hidden Leaf Village");
//        so.setArea("Sakinaka");
//        so.setBuilder("Prashant");
//        so.setCity("Mumbai");
//        so.setCont_number("8082314468");
//        so.setCountry("India");
//        so.setCreation_year("2018");
//        so.setFounder("Naruto");
//        so.setRegi_number("A-102145HLV012");
//        so.setStates("Maharashtra");
//        so.setType(op16);
//        so.setZip_Code("400072");
//        SocietyHibernate sh = new SocietyHibernate();
//        sh.insertSociety(so);
//        Room room = new Room();
//        room.setRoom_name("A-002");
//        room.setRoom_type(op1);
//        room.setWing(op2);
//        room.setRoom_size("2014");
//        room.setFloor(1);
//        room.setBuilding_id(s);
//
//        RoomHibernate rh = new RoomHibernate();
//        rh.insertRoom(room);
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Rooms> crit = cb.createQuery(Room.class);
//            Root<Rooms> root = crit.from(Room.class);
//            Join<Rooms, Option> joins = root.join("room_type");
//            crit.where(cb.equal(joins.get("name"), "1BHK"));
//            List<Rooms> roomList = session.createQuery(crit).getResultList();
//            System.out.println(roomList);
//            Member m = new Member();
//            m.setName("Prashant Dhuri");
//            m.setAddress("Mahavir Niwas, Sakinaka");
//            m.setCont_number("8082314468");
////        m.setGender(op4);
//            m.setPer_type("Member");
//            m.setAlt_number("8652454913");
//            m.setBirthday(LocalDate.now());
//            m.setEmail("prashantdhuri17@gmail.com");
//////        m.setId_Type(op6);
////        m.setMember_Type(op8);
//            m.setPassword("root");
//            m.setPhoto("C:/Demo/Img.jpg");
////        m.setRoom_id(room);
//            m.setUsername("root");
//
//            Parking p = new Parking();
////        p.setBuilding_id(s);
//            p.setDescription("Mostly in Parking");
//            p.setSlot_Name("R101");
//            p.setStatus(true);
////        p.setSlot_Type(op12);
//
//            Vehicle v = new Vehicle();
//            v.setPerson_id(m);
//            v.setVehical_number("MH-01-AF-4502");
////        v.setVehical_type(op10);
//
////            session.save(p);
//            session.persist(m);
//            Query query = session.createQuery("from Member where name = :n and visibility = 0");
//            query.setParameter("n", "Prashant Dhuri");
//            List<Member> member = query.getResultList();
//            System.out.println(member);
//            session.save(v);
//            ParkingAssign pa = new ParkingAssign();
//            pa.setDescription("Own by Member");
//            System.out.println(LocalDate.now().plusDays(6));
//            pa.setEdate(LocalDate.now().plusDays(6));
//            pa.setSdate(LocalDate.now());
//            pa.setParking_id(p);
//            pa.setVehicle_id(v);
//
////            session.save(pa);
////
//            Committee c = new Committee();
////        c.setDesignation(op14);
//            c.setMember_id(m);
//
//            CommitteeHibernate ch = new CommitteeHibernate();
//            ch.insertCommittee(c);
//            session.getTransaction().commit();
//            session.close();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }
}
