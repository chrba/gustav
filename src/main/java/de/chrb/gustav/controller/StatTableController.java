package de.chrb.gustav.controller;
import java.util.List;

import de.chrb.gustav.model.statistics.Statistics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for statistics table.
 *
 * @author Christian Bannes
 */
public class StatTableController {

    @FXML private TableColumn<Statistics, Number> num;
    @FXML private TableColumn<Statistics, Number> tocalGc;
    @FXML private TableColumn<Statistics, Number> numPerc;
    @FXML private TableColumn<Statistics, Number> tocalGcPerc;
    @FXML private TableColumn<Statistics, Number> avg;
    @FXML private TableColumn<Statistics, Number> min;
    @FXML private TableColumn<Statistics, Number> max;
    @FXML private TableColumn<Statistics, Number> overhead;
    @FXML private TableColumn<Statistics, Number> sigma;
    @FXML private TableColumn<Statistics, String> type;
    @FXML private TableView<Statistics> statTable;

    /**
     * Initialize all fields by binding them to the corresponding
     * statistics property value.
     */
    @FXML
    public void initialize() {
    	this.type.setCellValueFactory(s -> s.getValue().name);
    	this.num.setCellValueFactory(s -> s.getValue().num);
    	this.numPerc.setCellValueFactory(s -> s.getValue().numPerc);
    	this.tocalGc.setCellValueFactory(s -> s.getValue().secs);
    	this.tocalGcPerc.setCellValueFactory(s -> s.getValue().secsPerc);
    	this.overhead.setCellValueFactory(s -> s.getValue().overhead);
    	this.avg.setCellValueFactory(s -> s.getValue().avg);
    	this.sigma.setCellValueFactory(s -> s.getValue().sigma);
    	this.min.setCellValueFactory(s -> s.getValue().min);
    	this.max.setCellValueFactory(s -> s.getValue().max);
    }

    /**
     * Adds the data to the statistics table. Each element in the list corresponds to
     * one table row.
     *
     * @param data the statistics data
     */
    public void addData(final List<Statistics> data) {
    	final ObservableList<Statistics> items = FXCollections.observableArrayList(data);
    	this.statTable.setItems(items);
    }
}

