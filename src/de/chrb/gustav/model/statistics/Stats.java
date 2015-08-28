package de.chrb.gustav.model.statistics;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



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
	public static double average(final Collection<Long> values)
	{
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
	public static long median(final Collection<Long> values)
	{

		if(values.isEmpty()) return 0;

		final List<Long> list = new ArrayList<Long>(values);
		Collections.sort(list);

		final int mid = values.size() / 2;
		return list.get(mid);
	}
}
