package de.chrb.gustav.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The main controller bunching together all gui
 * controlls. The main controller is the entry point
 * for gc parsing. The controller will be notified through
 * {@link #addNewFile(ActionEvent)} when a new gc file is added
 * and through {@link #removeFile(ActionEvent)} when a gc file
 * is removed.
 *
 * @author Christian Bannes
 */
public class MainController {
	private static Logger LOG = LoggerFactory.getLogger(MainController.class);

	@FXML private Parent root;

    @FXML private Button btnRemove;
    @FXML private ListView<GCFile> fileListView;
    @FXML private Button btnAdd;

    @FXML private StatTableController statTableViewController;
    @FXML private BarChartsController barChartsViewController;

    @FXML private TabPane accordionTabPane;

	private CompositeObserver gcResultObserver;

	/**
	 * Initialize the gcResultObserver
	 */
	@FXML
	public void initialize() {
		this.gcResultObserver = new CompositeObserver(Arrays.asList(
				barChartsViewController,
				statTableViewController,
				new TabPaneGCObserver(this.accordionTabPane),
				new FileListViewGCObserver(this.fileListView)));
	}

	/**
	 * Event notification when a new gc file is added.
	 *
	 * @param event the action event triggered when a new gc file is added
	 */
    @FXML
    void addNewFile(ActionEvent event) {
    	LOG.debug("add new gc file");;
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	final Stage stage = (Stage)root.getScene().getWindow();
    	final GCFile gcFile = new GCFile(fileChooser.showOpenDialog(stage));

    	final GCAnalyzeResult gcResult = GCAnalyzeResult.from(gcFile);
    	this.gcResultObserver.observe(gcResult);
    }

    /**
     * Event notification when a gc file is removed
     *
	 * @param event the action event triggered when a new gc file is added
     */
    @FXML
    void removeFile(ActionEvent event) {
    	final GCFile gcFile = this.fileListView.getSelectionModel().getSelectedItem();
    	this.gcResultObserver.remove(gcFile.getName());
    }


}
