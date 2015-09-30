package de.chrb.gustav.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;
import de.chrb.gustav.model.statistics.DataContainer;
import de.chrb.gustav.model.statistics.Statistics;
import de.chrb.gustav.model.statistics.ChartSeriesCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	private static Logger LOG = LoggerFactory.getLogger(MainController.class);

	@FXML private Parent root;

    @FXML private Button btnRemove;
    @FXML private ListView<GCFile> fileListView;
    @FXML private Button btnAdd;

    @FXML private StatTableController statTableViewController;
    @FXML private BarChartsController barChartsViewController;
   // @FXML private AccordionController accordionViewController;

    @FXML private TabPane accordionTabPane;
   // private ParserRegistry parserRegistry;


	//private final ObservableList<GCFile> files = FXCollections.observableArrayList();
	//private ObservableList<Statistics> statistics = FXCollections.observableArrayList();
    private final DataContainer dataContainer = new DataContainer();


	@FXML
	public void initialize() {

		dataContainer.bind(this.fileListView);
		dataContainer.bind(this.statTableViewController);
		dataContainer.bind(this.barChartsViewController);
		dataContainer.bind(this.accordionTabPane);
	}

    @FXML
    void addNewFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	final Stage stage = (Stage)root.getScene().getWindow();
    	final GCFile gcFile = new GCFile(fileChooser.showOpenDialog(stage));

    	//final List<GCEvent> events = parserRegistry.parse(gcFile.toFile());
    //	LOG.debug("Found {} GCEvents", events.size());

    	//final StatisticsAnalyzer analyzer = new StatisticsAnalyzer(gcFile, events);

    	//this.statTableViewController.addData(analyzer.statisticsList());
    	//this.statistics.addAll(analyzer.statisticsList());
    	//this.files.add(gcFile);
    	this.dataContainer.add(gcFile);

    	//this.barChartsViewController.addSeries(analyzer);
    	//this.accordionViewController.addData(analyzer, events);

//    	final CustomTab tab = new CustomTab();
//    	tab.setText(gcFile.getName());
//    	accordionTabPane.getTabs().add(tab);
//    	tab.addData(analyzer, events);
    }

    @FXML
    void removeFile(ActionEvent event) {
    	final GCFile gcFile = this.fileListView.getSelectionModel().getSelectedItem();
    	this.dataContainer.remove(gcFile);
    	//this.files.remove(gcFile);
    }


}
