package com.housestack.support;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PhoenixSupport {

    public static void setRequiredField(JFXTextField... t) {
        for (JFXTextField s : t) {
            RequiredFieldValidator rf = new RequiredFieldValidator();
            rf.setMessage(s.getPromptText() + "is requeired");
            s.setValidators(rf);
        }
    }

    public static void setRequiredField(JFXPasswordField... t) {
        for (JFXPasswordField s : t) {
            RequiredFieldValidator rf = new RequiredFieldValidator();
            rf.setMessage(s.getPromptText() + "is requeired");
            s.setValidators(rf);
        }
    }

    public static void setRequiredField(JFXComboBox... t) {
        for (JFXComboBox s : t) {
            RequiredFieldValidator rf = new RequiredFieldValidator();
            rf.setMessage(s.getPromptText() + "is requeired");
            s.setValidators(rf);
        }
    }

    public static void setEmailValidator(JFXTextField... t) {
        for (JFXTextField s : t) {
            EmailValidator rf = new EmailValidator();
            rf.setMessage("Enter valid email address");
            s.setValidators(rf);
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    if (!s.validate()) {
                        s.clear();
                    }
                }
            });
        }
    }

    public static void setMoblieValidator(JFXTextField... t) {
        for (JFXTextField s : t) {
            MobileNumberValidator rf = new MobileNumberValidator();
            rf.setMessage("Enter valid contact number");
            s.setValidators(rf);
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    if (!s.validate()) {
                        s.clear();
                    }
                }
            });
        }
    }

    public static boolean isValidate(JFXTextField... n) {
        boolean result = false;
        for (JFXTextField t : n) {
            if (t.getText() != null) {
                if (!t.getText().isEmpty()) {
                    if (t.validate()) {
                        result = true;
                    } else {
                        System.out.println("not null  and is not empty but invalid" + t.validate());
                        result = false;
                        break;
                    }
                } else {
                    System.out.println("not null and is empty" + t.validate());
                    result = false;
                    break;
                }
            } else {
                System.out.println("null" + t.validate());
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isValidate(JFXPasswordField... n) {
        boolean result = false;
        for (JFXPasswordField t : n) {
            if (t.getText() != null) {
                if (!t.getText().isEmpty()) {
                    result = true;
                } else {
                    t.validate();
                    result = false;
                    break;
                }
            } else {
                t.validate();
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isValidate(JFXComboBox... c) {
        boolean result = true;
        for (JFXComboBox cb : c) {
            if (cb.getSelectionModel().isEmpty()) {
                cb.validate();
                result = false;
                break;
            }
        }
        return result;
    }

    public static void setFullScreen(StackPane window) {
        window.setPrefWidth(PhoenixConfiguration.getWidth());
        window.setPrefHeight(PhoenixConfiguration.getheight());

    }

    public static void setTabFilter(JFXTextArea textArea) {
        Parent p = textArea.getParent();
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB) {
                    ObservableList<Node> children = p.getChildrenUnmodifiable();
                    int idx = children.indexOf(textArea);
                    if (idx >= 0) {
                        for (int i = idx + 1; i < children.size(); i++) {
                            if (children.get(i).isFocusTraversable()) {
                                children.get(i).requestFocus();
                                break;
                            }
                        }
                    }
                    event.consume();
                }

            }
        });
    }

    public static void Info(String ContentMessage, String HeaderText) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setHeaderText(HeaderText);
        dialog.setContentText(ContentMessage);
        dialog.setResult(ButtonType.OK);
        dialog.setResult(ButtonType.CANCEL);
        dialog.showAndWait();
    }

    public static void Error(String contain, StackPane sp) {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Label lblheader = new Label();
        lblheader.setText("Error");
        lblheader.setMaxWidth(1.7976931348623157E308);
        lblheader.setPadding(new Insets(8));
        lblheader.setStyle("-fx-background-color:red; -fx-text-fill:white");
        jfxDialogLayout.setHeading(lblheader);
        jfxDialogLayout.setBody(new Text(contain));
        JFXDialog jfxDialog = new JFXDialog(sp, jfxDialogLayout, JFXDialog.DialogTransition.TOP);
        JFXButton okay = new JFXButton("Cancel");
        okay.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                jfxDialog.close();
            }
        });
        jfxDialog.setOnDialogOpened(e -> okay.requestFocus());
        okay.setPrefWidth(110);
        okay.setStyle("-fx-background-color: #3E4A4F; -fx-text-fill: white;");
        okay.setButtonType(JFXButton.ButtonType.RAISED);
        okay.setOnAction(e -> {
            jfxDialog.close();
        });
        jfxDialogLayout.setActions(okay);
        jfxDialog.show();
    }

    public static String getData(String data) {
        if (data != null) {
            if (data.isEmpty()) {
                return "NA";
            } else {
                return data;
            }
        } else {
            return "NA";
        }
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
