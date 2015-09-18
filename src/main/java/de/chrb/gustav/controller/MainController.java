package de.chrb.gustav.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;
import de.chrb.gustav.model.statistics.StatisticsAnalyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	private static Logger LOG = LoggerFactory.getLogger(MainController.class);

	@FXML private Parent root;

    @FXML private Button btnRemove;
    @FXML private ListView<String> fileListView;
    @FXML private Button btnAdd;

    @FXML private StatTableController statTableViewController;
    @FXML private BarChartsController barChartsViewController;
    @FXML private AccordionController accordionViewController;

	private ParserRegistry parserRegistry;
	private final ObservableList<String> files = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		fileListView.setItems(this.files);
	}

    @FXML
    void addNewFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	final Stage stage = (Stage)root.getScene().getWindow();
    	final File file = fileChooser.showOpenDialog(stage);

    	final List<GCEvent> events = parserRegistry.parse(file);
    	LOG.debug("Found {} GCEvents", events.size());

    	final StatisticsAnalyzer analyzer = new StatisticsAnalyzer(events);
    	this.statTableViewController.addData(analyzer.statisticsList());

    	files.add("file: " + file.getName());

    	barChartsViewController.addSeries(analyzer);
    	accordionViewController.addData(analyzer, events);

    }

    @FXML
    void removeFile(ActionEvent event) {

    }

	public void setGCParserRegistry(ParserRegistry parserRegistry) {
		this.parserRegistry = parserRegistry;

	}



}
