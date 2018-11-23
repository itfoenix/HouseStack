/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.society;

import com.housestack.database.BuildingHibernate;
import com.housestack.database.SocietyHibernate;
import com.housestack.model.Building;
import com.housestack.model.Society;
import com.housestack.support.ActionButtonType;
import com.housestack.support.ActionCell;
import com.housestack.support.Dialog;
import com.housestack.support.PhoenixSupport;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author choudhary
 */
public class BuildingWindowController implements Initializable {

    @FXML
    private StackPane window;

    @FXML
    private JFXTextField txt_BuildingName;

    @FXML
    private JFXTextField txt_BuildingNumber;

    @FXML
    private JFXTextField txt_Floors;

    @FXML
    private JFXTextField txt_Rooms;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXComboBox<Society> cb_Society;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXTreeTableView<Building> tblBuilding;

    private JFXTreeTableColumn<Building, Integer> tc_action;
    private JFXTreeTableColumn<Building, Integer> tc_srno;
    private JFXTreeTableColumn<Building, String> tc_buildingname;
    private JFXTreeTableColumn<Building, String> tc_buildingnumber;
    private JFXTreeTableColumn<Building, Integer> tc_floors;
    private JFXTreeTableColumn<Building, Integer> tc_rooms;
    private JFXTreeTableColumn<Building, Society> tc_society;

    private ObservableList<Building> buildingList;
    /**
     * glBuilding is a global variable for Building. gl stands for Global.
     *
     */
    private Building glBuilding;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buildingList = FXCollections.observableArrayList();
        glBuilding = new Building();
        PhoenixSupport.setRequiredField(txt_BuildingName, txt_Floors, txt_BuildingNumber);
        PhoenixSupport.setRequiredField(cb_Society);
        SocietyHibernate sh = new SocietyHibernate();
        cb_Society.getItems().addAll(sh.getAllSociety());
        getAllBuildings();
        initTable();
        refreshTable();
        TextFields.bindAutoCompletion(txt_search, buildingList);
        txt_search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblBuilding.setPredicate(t -> t.getValue().getBuilding_name().startsWith(newValue));
        });
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
     * This method is used to initialize table.
     *
     */
    private void initTable() {
        tc_action = new JFXTreeTableColumn<>();
        tc_action.setCellFactory(param -> {
            ActionCell ac = new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.DELETE_BUTTON) {

                @Override
                public void deleteOperation() {
                    JFXButton btnYes = new JFXButton("Yes");
                    btnYes.getStyleClass().add("btn-save");
                    JFXButton btnNo = new JFXButton("No");
                    btnNo.getStyleClass().add("btn-cancel");
                    JFXDialog dialog = Dialog.getDialog(window, new Label("Delete Information"), new Label("Do you want to delete this Building."), btnYes, btnNo);
                    btnNo.setOnAction(param -> dialog.close());
                    btnYes.setOnAction(param -> {
                        tblBuilding.getSelectionModel().select(getTreeTableRow().getIndex());
                        glBuilding = tblBuilding.getSelectionModel().getSelectedItem().getValue();
                        BuildingHibernate bh = new BuildingHibernate();
                        bh.deleteBuilding(glBuilding);
                        getAllBuildings();
                        refreshTable();
                        Dialog.Success("Deletion", "Successfully Deleted the Information.", window);
                        dialog.close();
                    });
                    dialog.show();
                }

                @Override
                public void editOperation() {
                    tblBuilding.getSelectionModel().select(getTreeTableRow().getIndex());
                    glBuilding = tblBuilding.getSelectionModel().getSelectedItem().getValue();
                    btn_save.setText("Update");
                    txt_BuildingName.setText(glBuilding.getBuilding_name());
                    txt_BuildingNumber.setText(glBuilding.getBuilding_num());
                    txt_Floors.setText(String.valueOf(glBuilding.getFloors()));
                    txt_Rooms.setText(String.valueOf(glBuilding.getRooms()));
                    cb_Society.getSelectionModel().select(glBuilding.getSociety_id());
                }
            };
            return ac;
        });
        tc_srno = new JFXTreeTableColumn<>("Sr. No.");
        tc_srno.setCellValueFactory(param -> new SimpleIntegerProperty(buildingList.indexOf(param.getValue().getValue()) + 1).asObject());
        tc_buildingname = new JFXTreeTableColumn<>("Building Name");
        tc_buildingname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getBuilding_name()));
        tc_buildingnumber = new JFXTreeTableColumn<>("Building Number");
        tc_buildingnumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getBuilding_num()));
        tc_floors = new JFXTreeTableColumn<>("Total Floors");
        tc_floors.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getFloors()).asObject());
        tc_rooms = new JFXTreeTableColumn<>("Total Rooms");
        tc_rooms.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getRooms()).asObject());
        tc_society = new JFXTreeTableColumn<>("Society Name");
        tc_society.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getValue().getSociety_id()));
        tblBuilding.getColumns().addAll(tc_action, tc_srno, tc_buildingname, tc_buildingnumber, tc_floors, tc_rooms, tc_society);
    }

    /**
     * This method is used to save all the information or update any
     * information.
     *
     */
    private void save() {
        if (PhoenixSupport.isValidate(txt_BuildingName, txt_Floors, txt_Rooms) && PhoenixSupport.isValidate(cb_Society)) {
            glBuilding.setBuilding_name(txt_BuildingName.getText());
            glBuilding.setBuilding_num(txt_BuildingNumber.getText());
            glBuilding.setFloors(Integer.parseInt(txt_Floors.getText()));
            glBuilding.setRooms(Integer.parseInt(txt_Rooms.getText()));
            glBuilding.setSociety_id(cb_Society.getSelectionModel().getSelectedItem());
            BuildingHibernate bh = new BuildingHibernate();
            bh.insertBuilding(glBuilding);
            if (btn_save.getText().equals("Update")) {
                Dialog.Success("Updation", "Successfully Updated all Information.", window);
                btn_save.setText("Save");
            } else {
                Dialog.Success("Saving Information", "Successfully saved all the information", window);
            }
            clearFields();
            getAllBuildings();
            refreshTable();
        } else {
            Dialog.Error("Failed to Save", "Please add all the required Information.", window);
        }
    }

    /**
     * This method is used to clear all the data entered in fields.
     *
     */
    private void clearFields() {
        txt_BuildingName.clear();
        txt_BuildingNumber.clear();
        txt_Floors.clear();
        txt_Rooms.clear();
        cb_Society.getSelectionModel().clearSelection();
    }

    /**
     * This is to get list of all Buildings.
     *
     */
    private void getAllBuildings() {
        buildingList.clear();
        BuildingHibernate bh = new BuildingHibernate();
        buildingList.addAll(bh.getAllBuilding());
    }

    /**
     * This method is used to refresh Table.
     *
     */
    public void refreshTable() {
        TreeItem<Building> treeItem = new RecursiveTreeItem<>(buildingList, RecursiveTreeObject::getChildren);
        tblBuilding.setRoot(treeItem);
        tblBuilding.setShowRoot(false);
    }
}
