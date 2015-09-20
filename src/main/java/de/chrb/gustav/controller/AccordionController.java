package de.chrb.gustav.controller;

import java.util.Arrays;
import java.util.List;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.statistics.Characteristics;
import de.chrb.gustav.model.statistics.StatisticsAnalyzer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.web.HTMLEditor;

/**
 * Accordion controller with three elemens:
 *   - important data textarea
 *   - timeline chart
 *   - pause distribution chart
 *
 * @author Christian Bannes
 */
public class AccordionController {
    @FXML private HTMLEditor importantDataTextArea;
    @FXML private ScatterChart<Double, Double> timeline;
    @FXML private ScatterChart<Long, Long> pauseDistribution;
    @FXML private PieChart pieStats;
    @FXML private Accordion accordionView;

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
	public void addData(final StatisticsAnalyzer analyzer, final List<GCEvent> events) {
    	this.timeline.setData(FXCollections.observableArrayList(analyzer.createTimeline()));
    	this.pauseDistribution.setData(FXCollections.observableArrayList(analyzer.createPauseDistribution()));

    	final Characteristics c = new Characteristics(events);
    	this.importantDataTextArea.setHtmlText(c.text());
    	this.pieStats.setData(FXCollections.observableArrayList(analyzer.createPieChartData()));

    	final TitledPane p = accordionView.getPanes().get(0);
    	accordionView.setExpandedPane(p);
	}
}