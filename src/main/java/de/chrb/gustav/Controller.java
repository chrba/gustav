package de.chrb.gustav;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;
import de.chrb.gustav.model.statistics.Statistics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	private static Logger LOG = LoggerFactory.getLogger(Controller.class);

	@FXML
	private Parent root;

    @FXML
    private BarChart<?, ?> bcAvg;

    @FXML
    private TableColumn<Statistics, Number> tcNum;

    @FXML
    private TableColumn<Statistics, Number> tbSigma;

    @FXML
    private LineChart<?, ?> lcGCTimeline;

    @FXML
    private TableColumn<Statistics, String> gcType;

    @FXML
    private BarChart<?, ?> gcMin;

    @FXML
    private LineChart<?, ?> lcGCPauseDistribution;

    @FXML
    private BarChart<?, ?> bcTotalGC;

    @FXML
    private TableColumn<Statistics, Number> tcTocalGc;


    @FXML
    private TableColumn<Statistics, Number> tcNumPerc;

    @FXML
    private BarChart<?, ?> bcOverhead;

    @FXML
    private BarChart<?, ?> bcNum;

    @FXML
    private TableView<Statistics> statTable;

    @FXML
    private Button btnRemove;

    @FXML
    private TableColumn<Statistics, Number> tcTocalGcPerc;

    @FXML
    private ListView<File> fileListView;

    @FXML
    private TableColumn<Statistics, Number> tcMax;

    @FXML
    private TableColumn<Statistics, Number> tcOverhead;

    @FXML
    private PieChart pcGCStats;

    @FXML
    private BarChart<?, ?> bcMax;

    @FXML
    private BarChart<?, ?> bcNumPerc;

    @FXML
    private TableColumn<Statistics, Number> tcAvg;

    @FXML
    private TableColumn<Statistics, Number> tcMin;

    @FXML
    private Button btnAdd;

    @FXML
    private TextArea tfImportantDataTextArea;

    @FXML
    private BarChart<?, ?> gcSigma;

	private ParserRegistry parserRegistry;



    @FXML
    void addNewFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	final Stage stage = (Stage)root.getScene().getWindow();
    	final File file = fileChooser.showOpenDialog(stage);

    	final List<GCEvent> events = parserRegistry.parse(file);
    	LOG.debug("Found {} GCEvents", events.size());

    	gcType.setCellValueFactory(s -> s.getValue().name);
    	tcNum.setCellValueFactory(s -> s.getValue().num);
    	tcNumPerc.setCellValueFactory(s -> s.getValue().numPerc);
    	tcTocalGc.setCellValueFactory(s -> s.getValue().secs);
    	tcTocalGcPerc.setCellValueFactory(s -> s.getValue().secsPerc);
    	tcOverhead.setCellValueFactory(s -> s.getValue().overhead);
    	tcAvg.setCellValueFactory(s -> s.getValue().avg);
    	tbSigma.setCellValueFactory(s -> s.getValue().sigma);
    	tcMin.setCellValueFactory(s -> s.getValue().min);
    	tcMax.setCellValueFactory(s -> s.getValue().max);

    	final Statistics s = new Statistics(file.getName(), "ParNew", events, events);
    	final ObservableList<Statistics> data =
    	        FXCollections.observableArrayList(s);

    	statTable.setItems(data);

    	final ObservableList<File> items = FXCollections.observableArrayList(file);
    	fileListView.setItems(items);
    }

    @FXML
    void removeFile(ActionEvent event) {

    }

	public void setGCParserRegistry(ParserRegistry parserRegistry) {
		this.parserRegistry = parserRegistry;

	}



}
