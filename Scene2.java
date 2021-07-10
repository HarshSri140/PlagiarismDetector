package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.awt.*;
import java.io.IOException;

public class Scene2 {
    String str;
    @FXML private TextArea test_text;
    @FXML private TextArea result_display;
    public void pushButton(ActionEvent event)throws Exception{
        result_display.setText("Some kind of error occured. Please close application, check internet connection and run again.");
        str= test_text.getText();
        System.out.println(str);
        detect.detector(str);
        result_display.setText(detect.finalres+"\n\n"+"Percentage plagiarism detected is around: "+detect.res+"%");
    }

}
