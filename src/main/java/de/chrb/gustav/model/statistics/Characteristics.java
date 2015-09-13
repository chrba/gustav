package de.chrb.gustav.model.statistics;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.chrb.gustav.model.gc.GCEvent;

public class Characteristics {

	/**
	 * Determines the garbage in mb that is moved to the old gen
	 * per secs (on average).
	 *
	 * Metrics: [mb] / [secs]
	 *
	 * @return the promotion rate, always >= 0
	 */
	public double promotionRate(final List<GCEvent> allEvents) {
		//how fillhow full after gc

		return 0D;
	}

	/**
	 * Determines how many memory is allocated per sec (on average)
	 *
	 * Metrics: [mb] / [secs]
	 *
	 * @return the allocation rate, always >= 0
	 */
	public double allocationRate(final List<GCEvent> allEvents) {
		//1. get young gen capacity
		final Stream<GCEvent> minorEvents =  allEvents.stream().filter(e -> e.isMinor());
		final Optional<GCEvent> minor = minorEvents.findFirst();
		if(!minor.isPresent() || !minor.get().getMemStats().isPresent()) return 0.0;

		final int totalYoungGenCapacity = minor.get()
					.getMemStats().get()
					.getGenerationChange()
					.totalCapacity();

		return totalYoungGenCapacity / avgTimeBetweenEvents(minorEvents);
	}

	private double avgTimeBetweenEvents(final Stream<GCEvent> minorEvents) {
		final double[] elapsedTimes = minorEvents
			.mapToDouble(e -> e.getTimeStats().getElappsedTime())
			.toArray();

		int s = 0;
		for(int i = 1; i < elapsedTimes.length; i++) {
			s += (elapsedTimes[i] - elapsedTimes[i-1]);
		}
		return s / elapsedTimes.length;
	}

	//Checks:
	// oldgen >= 1.5 * livedata size
	//younggen >= 10% * totalheap

	//allocation rate : younggen cap / time between younggen
	//promotion rate: garbage moved to old gen per sec (on average)

	//live data size: occupancy of oldgen after fullgc


	//surviver death ratio: size of surviver n / size of surviver n-1
	//old gen colletion time: time between two old gc time
	//young gen collection time: time between two younggen
	//old gen buf: the promotion rate * the maximum Old Gen collection


}
