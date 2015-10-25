package de.chrb.gustav.model.statistics;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.chrb.gustav.model.gc.GCEvent;



/**
 * Provides methods for statistics calculation
 *
 * @author Christian Bannes
 */
public class Stats {

	/**
	 * Private default constructor
	 */
	private Stats() {
		//prevent instantiation
	}

	/**
	 * Calculates the variance over the given values
	 *
	 * @param values the values
	 * @return the variance
	 */
	public static double variance(final Collection<Long> values) {
		final double avg = Stats.average(values);

		return values.stream()
			.map(v -> Math.pow(v - avg, 2))
			.mapToDouble(Double::doubleValue)
			.sum() / values.size();
	}

	/**
	 * Calculates the standard deviation over the given values
	 *
	 * @param values thee values
	 * @return the standard deviation
	 */
	public static double standardDeviation(final Collection<Long> values) {
		return Math.sqrt(Stats.variance(values));
	}

	/**
	 * Calculates the average value over the given values
	 *
	 * @param values the values
	 * @return the average
	 */
	public static double average(final Collection<Long> values) {
		return values.stream()
			.mapToLong(Long::longValue)
			.average()
			.orElse(0.0);
	}

	/**
	 * Returns the median of the given collection
	 *
	 * @param values the values
	 * @return the median
	 */
	public static long median(final Collection<Long> values) {
		if(values.isEmpty()) return 0;
		final List<Long> list = values.stream()
		        .sorted()
		        .collect(Collectors.toList());

		final int mid = values.size() / 2;
		return list.get(mid);
	}

	/**
	 * Calculates the percentage of the given events from all events
	 * @param events the events
	 * @param allEvents all events
	 * @return the percentage value between 0 and 100
	 */
	public static double numPerc(final List<GCEvent> events, final List<GCEvent> allEvents) {
		return allEvents.isEmpty()? 0 : events.size() / (double)allEvents.size() * 100;
	}

	public static double gcSecsPercent(final double totalSecs, final double totalSecsAllEvents) {
		return totalSecsAllEvents == 0? 0 : totalSecs / totalSecsAllEvents * 100;
	}


	public static double sumSecs(final List<GCEvent> vals) {
		return vals.stream()
			.map(v -> v.getTimeStats().getDuration())
			.mapToDouble(Double::doubleValue)
			.sum();
	}

	public static double overhead(final double totalSecs, final List<GCEvent> allEvents) {
		final double totalElapsedTimeSinceMeasurement = Stats.calcTotalTime(allEvents);

		return totalElapsedTimeSinceMeasurement == 0? 0
				: totalSecs / totalElapsedTimeSinceMeasurement * 100;
	}

	/**
	 * The time between the first gc event and the last gc event
	 * @param events all events
	 * @return the time in secs
	 */
	private static double calcTotalTime(List<GCEvent> events) {
		final List<Double> times = events.stream()
				.map(event -> event.getTimeStats().getElappsedTime())
				.collect(Collectors.toList());

		return  Collections.max(times)-Collections.min(times);
	}
}
