/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.member;

import com.housestack.database.DocumentsHibernate;
import com.housestack.database.FamilyMemberHibernate;
import com.housestack.database.MemberHibernate;
import com.housestack.database.OptionHibernate;
import com.housestack.database.ParkingAssignHibernate;
import com.housestack.database.ParkingHibernate;
import com.housestack.database.RoomHibernate;
import com.housestack.database.SocietyHibernate;
import com.housestack.model.Building;
import com.housestack.model.Documents;
import com.housestack.model.FamilyMember;
import com.housestack.model.Member;
import com.housestack.model.Option;
import com.housestack.model.Parking;
import com.housestack.model.ParkingAssign;
import com.housestack.model.Person;
import com.housestack.model.Room;
import com.housestack.model.Society;
import com.housestack.model.Vehicle;
import com.housestack.support.ActionButtonType;
import com.housestack.support.ActionCell;
import com.housestack.support.Dialog;
import com.housestack.support.FileChoosers;
import com.housestack.support.PhoenixSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author choudhary
 */
public class MemberWindowController implements Initializable {

    @FXML
    private StackPane window;

    @FXML
    private JFXTextField txt_MemberName;

    @FXML
    private JFXTextField txt_ContactNumber;

    @FXML
    private JFXTextField txt_AltNumber;

    @FXML
    private JFXTextField txt_Email;

    @FXML
    private JFXTextArea txt_Address;

    @FXML
    private JFXComboBox<Option> cb_Gender;

    @FXML
    private JFXDatePicker dp_Birthday;

    @FXML
    private JFXTextField txt_Username;

    @FXML
    private JFXPasswordField txt_Password;

    @FXML
    private JFXTextField txt_Photo;

    @FXML
    private JFXButton btn_PhotoButton;

    @FXML
    private JFXComboBox<Option> cb_owner;

    @FXML
    private JFXComboBox<Option> cb_IdType;

    @FXML
    private JFXTextField txt_Proof;

    @FXML
    private JFXButton btn_idProof;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXTextField txt_OffNumber;

    @FXML
    private JFXTreeTableView<Member> tblMember;

    private JFXTreeTableColumn<Member, Integer> tcAction;
    private JFXTreeTableColumn<Member, Integer> tcSr;
    private JFXTreeTableColumn<Member, String> tcMemberName;
    private JFXTreeTableColumn<Member, String> tcContactNumber;
    private JFXTreeTableColumn<Member, String> tcEmail;
    private JFXTreeTableColumn<Member, String> tcOffNumber;
    private JFXTreeTableColumn<Member, Room> tcRoom;
    private JFXTreeTableColumn<Member, Option> tcOwner;
    private JFXTreeTableColumn<Member, String> tcUsername;

