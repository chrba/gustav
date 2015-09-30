package de.chrb.gustav.model.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.gc.GCEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

//one file will result in
// 1 statistics object > ObservableList<Statistics>
// XYChart.Series for each barchart > ObservableList<Series> for each barchart
// 1 Tab > ObservableList<Tab>
public class StatisticsAnalyzer2 {
	//private final Map<String, Statistics> statisticsByName;
	private final ObservableList<Statistics> statistics;
	private final Map<String, List<GCEvent>> eventsByName;
	private final List<GCEvent> sortedEvents;

	public StatisticsAnalyzer2(final GCFile gcFile, final List<GCEvent> events) {
		this.sortedEvents = new ArrayList<>(events);
		this.sortedEvents.sort((e1, e2) -> Double.compare(e1.getTimeStats().getElappsedTime(), e2.getTimeStats().getElappsedTime()));

		this.eventsByName = events.stream()
				.collect(Collectors.groupingBy(e -> e.getName()));

		this.statistics = FXCollections.observableArrayList(eventsByName.entrySet().stream()
			.map( e -> new Statistics(gcFile.getName(), e.getKey(), e.getValue(), events))
			.collect(Collectors.toList()));


	}
	public List<Statistics> statisticsList() {
		return	this.statistics;
	}

	private void test() {

		ObservableList<Series<String, Integer>> ob1 = null;
		ObservableList<Statistics> ob2 = null;

		ob2.removeIf(p -> p.fileName.equals("x"));
		ob1.removeIf(s -> s.getName().equals("name"));
	}

	private void test2(ObservableList<Series<?, ?>> x) {
		x.removeIf(s -> s.getName().equals("name"));
	}

	public XYChart.Series<String, Integer> createNumSeries() {
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("test");
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

	public List<XYChart.Series<Long, Long>> createPauseDistribution() {

		final List<XYChart.Series<Long, Long>> serieses = new ArrayList<>();
		this.eventsByName.forEach((k, v) -> serieses.add(createPauseDistributionSeries(v)));
		return serieses;
	}

	private Series<Long, Long> createPauseDistributionSeries(List<GCEvent> events) {
		XYChart.Series<Long, Long> series = new XYChart.Series<>();
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
	public List<PieChart.Data> createPieChartData() {
		final List<PieChart.Data> series = new ArrayList<>();

		this.eventsByName.forEach((k, v) -> series.add(new PieChart.Data(k, v.size())));

		return series;
	}
}
