package de.chrb.gustav;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 final AnchorPane rootLayout = loadRootLayout();
			 final Scene scene = new Scene(rootLayout);

			 final String css = Main.class.getResource("view/style.css").toExternalForm();
			 scene.getStylesheets().clear();
			 scene.getStylesheets().add(css);

			 primaryStage.setScene(scene);
			 primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private AnchorPane loadRootLayout() throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));


		 final AnchorPane root = (AnchorPane) loader.load();
		// final MainController controller = (MainController)loader.getController();
		// controller.setGCParserRegistry(getParserRegistry());
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}


}