    JFXComboBox<Building> cb_Building;
    private JFXDialog dialog;
    private JFXTreeTableView<ParkingAssign> tblVehicle;
    private ObservableList<Room> roomList;
    private ParkingAssign glParkingAssign;
    private Member glMember;
    private ObservableList<ParkingAssign> parkingList;
    private File photoFile, proofFile;
    private ObservableList<Member> memberList;
    private JFXTextField txt_flyIdProof;
    private ObservableList<FamilyMember> familyList;
    private ObservableList<Documents> documentList;
    private FamilyMember glFamilyMember;
    private JFXTreeTableView<FamilyMember> tblFamilyMember;
    private Documents documents;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        memberList = FXCollections.observableArrayList();
        parkingList = FXCollections.observableArrayList();
        familyList = FXCollections.observableArrayList();
        documentList = FXCollections.observableArrayList();
        glParkingAssign = new ParkingAssign();
        glMember = new Member();
        roomList = FXCollections.observableArrayList();
        OptionHibernate oh = new OptionHibernate();
        cb_Gender.getItems().addAll(oh.getOptionOfType(3));
        cb_IdType.getItems().addAll(oh.getOptionOfType(4));
        cb_owner.getItems().addAll(oh.getOptionOfType(5));
        PhoenixSupport.setRequiredField(txt_MemberName, txt_ContactNumber, txt_Email, txt_OffNumber, txt_Username, txt_Photo, txt_Proof);
        PhoenixSupport.setRequiredField(txt_Password);
        PhoenixSupport.setRequiredField(cb_Gender, cb_IdType, cb_owner);
        initTable();
        getAllMember();
        refreshTable();
    }

    @FXML
    private void addVehicle_action(ActionEvent event) {
        addVehicle();
    }

    @FXML
    private void addVehicle_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addVehicle();
        }
    }

    @FXML
    private void browse_action(ActionEvent event) {
        if (((JFXButton) event.getSource()).getAccessibleText().equals("uploadPhoto")) {
            photoFile = browse(1);
        } else {
            proofFile = browse(2);
        }
    }

    @FXML
    private void browse_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (((JFXButton) event.getSource()).getAccessibleText().equals("uploadPhoto")) {
                photoFile = browse(1);
            } else {
                proofFile = browse(2);
            }
        }
    }

    @FXML
    private void save_action(ActionEvent event) {
        save();
    }

    @FXML
    private void save_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            save();
        }
    }

    @FXML
    private void cancel_action(ActionEvent event) {
        clearField();
    }

    @FXML
    private void cancel_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clearField();
        }
    }

    @FXML
    private void selectRoom_action(ActionEvent event) {
        selectRoom();
    }

    @FXML
    private void selectRoom_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            selectRoom();
        }
    }

    @FXML
    private void addFamily_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            family();
        }
    }

    @FXML
    private void addFamily_action(ActionEvent event) {
        family();
    }

    private void selectRoom() {
        VBox vb = new VBox();
        vb.setSpacing(16);
        JFXComboBox<Society> cb_Society = new JFXComboBox<>();
        cb_Society.setPromptText("Select Society");
        cb_Building = new JFXComboBox<>();
        cb_Building.setPromptText("Select Building");
        JFXComboBox<Room> cb_Room = new JFXComboBox<>();
        cb_Room.setPromptText("Select Room");
        PhoenixSupport.setRequiredField(cb_Society, cb_Building, cb_Room);
        RoomHibernate rh = new RoomHibernate();
        roomList.addAll(rh.getAllRoom());
        for (Room r : roomList) {
            if (!cb_Society.getItems().contains(r.getBuilding_id().getSociety_id())) {
                cb_Society.getItems().add(r.getBuilding_id().getSociety_id());
            }
        }
        cb_Society.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Society> observable, Society oldValue, Society newValue) -> {
            if (newValue != null) {
                for (Room r : roomList) {
                    if (r.getBuilding_id().getSociety_id().equals(newValue)) {
                        if (cb_Building.getItems().isEmpty() || !cb_Building.getItems().contains(r.getBuilding_id())) {
                            cb_Building.getItems().add(r.getBuilding_id());
                        }
                    }
                }
            }
        });
        cb_Building.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Building> observable, Building oldValue, Building newValue) -> {
            if (newValue != null) {
                for (Room r : roomList) {
                    if (r.getBuilding_id().equals(newValue)) {
                        cb_Room.getItems().add(r);
                    }
                }
            }
        });
        vb.getChildren().addAll(cb_Society, cb_Building, cb_Room);
        JFXButton btnAdd = new JFXButton("Add");
        btnAdd.getStyleClass().add("btn-save");
        JFXButton btnClose = new JFXButton("Close");
        btnClose.getStyleClass().add("btn-cancel");
        dialog = Dialog.getDialog(window, new Label("Select Room"), vb, btnAdd, btnClose);
        dialog.show();
        btnAdd.setOnAction(e -> {
            glMember.setRoom_id(cb_Room.getSelectionModel().getSelectedItem());
            dialog.close();
        });
        btnClose.setOnAction(e -> dialog.close());
        btnClose.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                dialog.close();
            }
        });
    }

    private void addVehicle() {
        parkingList = FXCollections.observableArrayList();
        VBox vb = new VBox();
        vb.setSpacing(16);
        vb.setMinWidth(700);
        HBox hb = new HBox();
        hb.setSpacing(8);
        JFXTextField txt_vehicleNumber = new JFXTextField();
        txt_vehicleNumber.setLabelFloat(true);
        txt_vehicleNumber.setPromptText("Vehicle Number");
        JFXComboBox<Option> cb_VehicleType = new JFXComboBox<>();
        cb_VehicleType.setLabelFloat(true);
        cb_VehicleType.setPromptText("Vehical Type");

//        Parking Slot
        JFXComboBox<Parking> cb_ParkingSlot = new JFXComboBox<>();
        cb_ParkingSlot.setLabelFloat(true);
        cb_ParkingSlot.setPromptText("Parking Slot");

        JFXComboBox<Option> cb_ParkingSlotType = new JFXComboBox<>();
        cb_ParkingSlotType.setLabelFloat(true);
        cb_ParkingSlotType.setPromptText("Parking Slot");

        PhoenixSupport.setRequiredField(txt_vehicleNumber);
        PhoenixSupport.setRequiredField(cb_VehicleType, cb_ParkingSlot, cb_ParkingSlotType);

        JFXButton btnAddVehicle = new JFXButton("Add");
        btnAddVehicle.getStyleClass().add("btn-save");
        btnAddVehicle.setOnAction(e -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehical_number(txt_vehicleNumber.getText());
            vehicle.setVehical_type(cb_VehicleType.getSelectionModel().getSelectedItem());
            ParkingAssign pa = new ParkingAssign();
            pa.setVehicle_id(vehicle);
            pa.setParking_id(cb_ParkingSlot.getSelectionModel().getSelectedItem());
            parkingList.add(pa);
            refreshInnerTable();
        });

        hb.getChildren().addAll(txt_vehicleNumber, cb_VehicleType, cb_ParkingSlotType, cb_ParkingSlot, btnAddVehicle);

        tblVehicle = new JFXTreeTableView<>();
        JFXTreeTableColumn<ParkingAssign, Integer> tcAction = new JFXTreeTableColumn<>();
        tcAction.setCellFactory(param -> new ActionCell(ActionButtonType.DELETE_BUTTON) {

            @Override
            public void deleteOperation() {
                tblVehicle.getSelectionModel().select(getTreeTableRow().getIndex());
                glParkingAssign = tblVehicle.getSelectionModel().getSelectedItem().getValue();
                parkingList.remove(glParkingAssign);
                refreshInnerTable();
            }
        });
        JFXTreeTableColumn<ParkingAssign, Integer> tcSr = new JFXTreeTableColumn<>("Sr No.");
        tcSr.setCellValueFactory(param -> new SimpleIntegerProperty(parkingList.indexOf(param.getValue().getValue()) + 1).asObject());
        JFXTreeTableColumn<ParkingAssign, String> tcVehicleNumber = new JFXTreeTableColumn<>("Vehicle Number");
        tcVehicleNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getVehicle_id().getVehical_number()));
        tcVehicleNumber.setMinWidth(175);
        JFXTreeTableColumn<ParkingAssign, Option> tcVehicleType = new JFXTreeTableColumn<>("Vehicle Type");
        tcVehicleType.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getVehicle_id().getVehical_type()));
        tcVehicleType.setMinWidth(150);
        JFXTreeTableColumn<ParkingAssign, String> tcParkingSlot = new JFXTreeTableColumn<>("Parking Slot");
        tcParkingSlot.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getParking_id()));
        tcParkingSlot.setMinWidth(125);
        tblVehicle.getColumns().addAll(tcAction, tcSr, tcVehicleNumber, tcVehicleType, tcParkingSlot);

        OptionHibernate oh = new OptionHibernate();
        cb_VehicleType.getItems().addAll(oh.getOptionOfType(6));
        cb_ParkingSlotType.getItems().addAll(oh.getOptionOfType(7));
        cb_ParkingSlotType.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Option> observable, Option oldValue, Option newValue) -> {
            if (newValue != null) {
                ParkingHibernate ph = new ParkingHibernate();
                cb_ParkingSlot.getItems().addAll(ph.getAllParkingByType(newValue));
            }
        });

        JFXButton btnAdd = new JFXButton("Add");
        btnAdd.getStyleClass().add("btn-save");
        JFXButton btnClose = new JFXButton("Close");
        btnClose.getStyleClass().add("btn-cancel");
        vb.getChildren().addAll(hb, tblVehicle);

        dialog = Dialog.getDialog(window, new Label("Select Room"), vb, btnAdd, btnClose);
        dialog.show();
        btnAdd.setOnAction(e -> {
            dialog.close();
        });
        btnClose.setOnAction(e -> {
            dialog.close();
            parkingList.clear();
            refreshInnerTable();
        });
        btnClose.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                dialog.close();
                parkingList.clear();
                refreshInnerTable();
            }
        });
    }

    /**
     * This is a main method which is used to add all informations in database.
     *
     */
    public void save() {
        Person p = new Person();
        p.setName(txt_MemberName.getText());
        glMember.setBirthday(dp_Birthday.getValue());
        p.setCont_number(txt_ContactNumber.getText());
        glMember.setAlt_number(txt_AltNumber.getText());
        p.setAddress(txt_Address.getText());
        glMember.setEmail(txt_Email.getText());
        p.setGender(cb_Gender.getSelectionModel().getSelectedItem());
        glMember.setId_Type(cb_IdType.getSelectionModel().getSelectedItem());
        glMember.setMember_Type(cb_owner.getSelectionModel().getSelectedItem());
        glMember.setOff_number(txt_OffNumber.getText());
        glMember.setPassword(txt_Password.getText());
        p.setPer_type("Member");
        glMember.setPerson_id(p);
        if (txt_Photo.getText() != null) {
            if (photoFile != null) {
                File file2 = new File("src\\com\\housestack\\resource\\memberimages\\" + txt_MemberName.getText() + LocalDate.now() + "-" + LocalDateTime.now().getMinute() + "." + FilenameUtils.getExtension(photoFile.getName()));
                try {
                    Files.copy(photoFile.toPath(), file2.toPath());

                } catch (IOException ex) {
                    Logger.getLogger(MemberWindowController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                glMember.setPhoto(file2.getAbsolutePath());
            }
        }

        glMember.setUsername(txt_Username.getText());

        insertProof("Member", p);

//        MemberHibernate mh = new MemberHibernate();
//        mh.insertMember(glMember);
        if (!parkingList.isEmpty()) {
            for (ParkingAssign pa : parkingList) {
                ParkingAssignHibernate pah = new ParkingAssignHibernate();
                pah.insertParking(pa);
            }
        }
        if (!familyList.isEmpty()) {
            FamilyMemberHibernate fmh = new FamilyMemberHibernate();
            for (FamilyMember fm : familyList) {
                fm.setMember(glMember);
                fmh.insertFamilyMember(fm);
            }
        }
        if (!documentList.isEmpty()) {
            DocumentsHibernate dh = new DocumentsHibernate();
            for (Documents d : documentList) {
                dh.insertDocuments(d);
            }
        }
        if (btn_save.getText().equals("Update")) {
            Dialog.Success("Updation", "Successfully Updated all Information.", window);
            btn_save.setText("Save");
        } else {
            Dialog.Success("Saving Information", "Successfully saved all the information", window);
        }
        getAllMember();
        refreshTable();
        clearField();
    }

    /**
     * This method is use to add ObservableList of Parking Vehicle in a table.
     *
     */
    public void refreshInnerTable() {
        TreeItem<ParkingAssign> treeItem = new RecursiveTreeItem<>(parkingList, RecursiveTreeObject::getChildren);
        tblVehicle.setRoot(treeItem);
        tblVehicle.setShowRoot(false);
    }

    /**
     * This method is used to open a Window's Explorer to select file or image.
     * and set the name of that file to particular text field.
     *
     * @param i It is of type Integer. 1 = Member Photo, 2 = Member Id-Proof, 3
     * = Residing Member Id-Proof
     * @return It return the File.
     */
    public File browse(int i) {
        FileChoosers fc = new FileChoosers();
        File file = fc.openFileCooser(window);
        if (i == 1) {
            txt_Photo.setText(file.getName());
        } else if (i == 2) {
            txt_Proof.setText(file.getName());
        } else {
            txt_flyIdProof.setText(file.getName());
        }
        return file;
    }

    /**
     * This method is to to add Columns in Member table.
     */
    public void initTable() {
        tcAction = new JFXTreeTableColumn<>();
        tcAction.setCellFactory(param -> new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.VIEW_BUTTON, ActionButtonType.DELETE_BUTTON) {

            @Override
            public void viewOperation() {
                super.viewOperation(); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void deleteOperation() {
                super.deleteOperation(); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void editOperation() {
                super.editOperation(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        tcContactNumber = new JFXTreeTableColumn<>("Contact Number");
        tcContactNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPerson_id().getCont_number()));
        tcEmail = new JFXTreeTableColumn<>("Email");
        tcEmail.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEmail()));
        tcMemberName = new JFXTreeTableColumn<>("Member Name");
        tcMemberName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPerson_id().getName()));
        tcOffNumber = new JFXTreeTableColumn<>("Office Number");
        tcOffNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getOff_number()));
        tcOwner = new JFXTreeTableColumn<>("Owner Type");
        tcOwner.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getMember_Type()));
        tcUsername = new JFXTreeTableColumn<>("Username");
        tcUsername.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getUsername()));
        tcRoom = new JFXTreeTableColumn<>("Room");
        tcRoom.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getRoom_id()));
        tcSr = new JFXTreeTableColumn<>("Sr. No.");
        tcSr.setCellValueFactory(param -> new SimpleIntegerProperty(memberList.indexOf(param.getValue().getValue()) + 1).asObject());
        tblMember.getColumns().addAll(tcAction, tcSr, tcMemberName, tcContactNumber, tcEmail, tcOwner, tcRoom, tcOffNumber);
    }

    /**
     * This method is used to get All the members created members.
     */
    public void getAllMember() {
        memberList.clear();
        MemberHibernate mh = new MemberHibernate();
        memberList.addAll(mh.getAllMembers());
    }

    /**
     * This method is used to add ObeservableList of members in table
     */
    public void refreshTable() {
        TreeItem<Member> treeItem = new RecursiveTreeItem<>(memberList, RecursiveTreeObject::getChildren);
        tblMember.setRoot(treeItem);
        tblMember.setShowRoot(false);
    }

    /**
     * This method is used to clearAllFields
     */
    public void clearField() {
        txt_Address.clear();
        txt_AltNumber.clear();
        txt_ContactNumber.clear();
        txt_Email.clear();
        txt_MemberName.clear();
        txt_OffNumber.clear();
        txt_Password.clear();
        txt_Photo.clear();
        txt_Proof.clear();
        txt_Username.clear();
        cb_Building.getSelectionModel().clearSelection();
        cb_Gender.getSelectionModel().clearSelection();
        cb_IdType.getSelectionModel().clearSelection();
        cb_owner.getSelectionModel().clearSelection();
        dp_Birthday.setValue(null);
        familyList.clear();
        refreshFlyTable();
    }

    /**
     * This Method is called when Add Family Member Button is clicked.. It gives
     * the pop-up to add family Members.
     */
    public void family() {
        glFamilyMember = new FamilyMember();
        VBox vb = new VBox();
        vb.setSpacing(16);
        vb.setMinWidth(700);
        HBox hb1 = new HBox();
        hb1.setSpacing(8);
        HBox hb2 = new HBox();
        hb2.setSpacing(8);
        JFXTextField txt_flyName = new JFXTextField();
        txt_flyName.setMinWidth(150);
        txt_flyName.setPromptText("Name");
        JFXTextField txt_flyContact = new JFXTextField();
        txt_flyContact.setMinWidth(150);
        txt_flyContact.setPromptText("Contact");

        JFXComboBox<Option> cb_flyGender = new JFXComboBox();
        cb_flyGender.setMinWidth(150);
        OptionHibernate oh = new OptionHibernate();
        cb_flyGender.getItems().addAll(oh.getOptionOfType(3));
        cb_flyGender.setPromptText("Select Gender");

        JFXComboBox<Option> cb_flyRelation = new JFXComboBox();
        cb_flyRelation.setMinWidth(150);
        cb_flyRelation.getItems().addAll(oh.getOptionOfType(10));
        cb_flyRelation.getSelectionModel().selectLast();
        cb_flyRelation.setPromptText("Select Relation");

        JFXComboBox<Option> cb_flyIdProof = new JFXComboBox();
        cb_flyIdProof.setMinWidth(150);
        cb_flyIdProof.getItems().addAll(oh.getOptionOfType(4));
        cb_flyIdProof.setPromptText("Select Id-Proof");

        txt_flyIdProof = new JFXTextField();
        txt_flyIdProof.setMinWidth(150);
        txt_flyIdProof.setPromptText("Id Proof");
        PhoenixSupport.setRequiredField(txt_flyName);
        PhoenixSupport.setRequiredField(cb_flyRelation);

        JFXButton btn_flyBrowse = new JFXButton("Browse");
        btn_flyBrowse.getStyleClass().add("btn-save");
        btn_flyBrowse.setOnAction(e -> {
            proofFile = browse(3);
        });
        btn_flyBrowse.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                proofFile = browse(3);
            }
        });

