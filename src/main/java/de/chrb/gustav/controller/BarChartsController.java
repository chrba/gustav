package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.ChartSeries;
import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

/**
 * Controller containing the bar chart statistics
 *
 * @author Christian Bannes
 */
public class BarChartsController implements GCResultObserver {

    @FXML private BarChart<String, Double> avg;
    @FXML private BarChart<String, Long> min;
    @FXML private BarChart<String, Double> totalGC;
    @FXML private BarChart<String, Double> overhead;
    @FXML private BarChart<String, Integer> num;
    @FXML private BarChart<String, Long> max;
    @FXML private BarChart<String, Double> numPerc;
    @FXML private BarChart<String, Double> sigma;



	@Override
	public void observe(GCAnalyzeResult result) {
		final ChartSeries analyzer = result.getChartSeries();
		final String name = result.getGCFile().getName();
    	this.num.getData().add(analyzer.createNumSeries(name));
    	this.overhead.getData().add(analyzer.createOverheadSeries(name));
    	this.avg.getData().add(analyzer.createAvgSeries(name));
    	this.max.getData().add(analyzer.createMaxSeries(name));
    	this.min.getData().add(analyzer.createMinSeries(name));
    	this.sigma.getData().add(analyzer.createSigmaSeries(name));
    	this.numPerc.getData().add(analyzer.createNumPercSeries(name));
    	this.totalGC.getData().add(analyzer.createSecsPercSeries(name));
	}


	@Override
	public void remove(String fromFileName) {
		this.num.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.overhead.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.avg.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.max.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.min.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.sigma.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.numPerc.getData().removeIf(s -> s.getName().equals(fromFileName));
		this.totalGC.getData().removeIf(s -> s.getName().equals(fromFileName));
	}

}
