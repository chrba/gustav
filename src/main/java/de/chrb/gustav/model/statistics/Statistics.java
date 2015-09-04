package de.chrb.gustav.model.statistics;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.chrb.gustav.model.gc.GCEvent;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * Collection of statistic information provided to the front end.
 *
 * @author Christian Bannes
 */
public class Statistics {
	public final ReadOnlyStringProperty name;
	public final ReadOnlyIntegerProperty num;
	public final ReadOnlyDoubleProperty numPerc;
	public final ReadOnlyDoubleProperty secs;
	public final ReadOnlyDoubleProperty secsPerc;
	public final ReadOnlyDoubleProperty overhead;
	public final ReadOnlyDoubleProperty avg;
	public final ReadOnlyDoubleProperty sigma;
	public final ReadOnlyLongProperty max;
	public final ReadOnlyLongProperty min;
	public final ReadOnlyLongProperty median;


	public Statistics(final String name, final List<GCEvent> events, final List<GCEvent> allEvents) {
		this.name = new ReadOnlyStringWrapper(name).getReadOnlyProperty();
		this.num = new ReadOnlyIntegerWrapper(events.size()).getReadOnlyProperty();
		this.numPerc = new ReadOnlyDoubleWrapper(numPerc(events, allEvents)).getReadOnlyProperty();

		final double totalSecs = sumSecs(events);
		final double secsAll = sumSecs(allEvents);

		final List<Long> secsList = readSecsList(events);

		this.secs = new ReadOnlyDoubleWrapper(totalSecs).getReadOnlyProperty();
		this.secsPerc = new ReadOnlyDoubleWrapper(GCSecsPercent(totalSecs, secsAll)).getReadOnlyProperty();
		this.overhead = new ReadOnlyDoubleWrapper(overhead(totalSecs, allEvents)).getReadOnlyProperty();

		this.avg = new ReadOnlyDoubleWrapper(Stats.average(secsList)).getReadOnlyProperty();
		this.sigma = new ReadOnlyDoubleWrapper(Math.sqrt(Stats.variance(secsList))).getReadOnlyProperty();

		this.max = new ReadOnlyLongWrapper(secsList.isEmpty()? 0 : Collections.max(secsList)).getReadOnlyProperty();
		this.min = new ReadOnlyLongWrapper(secsList.isEmpty()? 0 : Collections.min(secsList)).getReadOnlyProperty();
		this.median = new ReadOnlyLongWrapper(secsList.isEmpty()? 0 : Stats.median(secsList)).getReadOnlyProperty();
	}

	private List<Long> readSecsList(final List<GCEvent> events) {
		return events.stream()
				.map(e -> Math.round(e.getTimeStats().getDuration()))
				.collect(Collectors.toList());
	}

	private double numPerc(final List<GCEvent> events, final List<GCEvent> allEvents) {
		return allEvents.isEmpty()? 0 :events.size() / (double)allEvents.size() * 100;
	}

	private double sumSecs(final List<GCEvent> vals) {
		return vals.stream()
			.map(v -> v.getTimeStats().getDuration())
			.mapToDouble(Double::doubleValue)
			.sum();
	}

	private double GCSecsPercent(final double totalSecs, final double totalSecsAllEvents) {
		return totalSecsAllEvents == 0? 0 : totalSecs / totalSecsAllEvents * 100;
	}

	public double overhead(final double totalSecs, final List<GCEvent> allEvents) {
		final double totalElapsedTimeSinceMeasurement = calcTotalTime(allEvents);

		return totalElapsedTimeSinceMeasurement == 0? 0
				: totalSecs / totalElapsedTimeSinceMeasurement * 100;
	}

	private double calcTotalTime(List<GCEvent> events) {

		final List<Double> times = events.stream()
				.map(event -> event.getTimeStats().getElappsedTime())
				.collect(Collectors.toList());

		return  Collections.max(times)-Collections.min(times);
	}



}