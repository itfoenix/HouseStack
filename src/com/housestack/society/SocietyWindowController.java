/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.society;

import com.housestack.database.OptionHibernate;
import com.housestack.database.SocietyHibernate;
import com.housestack.model.Option;
import com.housestack.model.Society;
import com.housestack.support.ActionButtonType;
import com.housestack.support.ActionCell;
import com.housestack.support.Dialog;
import com.housestack.support.PhoenixSupport;
import com.housestack.support.Support;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author choudhary
 */
public class SocietyWindowController implements Initializable {

    @FXML
    private JFXTextField txt_SocietyName;

    @FXML
    private JFXTextField txt_ContactNumber;

    @FXML
    private JFXTextField txt_Area;

    @FXML
    private JFXTextField txt_City;

    @FXML
    private JFXTextField txt_State;

    @FXML
    private JFXTextField txt_Country;

    @FXML
    private JFXTextField txt_ZipCode;

    @FXML
    private JFXComboBox<Option> cb_SocietyType;

    @FXML
    private JFXTextField txt_Year;

    @FXML
    private JFXTextField txt_Founder;

    @FXML
    private JFXTextField txt_Builder;

    @FXML
    private JFXTextField txt_RegistrationNumber;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXTreeTableView<Society> tblSociety;

    @FXML
    private JFXButton btn_save;

    @FXML
    private StackPane window;

    private JFXTreeTableColumn<Society, Integer> tcSrNo;
    private JFXTreeTableColumn<Society, String> tcSocietyName;
    private JFXTreeTableColumn<Society, String> tcAddress;
    private JFXTreeTableColumn<Society, String> tcContactNumber;
    private JFXTreeTableColumn<Society, String> tcRegiNumber;
    private JFXTreeTableColumn<Society, String> tcCity;
    private JFXTreeTableColumn<Society, String> tcZipCode;
    private JFXTreeTableColumn<Society, String> tcType;
    private JFXTreeTableColumn<Society, Integer> tcAction;

