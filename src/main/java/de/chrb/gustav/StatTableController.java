package de.chrb.gustav;
import java.util.List;

import de.chrb.gustav.model.statistics.Statistics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatTableController {

    @FXML
    private TableColumn<Statistics, Number> tcNum;
    @FXML
    private TableColumn<Statistics, Number> tcTocalGc;
    @FXML
    private TableColumn<Statistics, Number> tcNumPerc;
    @FXML
    private TableColumn<Statistics, Number> tcTocalGcPerc;
    @FXML
    private TableColumn<Statistics, Number> tcAvg;
    @FXML
    private TableColumn<Statistics, Number> tcMin;
    @FXML
    private TableColumn<Statistics, Number> tcMax;
    @FXML
    private TableColumn<Statistics, Number> tcOverhead;
    @FXML
    private TableColumn<Statistics, Number> tbSigma;
    @FXML
    private TableColumn<Statistics, String> gcType;
    @FXML
    private TableView<Statistics> statTable;



    @FXML
    public void initialize() {
    	this.gcType.setCellValueFactory(s -> s.getValue().name);
    	this.tcNum.setCellValueFactory(s -> s.getValue().num);
    	this.tcNumPerc.setCellValueFactory(s -> s.getValue().numPerc);
    	this.tcTocalGc.setCellValueFactory(s -> s.getValue().secs);
    	this.tcTocalGcPerc.setCellValueFactory(s -> s.getValue().secsPerc);
    	this.tcOverhead.setCellValueFactory(s -> s.getValue().overhead);
    	this.tcAvg.setCellValueFactory(s -> s.getValue().avg);
    	this.tbSigma.setCellValueFactory(s -> s.getValue().sigma);
    	this.tcMin.setCellValueFactory(s -> s.getValue().min);
    	this.tcMax.setCellValueFactory(s -> s.getValue().max);
    }

    public void addData(final List<Statistics> data) {
    	final ObservableList<Statistics> items = FXCollections.observableArrayList(data);
    	this.statTable.setItems(items);
    }
}

