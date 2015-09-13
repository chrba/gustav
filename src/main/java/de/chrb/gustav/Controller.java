package de.chrb.gustav;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;
import de.chrb.gustav.model.statistics.Statistics;
import de.chrb.gustav.model.statistics.StatisticsAnalyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
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
    private BarChart<String, Double> bcAvg;

    @FXML
    private TableColumn<Statistics, Number> tcNum;

    @FXML
    private TableColumn<Statistics, Number> tbSigma;

    @FXML
    private ScatterChart<Double, Double> lcGCTimeline;

    @FXML
    private TableColumn<Statistics, String> gcType;

    @FXML
    private BarChart<String, Long> bcMin;

    @FXML
    private ScatterChart<Long, Long> lcGCPauseDistribution;

    @FXML
    private BarChart<String, Double> bcTotalGC;

    @FXML
    private TableColumn<Statistics, Number> tcTocalGc;


    @FXML
    private TableColumn<Statistics, Number> tcNumPerc;

    @FXML
    private BarChart<String, Double> bcOverhead;

    @FXML
    private BarChart<String, Integer> bcNum;

    @FXML
    private TableView<Statistics> statTable;

    @FXML
    private Button btnRemove;

    @FXML
    private TableColumn<Statistics, Number> tcTocalGcPerc;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableColumn<Statistics, Number> tcMax;

    @FXML
    private TableColumn<Statistics, Number> tcOverhead;

    @FXML
    private PieChart pcGCStats;

    @FXML
    private BarChart<String, Long> bcMax;

    @FXML
    private BarChart<String, Double> bcNumPerc;

    @FXML
    private TableColumn<Statistics, Number> tcAvg;

    @FXML
    private TableColumn<Statistics, Number> tcMin;

    @FXML
    private Button btnAdd;

    @FXML
    private TextArea tfImportantDataTextArea;

    @FXML
    private BarChart<String, Double> bcSigma;

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

    	final StatisticsAnalyzer analyzer = new StatisticsAnalyzer(events);
    	final ObservableList<Statistics> data =
    	        FXCollections.observableArrayList(analyzer.statisticsList());

    	statTable.setItems(data);

    	final ObservableList<String> items = FXCollections.observableArrayList("file:" + file.getName());
    	fileListView.setItems(items);


    	bcNum.getData().add(analyzer.createNumSeries());
    	bcOverhead.getData().add(analyzer.createOverheadSeries());
    	bcAvg.getData().add(analyzer.createAvgSeries());
    	bcMax.getData().add(analyzer.createMaxSeries());
    	bcMin.getData().add(analyzer.createMinSeries());
    	bcSigma.getData().add(analyzer.createSigmaSeries());
    	bcNumPerc.getData().add(analyzer.createNumPercSeries());
    	bcTotalGC.getData().add(analyzer.createSecsPercSeries());


    	//
    	//lcGCTimeline
    	XYChart.Series<Double, Integer> series = new XYChart.Series<Double, Integer>();
    	series.getData().add(new XYChart.Data<>(1.0, 10));
    	series.getData().add(new XYChart.Data<>(4.3, 11));
    	series.getData().add(new XYChart.Data<>(5.1, 2));
    	series.getData().add(new XYChart.Data<>(7.876, 7));
    	series.getData().add(new XYChart.Data<>(10.3442, 20));
    	series.getData().add(new XYChart.Data<>(12.1, 4));
    	series.getData().add(new XYChart.Data<>(20.4, 40));

    	lcGCTimeline.setData(FXCollections.observableArrayList(analyzer.createTimeline()));

    	lcGCPauseDistribution.setData(FXCollections.observableArrayList(analyzer.createPauseDistribution()));
    	//lcGCTimeline.setCreateSymbols(false);

    	//pcGCStats.
    	pcGCStats.setData(FXCollections.observableArrayList(analyzer.createPieChartData()));

    }

    @FXML
    void removeFile(ActionEvent event) {

    }

	public void setGCParserRegistry(ParserRegistry parserRegistry) {
		this.parserRegistry = parserRegistry;

	}



}
