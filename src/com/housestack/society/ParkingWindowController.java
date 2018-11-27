/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.society;

import com.housestack.database.BuildingHibernate;
import com.housestack.database.OptionHibernate;
import com.housestack.database.ParkingHibernate;
import com.housestack.model.Building;
import com.housestack.model.Option;
import com.housestack.model.Parking;
import com.housestack.model.Society;
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author choudhary
 */
public class ParkingWindowController implements Initializable {

    @FXML
    private StackPane window;

    @FXML
    private JFXTextField txt_SlotName;

    @FXML
    private JFXComboBox<Option> cb_SlotType;

    @FXML
    private JFXComboBox<Society> cb_Society;

    @FXML
    private JFXComboBox<Building> cb_Building;

    @FXML
    private JFXTextField txt_Description;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXTreeTableView<Parking> tblParking;

    private JFXTreeTableColumn<Parking, Integer> tcAction;
    private JFXTreeTableColumn<Parking, Integer> tcSr;
    private JFXTreeTableColumn<Parking, String> tcSlotName;
    private JFXTreeTableColumn<Parking, Option> tcSlotType;
    private JFXTreeTableColumn<Parking, Building> tcBuilding;
    private JFXTreeTableColumn<Parking, String> tcDescription;
    private JFXTreeTableColumn<Parking, String> tcStatus;

    private ObservableList<Building> buildingList;
    private ObservableList<Parking> parkingList;
    private Parking glParking;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buildingList = FXCollections.observableArrayList();
        parkingList = FXCollections.observableArrayList();
        glParking = new Parking();
        OptionHibernate oh = new OptionHibernate();
        cb_SlotType.getItems().addAll(oh.getOptionOfType(7));
        BuildingHibernate bh = new BuildingHibernate();
        buildingList.addAll(bh.getAllBuilding());
        for (Building b : buildingList) {
            if (cb_Society.getItems().isEmpty() || !cb_Society.getItems().contains(b)) {
                cb_Society.getItems().add(b.getSociety_id());
            }
        }
        cb_Society.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Society> observable, Society oldValue, Society newValue) -> {
            if (newValue != null) {
                for (Building b : buildingList) {
                    if (b.getSociety_id().equals(newValue)) {
                        cb_Building.getItems().add(b);
                    }
                }
            }
        });
        initTable();
        getAllParking();
        TextFields.bindAutoCompletion(txt_search, parkingList);
        PhoenixSupport.setRequiredField(txt_SlotName);
        PhoenixSupport.setRequiredField(cb_SlotType, cb_Society, cb_Building);
        refreshTable();
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

    private void save() {
        if (PhoenixSupport.isValidate(txt_SlotName) && PhoenixSupport.isValidate(cb_SlotType, cb_Society, cb_Building)) {
            Parking parking = new Parking();
            if (btn_save.getText().equals("Update")) {
                parking = glParking;
            }
            parking.setSlot_Name(txt_SlotName.getText());
            parking.setSlot_Type(cb_SlotType.getSelectionModel().getSelectedItem());
            parking.setDescription(txt_Description.getText());
            parking.setStatus(false);
            parking.setBuilding_id(cb_Building.getSelectionModel().getSelectedItem());
            ParkingHibernate ph = new ParkingHibernate();
            ph.insertParking(parking);
            if (btn_save.getText().equals("Update")) {
                Dialog.Success("Updation", "Successfully Updated all Information.", window);
                btn_save.setText("Save");
            } else {
                Dialog.Success("Saving Information", "Successfully saved all the information", window);
            }
            getAllParking();
            refreshTable();
            clearFields();
        } else {
            Dialog.Error("Failed to Save", "Please add all the required Information.", window);
        }
    }

    private void initTable() {
        tcAction = new JFXTreeTableColumn<>();
        tcAction.setCellFactory(param -> new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.DELETE_BUTTON) {

            @Override
            public void deleteOperation() {
                JFXButton btnYes = new JFXButton("Yes");
                btnYes.getStyleClass().add("btn-save");
                JFXButton btnNo = new JFXButton("No");
                btnNo.getStyleClass().add("btn-cancel");
                JFXDialog dialog = Dialog.getDialog(window, new Label("Delete Information"), new Label("Do you want to delete this Parking."), btnYes, btnNo);
                btnNo.setOnAction(param -> dialog.close());
                btnYes.setOnAction(param -> {
                    tblParking.getSelectionModel().select(getTreeTableRow().getIndex());
                    glParking = tblParking.getSelectionModel().getSelectedItem().getValue();
                    ParkingHibernate parkingHibernate = new ParkingHibernate();
                    parkingHibernate.deleteParking(glParking);
                    parkingList.remove(glParking);
                    refreshTable();
                    dialog.close();
                });
                dialog.show();
            }

            @Override
            public void editOperation() {
                tblParking.getSelectionModel().select(getTreeTableRow().getIndex());
                glParking = tblParking.getSelectionModel().getSelectedItem().getValue();
                txt_SlotName.setText(glParking.getSlot_Name());
                cb_SlotType.getSelectionModel().select(glParking.getSlot_Type());
                cb_Society.getSelectionModel().select(glParking.getBuilding_id().getSociety_id());
                cb_Building.getSelectionModel().select(glParking.getBuilding_id());
                txt_Description.setText(glParking.getDescription());
                btn_save.setText("Update");
            }

        });
        tcSr = new JFXTreeTableColumn<>("Sr. No.");
        tcSr.setCellValueFactory(param -> new SimpleIntegerProperty(parkingList.indexOf(param.getValue().getValue()) + 1).asObject());
        tcSlotName = new JFXTreeTableColumn<>("Slot Name");
        tcSlotName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSlot_Name()));
        tcSlotType = new JFXTreeTableColumn<>("Slot Type");
        tcSlotType.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getSlot_Type()));
        tcBuilding = new JFXTreeTableColumn<>("Building");
        tcBuilding.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getBuilding_id()));
        tcDescription = new JFXTreeTableColumn<>("Description");
        tcDescription.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getDescription()));
        tcStatus = new JFXTreeTableColumn<>("Status");
        tcStatus.setCellValueFactory(param -> {
            if (param.getValue().getValue().getStatus() == false) {
                return new SimpleStringProperty("Not Allocated");
            } else {
                return new SimpleStringProperty("Allocated");
            }
        });
        tblParking.getColumns().addAll(tcAction, tcSr, tcSlotName, tcSlotType, tcBuilding, tcDescription, tcStatus);
    }

    private void getAllParking() {
        if (!parkingList.isEmpty()) {
            parkingList.clear();
        }
        ParkingHibernate ph = new ParkingHibernate();
        parkingList.addAll(ph.getAllParking());
    }

    private void refreshTable() {
        TreeItem<Parking> treeItem = new RecursiveTreeItem<>(parkingList, RecursiveTreeObject::getChildren);
        tblParking.setRoot(treeItem);
        tblParking.setShowRoot(false);
    }

    private void clearFields() {
        txt_SlotName.clear();
        txt_Description.clear();
        cb_SlotType.getSelectionModel().clearSelection();
        cb_Building.getSelectionModel().clearSelection();
        cb_Society.getSelectionModel().clearSelection();
    }
}
