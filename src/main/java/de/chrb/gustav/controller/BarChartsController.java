package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.StatisticsAnalyzer;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

/**
 * Controller containing the bar chart statistics
 *
 * @author Christian Bannes
 */
public class BarChartsController {

    @FXML private BarChart<String, Double> avg;
    @FXML private BarChart<String, Long> min;
    @FXML private BarChart<String, Double> totalGC;
    @FXML private BarChart<String, Double> overhead;
    @FXML private BarChart<String, Integer> num;
    @FXML private BarChart<String, Long> max;
    @FXML private BarChart<String, Double> numPerc;
    @FXML private BarChart<String, Double> sigma;


    /**
     * Adds the result of one gc file analyzis to the bar charts
     *
     * @param analyzer the result of one gc file analyzis
     */
	public void addSeries(StatisticsAnalyzer analyzer) {
    	this.num.getData().add(analyzer.createNumSeries());
    	this.overhead.getData().add(analyzer.createOverheadSeries());
    	this.avg.getData().add(analyzer.createAvgSeries());
    	this.max.getData().add(analyzer.createMaxSeries());
    	this.min.getData().add(analyzer.createMinSeries());
    	this.sigma.getData().add(analyzer.createSigmaSeries());
    	this.numPerc.getData().add(analyzer.createNumPercSeries());
    	this.totalGC.getData().add(analyzer.createSecsPercSeries());

	}

}
