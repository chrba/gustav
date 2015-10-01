package de.chrb.gustav.controller;
import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import de.chrb.gustav.model.statistics.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for statistics table.
 *
 * @author Christian Bannes
 */
public class StatTableController implements GCResultObserver {

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
    @FXML private TableColumn<Statistics, String> fileName;
    @FXML private TableView<Statistics> statTable;

    /**
     * Initialize all fields by binding them to the corresponding
     * statistics property value.
     */
    @FXML
    public void initialize() {
    	this.fileName.setCellValueFactory(s -> s.getValue().fileName);
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

	@Override
	public void observe(final GCAnalyzeResult result) {
		this.statTable.getItems().addAll(result.getStatistics());

	}

	@Override
	public void remove(final String fromFileName) {
		this.statTable.getItems().removeIf(s -> s.fileName.get().equals(fromFileName));
	}
}

