package de.chrb.gustav;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<?, ?> bcAvg;

    @FXML
    private LineChart<?, ?> gcTimeline;

    @FXML
    private Button addBtn;

    @FXML
    private BarChart<?, ?> gcMin;

    @FXML
    private TextArea importantDataTextArea;

    @FXML
    private BarChart<?, ?> bcMax;

    @FXML
    private BarChart<?, ?> bcNumPerc;

    @FXML
    private BarChart<?, ?> bcTotalGC;

    @FXML
    private BarChart<?, ?> bcOverhead;

    @FXML
    private BarChart<?, ?> bcNum;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<?> statTable;

    @FXML
    private BarChart<?, ?> gcSigma;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<?, ?> gcPauseDistribution;

    @FXML
    private ListView<File> fileListView;

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
