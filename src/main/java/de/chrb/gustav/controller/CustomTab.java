package de.chrb.gustav.controller;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.statistics.Characteristics;
import de.chrb.gustav.model.statistics.ChartSeriesCreator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.web.HTMLEditor;

/**
 * Represents one tab for one gc file with some charts showing statistics
 * of a gc analyzis.
 *
 * @author Christian Bannes
 */
public class CustomTab extends Tab {
    @FXML private HTMLEditor importantDataTextArea;
    @FXML private ScatterChart<Double, Double> timeline;
    @FXML private ScatterChart<Long, Long> pauseDistribution;
    @FXML private PieChart pieStats;
    @FXML private Accordion accordion;

    /**
     * Creates new CustomTab
     */
    public CustomTab() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Accordion.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Currently there is no official way of hiding the controlls of
     * the html, so we go the unofficial way
     */
	@FXML
	public void initialize() {
		hideHTMLEditorToolbars(importantDataTextArea);
	}

	/**
	 * Hides the controls of the given html editor
	 *
	 * @param editor the editor
	 */
	private static void hideHTMLEditorToolbars(final HTMLEditor editor) {
	    editor.setVisible(false);
	    Platform.runLater(() -> {
		    Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
		    Arrays.stream(nodes).forEach(n -> {
	            n.setVisible(false);
	            n.setManaged(false);
		    });
	        editor.setVisible(true);
	    });
	}

	/**
	 * Adds data to the important textarea filed, the gc timeline and the gc pause distribution
	 *
	 * @param analyzer contains statistics analyze results
	 * @param events the gc events
	 */
	public void addData(final ChartSeriesCreator analyzer, final List<GCEvent> events) {
    	this.timeline.setData(FXCollections.observableArrayList(analyzer.createTimeline()));
    	this.pauseDistribution.setData(FXCollections.observableArrayList(analyzer.createPauseDistribution()));

    	final Characteristics c = new Characteristics(events);
    	this.importantDataTextArea.setHtmlText(c.text());
    	this.pieStats.setData(FXCollections.observableArrayList(analyzer.createPieChartData()));

    	final TitledPane p = this.accordion.getPanes().get(0);
    	this.accordion.setExpandedPane(p);
	}
}