    private ObservableList<Society> societyList;
    private ObservableList<Option> optionList;
    private JFXTextField txtName;
    private JFXDialog dialog;
    private int id;
    private Society glSociety;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        societyList = FXCollections.observableArrayList();
        optionList = FXCollections.observableArrayList();
        glSociety = new Society();
        getOptions();
        getSociety();
        PhoenixSupport.setMoblieValidator(txt_ContactNumber);
        PhoenixSupport.setRequiredField(txt_SocietyName, txt_ContactNumber, txt_RegistrationNumber, txt_Area);
        PhoenixSupport.setRequiredField(cb_SocietyType);
        initTable();
        refreshTable();
        TextFields.bindAutoCompletion(txt_search, societyList);
        txt_search.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tblSociety.setPredicate(t -> t.getValue().getSociety_name().startsWith(newValue));
            }
        });
    }

    public void getOptions() {
        optionList.clear();
        OptionHibernate optionHibernate = new OptionHibernate();
        optionList.addAll(optionHibernate.getOptionOfType(9));
        cb_SocietyType.setItems(optionList);
    }

    public void getSociety() {
        societyList.clear();
        SocietyHibernate societyHibernate = new SocietyHibernate();
        societyList.addAll(societyHibernate.getAllSociety());
    }

    public void initTable() {
        tcSrNo = new JFXTreeTableColumn<>("Sr No.");
        tcSrNo.setCellValueFactory(param -> new SimpleIntegerProperty(societyList.indexOf(param.getValue().getValue()) + 1).asObject());
        tcSrNo.setPrefWidth(80);
        tcSocietyName = new JFXTreeTableColumn<>("Society Name");
        tcSocietyName.setPrefWidth(200);
        tcSocietyName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSociety_name()));
        tcAddress = new JFXTreeTableColumn<>("Society Address");
        tcAddress.setPrefWidth(250);
        tcAddress.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getArea()));
        tcContactNumber = new JFXTreeTableColumn<>("Contact Number");
        tcContactNumber.setPrefWidth(100);
        tcContactNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getCont_number()));
        tcRegiNumber = new JFXTreeTableColumn<>("Registration Number");
        tcRegiNumber.setPrefWidth(150);
        tcRegiNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRegi_number()));
        tcCity = new JFXTreeTableColumn<>("City");
        tcCity.setPrefWidth(100);
        tcCity.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getCity()));
        tcZipCode = new JFXTreeTableColumn<>("ZipCode");
        tcZipCode.setPrefWidth(100);
        tcZipCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getZip_Code()));
        tcType = new JFXTreeTableColumn<>("Society Type");
        tcType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getType().getName()));
        tcType.setPrefWidth(100);
        tcAction = new JFXTreeTableColumn<>();
        tcAction.setCellFactory(param -> {
            ActionCell action = new ActionCell(ActionButtonType.EDIT_BUTTON, ActionButtonType.DELETE_BUTTON) {

                @Override
                public void deleteOperation() {
                    JFXButton btnYes = new JFXButton("Yes");
                    btnYes.getStyleClass().add("btn-save");
                    JFXButton btnNo = new JFXButton("No");
                    btnNo.getStyleClass().add("btn-cancel");
                    JFXDialog dialog = Dialog.getDialog(window, new Label("Delete Information"), new Label("Do you want to delete this Society."), btnYes, btnNo);
                    btnNo.setOnAction(param -> dialog.close());
                    btnYes.setOnAction(param -> {
                        tblSociety.getSelectionModel().select(getTreeTableRow().getIndex());
                        Society society = tblSociety.getSelectionModel().getSelectedItem().getValue();
                        SocietyHibernate societyHibernate = new SocietyHibernate();
                        societyHibernate.deleteSociety(society);
                        societyList.remove(society);
                        refreshTable();
                        dialog.close();
                    });
                    dialog.show();
                }

                @Override
                public void editOperation() {
                    tblSociety.getSelectionModel().select(getTreeTableRow().getIndex());
                    glSociety = tblSociety.getSelectionModel().getSelectedItem().getValue();
                    txt_Area.setText(glSociety.getArea());
                    txt_Builder.setText(glSociety.getBuilder());
                    txt_City.setText(glSociety.getCity());
                    txt_ContactNumber.setText(glSociety.getCont_number());
                    txt_Country.setText(glSociety.getCountry());
                    txt_Founder.setText(glSociety.getFounder());
                    txt_RegistrationNumber.setText(glSociety.getRegi_number());
                    txt_SocietyName.setText(glSociety.getSociety_name());
                    txt_State.setText(glSociety.getStates());
                    txt_Year.setText(glSociety.getCreation_year());
                    txt_ZipCode.setText(glSociety.getZip_Code());
                    cb_SocietyType.getSelectionModel().select(glSociety.getType());
                    id = glSociety.getSociety_id();
                    btn_save.setText("Update");
                }
            };
            return action;
        });
        tblSociety.getColumns().addAll(tcAction, tcSrNo, tcSocietyName, tcContactNumber, tcAddress, tcCity, tcZipCode, tcRegiNumber, tcType);
    }

    public void refreshTable() {
        TreeItem<Society> treeItem = new RecursiveTreeItem<>(societyList, RecursiveTreeObject::getChildren);
        tblSociety.setRoot(treeItem);
        tblSociety.setShowRoot(false);
    }

    @FXML
    public void save_action(ActionEvent event) {
        save();
        refreshTable();
    }

    @FXML
    public void save_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            save();
            refreshTable();
        }
    }

    public void save() {
        if (PhoenixSupport.isValidate(txt_SocietyName, txt_ContactNumber, txt_Area, txt_RegistrationNumber) && PhoenixSupport.isValidate(cb_SocietyType)) {
            Society society = new Society();
            society.setSociety_name(txt_SocietyName.getText());
            society.setCont_number(txt_ContactNumber.getText());
            society.setArea(txt_Area.getText());
            society.setCity(txt_City.getText());
            society.setStates(txt_State.getText());
            society.setBuilder(txt_Builder.getText());
            society.setCountry(txt_Country.getText());
            society.setCreation_year(txt_Year.getText());
            society.setFounder(txt_Founder.getText());
            society.setRegi_number(txt_RegistrationNumber.getText());
            society.setType(cb_SocietyType.getSelectionModel().getSelectedItem());
            society.setZip_Code(txt_ZipCode.getText());
            SocietyHibernate societyHibernate = new SocietyHibernate();
            if (btn_save.getText().equals("Update")) {
                society.setSociety_id(glSociety.getSociety_id());
            }
            societyHibernate.insertSociety(society);
            if (btn_save.getText().equals("Update")) {
                Dialog.Success("Updation", "All Informations are Successfully updated.", window);
                btn_save.setText("Save");
            } else {
                Dialog.Success("Insertion", "All Informations are Successfully Inserted", window);
            }
            societyList.clear();
            clearFields();
            getSociety();
//            }
        } else {
            Dialog.Error("Failed to Save", "Please add all the required Information.", window);
        }
    }

    @FXML
    public void addType_action(ActionEvent event) {
        addType();
    }

    @FXML
    public void addType_key(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addType();
        }
    }

    public void addType() {
        VBox vb = new VBox();
        txtName = new JFXTextField();
        txtName.setLabelFloat(true);
        txtName.setPromptText("Society Type");
        vb.getChildren().add(txtName);
        JFXButton btnSave = new JFXButton("Save");
        btnSave.setOnAction(e -> saveOption());
        btnSave.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                saveOption();
            }
        });
        JFXButton btnClose = new JFXButton("Close");
        btnClose.setOnAction(e -> closeOption());
        btnClose.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                closeOption();
            }
        });
        dialog = Support.getDialog(window, new Label("Add New Option"), vb, btnSave, btnClose);
        dialog.show();
    }

    public void saveOption() {
        if (!txtName.getText().isEmpty()) {
            Option option = new Option();
            option.setName(txtName.getText());
            option.setType(9);
            option.setDate(LocalDate.now());
            OptionHibernate optionHibernate = new OptionHibernate();
            optionHibernate.insertOption(option);
            getOptions();
            dialog.close();
        }
    }

    public void closeOption() {
        dialog.close();
    }

    public void clearFields() {
        txt_Area.clear();
        txt_Builder.clear();
        txt_City.clear();
        txt_ContactNumber.clear();
        txt_Country.clear();
        txt_Founder.clear();
        txt_RegistrationNumber.clear();
        txt_SocietyName.clear();
        txt_State.clear();
        txt_Year.clear();
        txt_ZipCode.clear();
        cb_SocietyType.getSelectionModel().clearSelection();
        txt_SocietyName.resetValidation();
        txt_Area.resetValidation();
        txt_RegistrationNumber.resetValidation();
        cb_SocietyType.resetValidation();
    }
}
