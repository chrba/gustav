package de.chrb.gustav.model.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.chrb.gustav.model.gc.GCEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * Creates a chart series
 *
 * @author Christian Bannes
 */
public class ChartSeries {
	private final List<Statistics> statistics;
	private final Map<String, List<GCEvent>> eventsByName;

	public ChartSeries(final GCAnalyzeResult analyzeResult) {
		this.statistics = analyzeResult.getStatistics();
		this.eventsByName = analyzeResult.eventsByName();
	}

	public XYChart.Series<String, Integer> createNumSeries(final String name) {
		final XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.num.get())));
		return series;
	}

	public XYChart.Series<String, Double> createNumPercSeries(final String name) {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.numPerc.get())));
		return series;
	}

	public XYChart.Series<String, Double> createSecsSeries(final String name) {
		final XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.secs.get())));
		return series;
	}

	public XYChart.Series<String, Double> createSecsPercSeries(final String name) {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.secsPerc.get())));
		return series;
	}
	public XYChart.Series<String, Double> createOverheadSeries(final String name) {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.overhead.get())));
		return series;
	}
	public XYChart.Series<String, Double> createAvgSeries(final String name) {
		final XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.avg.get())));
		return series;
	}
	public XYChart.Series<String, Double> createSigmaSeries(final String name) {
		final XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.sigma.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMaxSeries(final String name) {
		final XYChart.Series<String, Long> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.max.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMinSeries(final String name) {
		final XYChart.Series<String, Long> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.min.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMedianSeries(final String name) {
		final XYChart.Series<String, Long> series = new XYChart.Series<>();
		series.setName(name);
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.median.get())));
		return series;
	}

	public List<XYChart.Series<Double, Double>> createTimeline() {
		final List<XYChart.Series<Double, Double>> serieses = new ArrayList<>();
		this.eventsByName.forEach((k, v) -> serieses.add(createNewSeries(k, v)));
		return serieses;
	}

	public List<XYChart.Series<Long, Long>> createPauseDistribution() {
		final List<XYChart.Series<Long, Long>> serieses = new ArrayList<>();
		this.eventsByName.forEach((k, v) -> serieses.add(createPauseDistributionSeries(k, v)));
		return serieses;
	}

	public List<PieChart.Data> createPieChartData() {
		final List<PieChart.Data> series = new ArrayList<>();
		this.eventsByName.forEach((k, v) -> series.add(new PieChart.Data(k, v.size())));
		return series;
	}

	private Series<Long, Long> createPauseDistributionSeries(final String name, List<GCEvent> events) {
		final XYChart.Series<Long, Long> series = new XYChart.Series<>();
		series.setName(name);
		final Map<Long, Long> map = events.stream().collect(Collectors.groupingBy(e -> duration(e), Collectors.counting()));
		map.forEach((k, v) -> series.getData().add(new XYChart.Data<>(k, v)));
		return series;
	}
	private long duration(GCEvent e) {
		final int fac = 100;
		final double d = (e.getTimeStats().getDuration() * 1000) / fac;
		final long i = Math.round(d);
		final long duration = i * fac;
		return duration;
	}

	private Series<Double, Double> createNewSeries(final String name, final List<GCEvent> events) {
		final XYChart.Series<Double, Double> series = new XYChart.Series<>();
		series.setName(name);
		events.forEach(e ->  series.getData().add(new XYChart.Data<>(e.getTimeStats().getElappsedTime(), e.getTimeStats().getDuration())));
		return series;
	}
}
