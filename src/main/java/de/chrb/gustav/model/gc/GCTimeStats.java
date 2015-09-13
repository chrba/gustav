package de.chrb.gustav.model.gc;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * Represents timing statistics of one minor or major garbage collection
 * event of the JVM
 *
 * @author Christian Bannes
 */
@Immutable
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
		Preconditions.checkArgument(ellapsedTimeInSecs >= 0);
		Preconditions.checkArgument(durationInSecs >= 0);
		this.timestamp = Objects.requireNonNull(timestamp);
		this.ellapsedTimeInSecs = ellapsedTimeInSecs;
		this.durationInSecs = durationInSecs;
	}

	/**
	 * Returns the duration of a GC event
	 *
	 * @return the duration of a GC event in msecs, always >= 0
	 */
	public double getDuration() {
		return this.durationInSecs;
	}

	/**
	 * The time when an GC event occures
	 * @return
	 */
	@Nonnull public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Returns the elappsed time since jvm startup
	 *
	 * @return the elappsed time, always >= 0
	 */
	public double getElappsedTime() {
		return this.ellapsedTimeInSecs;
	}
}
