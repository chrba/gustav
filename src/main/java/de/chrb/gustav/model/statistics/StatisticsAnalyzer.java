package de.chrb.gustav.model.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.chrb.gustav.model.gc.GCEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class StatisticsAnalyzer {
	//private final Map<String, Statistics> statisticsByName;
	private final List<Statistics> statistics;
	private final Map<String, List<GCEvent>> eventsByName;
	private final List<GCEvent> sortedEvents;

	public StatisticsAnalyzer(final List<GCEvent> events) {
		this.sortedEvents = new ArrayList<>(events);
		this.sortedEvents.sort((e1, e2) -> Double.compare(e1.getTimeStats().getElappsedTime(), e2.getTimeStats().getElappsedTime()));

		this.eventsByName = events.stream()
				.collect(Collectors.groupingBy(e -> e.getName()));

		this.statistics = eventsByName.entrySet().stream()
			.map( e -> new Statistics(e.getKey(), e.getValue(), events))
			.collect(Collectors.toList());
	}
	public List<Statistics> statisticsList() {
		return	this.statistics;
	}

	public XYChart.Series<String, Integer> createNumSeries() {
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.num.get())));
		return series;
	}

	public XYChart.Series<String, Double> createNumPercSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.numPerc.get())));
		return series;
	}

	public XYChart.Series<String, Double> createSecsSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.secs.get())));
		return series;
	}

	public XYChart.Series<String, Double> createSecsPercSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.secsPerc.get())));
		return series;
	}
	public XYChart.Series<String, Double> createOverheadSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.overhead.get())));
		return series;
	}
	public XYChart.Series<String, Double> createAvgSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.avg.get())));
		return series;
	}
	public XYChart.Series<String, Double> createSigmaSeries() {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.sigma.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMaxSeries() {
		XYChart.Series<String, Long> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.max.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMinSeries() {
		XYChart.Series<String, Long> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.min.get())));
		return series;
	}
	public XYChart.Series<String, Long> createMedianSeries() {
		XYChart.Series<String, Long> series = new XYChart.Series<>();
		this.statistics.forEach(s -> series.getData().add(new XYChart.Data<>(s.name.get(), s.median.get())));
		return series;
	}

	public List<XYChart.Series<Double, Double>> createTimeline() {

		final List<XYChart.Series<Double, Double>> serieses = new ArrayList<>();
		this.eventsByName.forEach((k, v) -> serieses.add(createNewSeries(v)));
		return serieses;
	}
	private Series<Double, Double> createNewSeries(List<GCEvent> events) {
		XYChart.Series<Double, Double> series = new XYChart.Series<>();
		events.forEach(e ->  series.getData().add(new XYChart.Data<>(e.getTimeStats().getElappsedTime(), e.getTimeStats().getDuration())));
		return series;
	}
}
