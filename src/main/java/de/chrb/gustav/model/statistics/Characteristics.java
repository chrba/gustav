package de.chrb.gustav.model.statistics;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.chrb.gustav.model.gc.GCEvent;

public class Characteristics {

	private List<GCEvent> allEvents;

	public Characteristics(final List<GCEvent> allEvents) {
		this.allEvents = allEvents;
	}
	public String text() {
		/*
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Tuning Data (average):\n");
		buffer.append("----------------\n\n");
		buffer.append("Allocation Rate: " + this.allocationRate() + " MB/sec\n");
		buffer.append("Promotion Rate: " + "20 MB/sec\n");
		buffer.append("Time between two minor collections: 20 sec\n");
		buffer.append("Time between two major collections: 4.3 hours\n");

		return buffer.toString();
		*/
		final String s = "" +
		"<div style='line-height: 25px;color: #444; font-family: Gill Sans, Verdana;'><h3>Tuning Data</h3>" +
		"<div style='font-size:11pt'><ul>" +
		"<li>Promotion Rate: 234 Mb/s</li>" +
		"<li>Allcocation Rate: 23 Mb/s</li>" +
		"<li>Live Data: 234 Mb/s</li>" +
		"<li>Time between minor collections: 100 s</li>" +
		"<li>Time between major collections: 32 s</li>" +
		"</ul></div></div>";

		return s;
	}

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

		//1. get tenured after fullgc

		//2. get tenured at initial mark

		//2-1 = garbage added

		//calc garbage added / time between full gcs


		return 0D;
	}

	/**
	 * Determines how many memory is allocated per sec (on average)
	 *
	 * Metrics: [mb] / [secs]
	 *
	 * @return the allocation rate, always >= 0
	 */
	public long allocationRate() {
		final Optional<GCEvent> minor = this.allEvents.stream().filter(e -> e.isMinor()).findFirst();
		if(!minor.isPresent() || !minor.get().getMemStats().isPresent()) return 0;

		final int totalYoungGenCapacity = minor.get()
					.getMemStats().get()
					.getGenerationChange()
					.totalCapacity();

		final Stream<GCEvent> minorEvents =  this.allEvents.stream().filter(e -> e.isMinor());
		return Math.round(totalYoungGenCapacity / avgTimeBetweenEvents(minorEvents) / 1000.0);
	}

	private int avgTimeBetweenEvents(final Stream<GCEvent> minorEvents) {
		final double[] elapsedTimes = minorEvents
			.mapToDouble(e -> e.getTimeStats().getElappsedTime())
			.toArray();

		int s = 0;
		for(int i = 1; i < elapsedTimes.length; i++) {
			s += (elapsedTimes[i] - elapsedTimes[i-1]);
		}
		return s / elapsedTimes.length;
	}

	public int liveDataSize() {
		return 0;
	}

	public int timeBetwenTwoYoungGen() {
		return 0;
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
