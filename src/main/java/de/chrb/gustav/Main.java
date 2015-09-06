package de.chrb.gustav;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.chrb.gustav.model.message.GCParser;
import de.chrb.gustav.model.parser.ParserRegistry;
import de.chrb.gustav.model.parser.cms.ConcurrentMarkParser;
import de.chrb.gustav.model.parser.cms.ConcurrentResetParser;
import de.chrb.gustav.model.parser.cms.ConcurrentSweepParser;
import de.chrb.gustav.model.parser.cms.PreCleanParser;
import de.chrb.gustav.model.parser.parnew.ParNewParser;
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
		 final Controller controller = (Controller)loader.getController();
		 controller.setGCParserRegistry(getParserRegistry());
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ParserRegistry getParserRegistry() {
		final List<GCParser> parsers = Arrays.asList(
				new ParNewParser(),
				new ConcurrentMarkParser(),
				new ConcurrentResetParser(),
				new ConcurrentSweepParser(),
				new PreCleanParser());
		return new ParserRegistry(parsers);
	}

}
