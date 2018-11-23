/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.support;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ActionCell<S extends Object, T extends Object> extends JFXTreeTableCell<S, T> {

    private final HBox actionBar;
    private ImageView view;
    private ImageView edit;
    private ImageView delete;
    private double FitWidth, FitHeight;

    public ActionCell(ActionButtonType... buttonType) {
        this.FitWidth = 24;
        this.FitHeight = 24;
        actionBar = new HBox();
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setSpacing(16);
        view = new ImageView("/com/housestack/resource/Eye_24px.png");
        edit = new ImageView("/com/housestack/resource/Edit_24px.png");
        delete = new ImageView("/com/housestack/resource/Trash_24px.png");

        setSize(view);
        setSize(edit);
        setSize(delete);

        JFXButton btnedit = new JFXButton();
        btnedit.setGraphic(edit);
        btnedit.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        JFXButton btndelete = new JFXButton();
        btndelete.setGraphic(delete);
        btndelete.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        JFXButton btnview = new JFXButton();
        btnview.setGraphic(view);
        btnview.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        btnview.setOnMouseClicked(e -> {
            getTreeTableView().getSelectionModel().select(getTreeTableRow().getIndex());
            viewOperation();
        });
        btnedit.setOnMouseClicked(e -> {
            getTreeTableView().getSelectionModel().select(getTreeTableRow().getIndex());
            editOperation();
        });
        btndelete.setOnMouseClicked(e -> {
            getTreeTableView().getSelectionModel().select(getTreeTableRow().getIndex());
            deleteOperation();
        });
        for (ActionButtonType i : buttonType) {
            switch (i) {
                case EDIT_BUTTON:
                    actionBar.getChildren().add(btnedit);
                    break;
                case DELETE_BUTTON:
                    actionBar.getChildren().addAll(btndelete);
                    break;
                case VIEW_BUTTON:
                    actionBar.getChildren().addAll(btnview);
                    break;
                default:
                    actionBar.getChildren().addAll(btnedit, btndelete);
            }
        }

    }

    public ActionCell() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setSize(ImageView iv) {
        iv.setFitHeight(FitHeight);
        iv.setFitWidth(FitWidth);
    }

    public void setEditIcon(String path) {
        edit = new ImageView(path);
    }

    public void setDeleteIcon(String path) {
        delete = new ImageView(path);
    }

    public void setViewIcon(String path) {
        view = new ImageView(path);
    }

    public void setIconSize(double width, double height) {
        this.FitWidth = width;
        this.FitHeight = height;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(actionBar);
        } else {
            setGraphic(null);
        }

    }

    public void editOperation() {
        // editCode

//        Method m;
//        m.getP
    }

    public void deleteOperation() {
        // delete code
    }

    public void viewOperation() {
        // view code
    }
}
