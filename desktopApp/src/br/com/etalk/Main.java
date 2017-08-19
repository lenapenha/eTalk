package br.com.etalk;

import java.io.IOException;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import br.com.etalk.serial.*;
 
public class Main extends Application {
    private Scene scene;
    private static int i = 0;
    
    @Override
    public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        scene = new Scene(new Browser(),600,350);
        stage.setScene(scene);
        //stage.setMaximized(false);
        stage.setResizable(true);
        //stage.setFullScreen(true);
        
        //scene.getStylesheets().add("webviewsample/BrowserToolbar.css");        
        stage.show();
    }
    
    public static void main(String[] args){
    	SerialComm comm = new SerialComm();
        
    	comm.serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {

                // NOTE! - It is extremely important to read the data received from the
                // serial port.  If it does not get read from the receive buffer, the
                // buffer will continue to grow and consume memory.

                // print out the data received to the console
                try {
                	//comm.console.println("[HEX DATA]   " + event.getHexByteString());
                	comm.console.println("[ASCII DATA] " + event.getAsciiString());
                	comm.serial.writeln("AAAAAAAAAAA" + Integer.toString(i));
                	i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /*
        try {
			comm.serial.write("AAAAAAAAAAA");
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        launch(args);
    }
}

class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("https://35.199.23.52:8443");
        //add the web view to the scene
        getChildren().add(browser);
 
    }
    @SuppressWarnings("unused")
	private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 900;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 600;
    }
}