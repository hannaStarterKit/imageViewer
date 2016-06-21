/**
 * 
 */
package com.starterkit.viewer;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author HSIENKIE
 *
 */
public class Startup extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Simple image viewer");

		Parent root = FXMLLoader.load(getClass().getResource("/com/starterkit/viewer/view/viewer.fxml"),
				ResourceBundle.getBundle("com/starterkit/viewer/bundle/base"));
		Scene scene = new Scene(root);

		/*
		 * Set the style sheet(s) for application.
		 */
		scene.getStylesheets().add(getClass().getResource("/com/starterkit/viewer/css/standard.css").toExternalForm());

		primaryStage.setScene(scene);

		primaryStage.show();

	}

}
