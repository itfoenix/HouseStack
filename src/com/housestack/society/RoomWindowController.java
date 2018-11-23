/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.society;

import com.housestack.database.BuildingHibernate;
import com.housestack.database.OptionHibernate;
import com.housestack.database.RoomHibernate;
import com.housestack.model.Building;
import com.housestack.model.Option;
import com.housestack.model.Room;
import com.housestack.support.ActionButtonType;
import com.housestack.support.ActionCell;
import com.housestack.support.Dialog;
import com.housestack.support.PhoenixSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author choudhary
 */
public class RoomWindowController implements Initializable {

    @FXML
    private StackPane window;

    @FXML
    private JFXTextField txt_RoomName;

    @FXML
    private JFXComboBox<Option> cb_RoomType;

    @FXML
    private JFXButton btn_addType;

    @FXML
    private JFXTextField txt_RoomSize;

    @FXML
    private JFXComboBox<Option> cb_Wing;

    @FXML
    private JFXButton btn_addWing;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXComboBox<Building> cb_Building;

    @FXML
    private JFXComboBox<String> cb_Floor;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXTreeTableView<Room> tblRoom;

    private JFXTreeTableColumn<Room, Integer> tcAction;
    private JFXTreeTableColumn<Room, Integer> tcSrNo;
    private JFXTreeTableColumn<Room, String> tcRoomName;
    private JFXTreeTableColumn<Room, Option> tcRoomType;
    private JFXTreeTableColumn<Room, String> tcRoomSize;
    private JFXTreeTableColumn<Room, Option> tcWing;
    private JFXTreeTableColumn<Room, Building> tcBuilding;
    private JFXTreeTableColumn<Room, String> tcFloor;

