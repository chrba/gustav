package de.chrb.gustav.model.gc;

import java.time.LocalDateTime;

/**
 * Represents timing statistics of one minor or major garbage collection
 * event of the JVM
 *
 * @author Christian Bannes
 */
public class GCTimeStats {
	/** the date and time when the GC was done */
	private final LocalDateTime timestamp;
	/** elappsed time since jvm startup */
	private final double ellapsedTimeInSecs;
	/** the duration of the gc in msecs */
	private final double durationInSecs;

	public GCTimeStats(final LocalDateTime timestamp, final double ellapsedTimeInSecs, final double durationInSecs) {
		this.timestamp = timestamp;
		this.ellapsedTimeInSecs = ellapsedTimeInSecs;
		this.durationInSecs = durationInSecs;
	}

	/**
	 * @return the duration of a GC event in msecs
	 */
	public double getDuration() {
		return this.durationInSecs;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	public double getElappsedTime() {
		return this.ellapsedTimeInSecs;
	}
}