//        ADD to table
        JFXButton btn_flyAdd = new JFXButton("Add");
        btn_flyAdd.getStyleClass().add("btn-save");
        btn_flyAdd.setOnAction(e -> {
            addFlyInList(cb_flyIdProof, txt_flyName, txt_flyContact, cb_flyGender, cb_flyRelation, btn_flyAdd);
        });
        btn_flyAdd.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                addFlyInList(cb_flyIdProof, txt_flyName, txt_flyContact, cb_flyGender, cb_flyRelation, btn_flyAdd);
            }
        });
        hb1.getChildren().addAll(txt_flyName, txt_flyContact, cb_flyGender, cb_flyRelation);
        hb2.getChildren().addAll(cb_flyIdProof, txt_flyIdProof, btn_flyBrowse, btn_flyAdd);

        tblFamilyMember = new JFXTreeTableView();
        JFXTreeTableColumn<FamilyMember, Integer> tcflyAction = new JFXTreeTableColumn<>();
        tcflyAction.setCellFactory(param -> new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.DELETE_BUTTON) {

            @Override
            public void deleteOperation() {
                tblFamilyMember.getSelectionModel().select(getTreeTableRow().getIndex());
                FamilyMember familyMember = tblFamilyMember.getSelectionModel().getSelectedItem().getValue();
                familyList.remove(familyMember);
                refreshFlyTable();
            }

            @Override
            public void editOperation() {
                tblFamilyMember.getSelectionModel().select(getTreeTableRow().getIndex());
                glFamilyMember = tblFamilyMember.getSelectionModel().getSelectedItem().getValue();
                txt_flyName.setText(glFamilyMember.getPerson().getName());
                txt_flyContact.setText(glFamilyMember.getPerson().getCont_number());
                cb_flyGender.getSelectionModel().select(glFamilyMember.getPerson().getGender());
                cb_flyRelation.getSelectionModel().select(glFamilyMember.getRelationtoMember());
                cb_flyIdProof.getSelectionModel().select(glFamilyMember.getIdProof());
                btn_flyAdd.setText("Update");
            }

        });
        JFXTreeTableColumn<FamilyMember, Integer> tcflySr = new JFXTreeTableColumn<>("Sr. No.");
        tcflySr.setCellValueFactory(param -> new SimpleIntegerProperty(familyList.indexOf(param.getValue().getValue()) + 1).asObject());
        JFXTreeTableColumn<FamilyMember, String> tcflyName = new JFXTreeTableColumn<>("Name");
        tcflyName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPerson().getName()));
        JFXTreeTableColumn<FamilyMember, String> tcflyContact = new JFXTreeTableColumn<>("Contact");
        tcflyContact.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPerson().getCont_number()));
        JFXTreeTableColumn<FamilyMember, Option> tcflyGender = new JFXTreeTableColumn<>("Gender");
        tcflyGender.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getPerson().getGender()));
        JFXTreeTableColumn<FamilyMember, Option> tcflyIdProof = new JFXTreeTableColumn<>("Id Proof");
        tcflyIdProof.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getIdProof()));
        JFXTreeTableColumn<FamilyMember, Option> tcflyRelation = new JFXTreeTableColumn<>("Relation");
        tcflyRelation.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getRelationtoMember()));
        tblFamilyMember.getColumns().addAll(tcflyAction, tcflySr, tcflyName, tcflyContact, tcflyGender, tcflyIdProof, tcflyRelation);

        refreshFlyTable();

        vb.getChildren().addAll(hb1, hb2, tblFamilyMember);
        JFXButton btn_flySave = new JFXButton("Save");
        btn_flySave.getStyleClass().add("btn-save");
        JFXButton btn_flyCancel = new JFXButton("Cancel");
        btn_flyCancel.getStyleClass().add("btn-cancel");
        dialog = Dialog.getDialog(window, new Label("Add Residing Member's"), vb, btn_flySave, btn_flyCancel);
        dialog.show();
        btn_flySave.setOnAction(e -> dialog.close());
        btn_flySave.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                dialog.close();
            }
        });
        btn_flyCancel.setOnAction(e -> {
            dialog.close();
            familyList.clear();
            refreshFlyTable();
        });
        btn_flyCancel.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                dialog.close();
                familyList.clear();
                refreshFlyTable();
            }
        });
    }

    public void addFlyInList(JFXComboBox<Option> cb_flyIdProof, JFXTextField txt_flyName, JFXTextField txt_flyContact, JFXComboBox<Option> cb_flyGender, JFXComboBox<Option> cb_flyRelation, JFXButton btn_flyAdd) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setIdProof(cb_flyIdProof.getSelectionModel().getSelectedItem());
        Person person = new Person();
        person.setName(txt_flyName.getText());
        person.setCont_number(txt_flyContact.getText());
        person.setGender(cb_flyGender.getSelectionModel().getSelectedItem());
        person.setPer_type("Residing Member");
        familyMember.setPerson(person);
        familyMember.setRelationtoMember(cb_flyRelation.getSelectionModel().getSelectedItem());

        insertProof("Residing Member", person);

        if (btn_flyAdd.getText().equals("Add")) {
            familyList.add(familyMember);
        } else {
            familyList.set(familyList.indexOf(glFamilyMember), familyMember);
            btn_flyAdd.setText("Add");
        }
    }

    /**
     * This method is to add ObservableList of Residing Members in Table
     *
     */
    public void refreshFlyTable() {
        TreeItem<FamilyMember> treeItem = new RecursiveTreeItem<>(familyList, RecursiveTreeObject::getChildren);
        tblFamilyMember.setRoot(treeItem);
        tblFamilyMember.setShowRoot(false);
    }

    public void insertProof(String s, Person person) {
        if (txt_Proof.getText() != null) {
            documents = new Documents();
            if (proofFile != null) {
                File file2 = new File("src\\com\\housestack\\resource\\document\\" + PhoenixSupport.randomAlphaNumeric(16) + "." + FilenameUtils.getExtension(proofFile.getName()));
                try {
                    Files.copy(proofFile.toPath(), file2.toPath());
                } catch (IOException ex) {
                    Logger.getLogger(MemberWindowController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                documents.setLocation(file2.getAbsolutePath());
                documents.setDescription("It is " + person.getName() + " Id Proof");
                documents.setUpload_date(LocalDate.now());
                documents.setOwner_type(s);
                documents.setPerson(person);
            }
            documentList.add(documents);
        }
    }
}
