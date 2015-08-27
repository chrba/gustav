package de.chrb.gustav;

import java.io.File;

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
	@FXML
	private Parent root;

    @FXML
    private BarChart<?, ?> bcAvg;

    @FXML
    private TableColumn<?, ?> tcNum;

    @FXML
    private TableColumn<?, ?> tbSigma;

    @FXML
    private LineChart<?, ?> lcGCTimeline;

    @FXML
    private TableColumn<?, ?> tcFile;

    @FXML
    private BarChart<?, ?> gcMin;

    @FXML
    private LineChart<?, ?> lcGCPauseDistribution;

    @FXML
    private BarChart<?, ?> bcTotalGC;

    @FXML
    private TableColumn<?, ?> tcNumPerc;

    @FXML
    private BarChart<?, ?> bcOverhead;

    @FXML
    private BarChart<?, ?> bcNum;

    @FXML
    private TableView<?> statTable;

    @FXML
    private Button btnRemove;

    @FXML
    private TableColumn<?, ?> tcTocalGcPerc;

    @FXML
    private ListView<File> fileListView;

    @FXML
    private TableColumn<?, ?> tcMax;

    @FXML
    private TableColumn<?, ?> tcOverhead;

    @FXML
    private PieChart pcGCStats;

    @FXML
    private BarChart<?, ?> bcMax;

    @FXML
    private BarChart<?, ?> bcNumPerc;

    @FXML
    private TableColumn<?, ?> tcAvg;

    @FXML
    private TableColumn<?, ?> tcMin;

    @FXML
    private Button btnAdd;

    @FXML
    private TextArea tfImportantDataTextArea;

    @FXML
    private BarChart<?, ?> gcSigma;

    @FXML
    void addNewFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	final Stage stage = (Stage)root.getScene().getWindow();
    	final File file = fileChooser.showOpenDialog(stage);

    	final ObservableList<File> items = FXCollections.observableArrayList(file);
    	fileListView.setItems(items);
    }

    @FXML
    void removeFile(ActionEvent event) {

    }



}
