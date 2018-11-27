/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.housestack.support;

import java.io.File;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author choudhary
 */
public class FileChoosers {

    public File openFileCooser(StackPane window) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog((Stage) window.getScene().getWindow());
        return file;
    }
}
