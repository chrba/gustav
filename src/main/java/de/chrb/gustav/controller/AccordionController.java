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
import javafx.scene.web.HTMLEditor;

public class AccordionController {
    @FXML private HTMLEditor tfImportantDataTextArea;
    @FXML private ScatterChart<Double, Double> lcGCTimeline;
    @FXML private ScatterChart<Long, Long> lcGCPauseDistribution;
    @FXML private PieChart pcGCStats;

	@FXML
	public void initialize() {
		hideHTMLEditorToolbars(tfImportantDataTextArea);
	}

	public static void hideHTMLEditorToolbars(final HTMLEditor editor) {
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

	public void addData(final StatisticsAnalyzer analyzer, final List<GCEvent> events) {
    	lcGCTimeline.setData(FXCollections.observableArrayList(analyzer.createTimeline()));
    	lcGCPauseDistribution.setData(FXCollections.observableArrayList(analyzer.createPauseDistribution()));

    	final Characteristics c = new Characteristics(events);
    	tfImportantDataTextArea.setHtmlText(c.text());
    	pcGCStats.setData(FXCollections.observableArrayList(analyzer.createPieChartData()));
	}
}
