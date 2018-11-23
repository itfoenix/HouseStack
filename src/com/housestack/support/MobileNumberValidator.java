/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.support;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author NARENDRA JADHAV
 */
public class MobileNumberValidator extends ValidatorBase {

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl textfield = (TextInputControl) srcControl.get();
        if (textfield.getText().isEmpty() || textfield.getText() == null) {
            hasErrors.set(false);
        }
        if (!textfield.getText().matches("[0|91]?[7-9][0-9]{9}")) {
            hasErrors.set(true);
        } else {
            hasErrors.set(false);
        }
    }

}
