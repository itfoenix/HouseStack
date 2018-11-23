/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.support;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author NARENDRA JADHAV
 */
public class Dialog {

    private static final int ERROR = 1;
    private static final int SUCCESS = 2;

    public static JFXDialog getDialog(StackPane sp, Region body, JFXDialog.DialogTransition anim) {
        JFXDialogLayout bodyContain = new JFXDialogLayout();
        bodyContain.setBody(body);
        JFXDialog dialog = new JFXDialog(sp, bodyContain, anim);
        return dialog;
    }

    public static JFXDialog getDialog(StackPane sp, Region header, Region body, JFXButton... button) {
        JFXDialogLayout bodyContain = new JFXDialogLayout();
        bodyContain.setHeading(header);
        bodyContain.setBody(body);
        bodyContain.setActions(button);
        JFXDialog dialog = new JFXDialog(sp, bodyContain, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);
        return dialog;
    }

    private final static void showDialog(String errorHeading, String errorDescription, StackPane root, int t) {

        try {
            VBox dailog = FXMLLoader.load(Dialog.class.getResource("/com/housestack/support/Dialog.fxml"));

            JFXDialog d = getDialog(root, dailog, JFXDialog.DialogTransition.TOP);
            d.setOnDialogClosed(e -> root.setEffect(null));
            d.show();
            dailog.getChildren().forEach((e) -> {
                switch (e.getAccessibleText()) {
                    case "lbl_error_header":
                        Label s = ((Label) e);
                        s.setText(errorHeading);
                        if (t == ERROR) {
                            ImageView iv = new ImageView("/com/housestack/resource/error.png");
                            iv.setFitHeight(0);
                            iv.setFitWidth(0);
                            s.setGraphic(iv);
                        } else if (t == SUCCESS) {
                            ImageView iv = new ImageView("/com/housestack/resource/Ok_24px.png");
                            iv.setFitHeight(0);
                            iv.setFitWidth(0);
                            s.setGraphic(iv);
                        }
                        break;
                    case "lbl_error_description":
                        Label de = ((Label) e);
                        de.setText(errorDescription);
                        if (t == ERROR) {
                            ImageView iv = new ImageView("/com/housestack/resource/Delete_24px.png");
                            iv.setFitHeight(48);
                            iv.setFitWidth(48);
                            de.setGraphic(iv);
                        } else if (t == SUCCESS) {
                            ImageView iv = new ImageView("/com/housestack/resource/Checkmark_24px.png");
                            iv.setFitHeight(48);
                            iv.setFitWidth(48);
                            de.setGraphic(iv);
                        }
                        break;
                    case "btn_ok":
                        ((JFXButton) e).addEventHandler(MouseEvent.MOUSE_CLICKED, (x) -> {

                            d.close();

                        });
                        break;
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Error(String errorHeading, String errorDescription, StackPane root) {
        showDialog(errorHeading, errorDescription, root, ERROR);
    }

    public static void Success(String errorHeading, String errorDescription, StackPane root) {
        showDialog(errorHeading, errorDescription, root, SUCCESS);
    }
}