    private Room glRoom;
    private ObservableList<Room> roomList;
    private JFXTextField txtName;
    private JFXDialog dialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BuildingHibernate bh = new BuildingHibernate();
        roomList = FXCollections.observableArrayList();
        glRoom = new Room();
        PhoenixSupport.setRequiredField(txt_RoomName, txt_RoomSize);
        PhoenixSupport.setRequiredField(cb_Building, cb_Floor, cb_RoomType, cb_Wing);
        cb_Building.getItems().addAll(bh.getAllBuilding());
        cb_RoomType.getItems().addAll(new OptionHibernate().getOptionOfType(2));
        cb_Wing.getItems().addAll(new OptionHibernate().getOptionOfType(1));
        getAllRooms();
        initTable();
        refreshTable();
        TextFields.bindAutoCompletion(txt_search, roomList);
        cb_Building.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Building> observable, Building oldValue, Building newValue) -> {
            if (newValue != null) {
                cb_Floor.getItems().clear();
                for (int i = 1; i <= newValue.getFloors(); i++) {
                    cb_Floor.getItems().add(i + " Floor");
                }
            }
        });
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            tblRoom.setPredicate(t -> t.getValue().getRoom_name().startsWith(newValue) || t.getValue().getBuilding_id().getBuilding_name().startsWith(newValue));
        });
    }

    /**
     * This method is used to get all the rooms.
     *
     */
    private void getAllRooms() {
        roomList.clear();
        RoomHibernate rh = new RoomHibernate();
        roomList.addAll(rh.getAllRoom());
    }

    /**
     * This method is to add columns in table.
     *
     */
    private void initTable() {
        tcAction = new JFXTreeTableColumn<>();
        tcAction.setCellFactory(param -> {
            ActionCell ac = new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.DELETE_BUTTON) {

                @Override
                public void deleteOperation() {
                    JFXButton btnYes = new JFXButton("Yes");
                    btnYes.getStyleClass().add("btn-save");
                    JFXButton btnNo = new JFXButton("No");
                    btnNo.getStyleClass().add("btn-cancel");
                    JFXDialog dialog = Dialog.getDialog(window, new Label("Delete Information"), new Label("Do you want to delete this Room."), btnYes, btnNo);
                    btnNo.setOnAction(param -> dialog.close());
                    btnYes.setOnAction(param -> {
                        tblRoom.getSelectionModel().select(getTreeTableRow().getIndex());
                        glRoom = tblRoom.getSelectionModel().getSelectedItem().getValue();
                        RoomHibernate roomHibernate = new RoomHibernate();
                        roomHibernate.deleteRoom(glRoom);
                        roomList.remove(glRoom);
                        refreshTable();
                        dialog.close();
                    });
                    dialog.show();
                }

                @Override
                public void editOperation() {
                    tblRoom.getSelectionModel().select(getTreeTableRow().getIndex());
                    glRoom = tblRoom.getSelectionModel().getSelectedItem().getValue();
                    txt_RoomName.setText(glRoom.getRoom_name());
                    txt_RoomSize.setText(glRoom.getRoom_size());
                    cb_Building.getSelectionModel().select(glRoom.getBuilding_id());
                    cb_Floor.getSelectionModel().select(glRoom.getFloor() + " Floor");
                    cb_RoomType.getSelectionModel().select(glRoom.getRoom_type());
                    cb_Wing.getSelectionModel().select(glRoom.getWing());
                    btn_save.setText("Update");
                }

            };
            return ac;
        });
        tcSrNo = new JFXTreeTableColumn<>("Sr. No.");
        tcSrNo.setCellValueFactory(param -> new SimpleIntegerProperty(roomList.indexOf(param.getValue().getValue()) + 1).asObject());
        tcRoomName = new JFXTreeTableColumn<>("Room Name");
        tcRoomName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRoom_name()));
        tcRoomType = new JFXTreeTableColumn<>("Room Type");
        tcRoomType.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getRoom_type()));
        tcRoomSize = new JFXTreeTableColumn<>("Room Size");
        tcRoomSize.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRoom_size()));
        tcBuilding = new JFXTreeTableColumn<>("Building");
        tcBuilding.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getBuilding_id()));
        tcWing = new JFXTreeTableColumn<>("Wing");
        tcWing.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getWing()));
        tcFloor = new JFXTreeTableColumn<>("Floor");
        tcFloor.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFloor() + " Floor"));
        tblRoom.getColumns().addAll(tcAction, tcSrNo, tcRoomName, tcRoomType, tcRoomSize, tcBuilding, tcWing, tcFloor);
    }

    /**
     * This method is used to refresh the table i.e. add value in column
     *
     */
    private void refreshTable() {
        TreeItem<Room> treeItem = new RecursiveTreeItem<>(roomList, RecursiveTreeObject::getChildren);
        tblRoom.setRoot(treeItem);
        tblRoom.setShowRoot(false);
    }

    @FXML
    private void saveType_action(ActionEvent event) {
        addOption("type");
    }

    @FXML
    private void saveType_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addOption("type");
        }
    }

    @FXML
    private void saveWing_action(ActionEvent event) {
        addOption("wing");
    }

    @FXML
    private void saveWing_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addOption("wing");
        }
    }

    /**
     * This method is used to get the Pop-Up to add more options.
     *
     * @param optName This parameter is of String type which is used to differ
     * different types of options.
     */
    private void addOption(String optName) {
        VBox vb = new VBox();
        txtName = new JFXTextField();
        txtName.setLabelFloat(true);
        if (optName.equals("type")) {
            txtName.setPromptText("Room Type");
        } else {
            txtName.setPromptText("Wing");
        }
        vb.getChildren().add(txtName);
        JFXButton btnAdd = new JFXButton("Save");
        btnAdd.getStyleClass().add("btn-save");
        JFXButton btnClose = new JFXButton("Close");
        btnClose.getStyleClass().add("btn-cancel");
        dialog = Dialog.getDialog(window, new Label("Add new Room Type"), vb, btnAdd, btnClose);
        btnAdd.setOnAction(e -> insertoption(optName));
        btnAdd.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                insertoption(optName);
            }
        });
        btnClose.setOnAction(e -> dialog.close());
        btnClose.setOnKeyPressed(e -> {
            dialog.close();
        });
        dialog.show();
    }

    private void insertoption(String optName) {
        Option option = new Option();
        option.setName(txtName.getText());
        if (optName.equals("type")) {
            option.setType(2);
        } else {
            option.setType(1);
        }
        option.setDate(LocalDate.now());
        OptionHibernate oh = new OptionHibernate();
        oh.insertOption(option);
        if (optName.equals("type")) {
            cb_RoomType.getItems().clear();
            cb_RoomType.getItems().addAll(oh.getOptionOfType(2));
        } else {
            cb_Wing.getItems().clear();
            cb_Wing.getItems().addAll(oh.getOptionOfType(1));
        }
        dialog.close();
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

    /**
     * This method is used to insert or update informations of a Room.
     *
     */
    private void save() {
        if (PhoenixSupport.isValidate(txt_RoomName, txt_RoomSize) && PhoenixSupport.isValidate(cb_RoomType, cb_Building, cb_Wing, cb_Floor)) {
            glRoom.setRoom_name(txt_RoomName.getText());
            glRoom.setRoom_size(txt_RoomSize.getText());
            glRoom.setRoom_type(cb_RoomType.getSelectionModel().getSelectedItem());
            glRoom.setBuilding_id(cb_Building.getSelectionModel().getSelectedItem());
            glRoom.setFloor(cb_Floor.getSelectionModel().getSelectedIndex() + 1);
            glRoom.setWing(cb_Wing.getSelectionModel().getSelectedItem());
            RoomHibernate rh = new RoomHibernate();
            rh.insertRoom(glRoom);
            if (btn_save.getText().equals("Update")) {
                Dialog.Success("Updation", "Successfully Updated all Information.", window);
                btn_save.setText("Save");
            } else {
                Dialog.Success("Saving Information", "Successfully saved all the information", window);
            }
            getAllRooms();
            refreshTable();
            clearField();
        } else {
            Dialog.Error("Failed to Save", "Please add all the required Information.", window);
        }
    }

    private void clearField() {
        txt_RoomName.clear();
        txt_RoomSize.clear();
        cb_Building.getSelectionModel().clearSelection();
        cb_Floor.getSelectionModel().clearSelection();
        cb_RoomType.getSelectionModel().clearSelection();
        cb_Wing.getSelectionModel().clearSelection();
    }
}
