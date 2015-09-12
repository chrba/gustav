package de.chrb.gustav.model.gc;

import java.time.LocalDateTime;

/**
 * Represents timing statistics of one minor or major garbage collection
 * event of the JVM
 *
 * @author Christian Bannes
 */
public class GCTimeStats {
	private final LocalDateTime timestamp;
	private final double ellapsedTimeInSecs;
	private final double durationInSecs;

	/**
	 * Creates a time statistics object for one {@link GCEvent}
	 * @param timestamp the time the event occured
	 * @param ellapsedTimeInSecs the elappsed time since jvm startup
	 * @param durationInSecs the duration of a {@link GCEvent}
	 */
	public GCTimeStats(final LocalDateTime timestamp, final double ellapsedTimeInSecs, final double durationInSecs) {
		this.timestamp = timestamp;
		this.ellapsedTimeInSecs = ellapsedTimeInSecs;
		this.durationInSecs = durationInSecs;
	}

	/**
	 * Returns the duration of a GC event
	 *
	 * @return the duration of a GC event in msecs
	 */
	public double getDuration() {
		return this.durationInSecs;
	}

	/**
	 * The time when an GC event occures
	 * @return
	 */
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Returns the elappsed time since jvm startup
	 *
	 * @return the elappsed time
	 */
	public double getElappsedTime() {
		return this.ellapsedTimeInSecs;
	}
}
