package de.chrb.gustav.model.gc;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

/**
 * Represents one garbage collection event of the
 * parallel minor garbage collector (PsYoungGen). The
 * PsYoungGen collector is not a stop-the-world collector, it runs
 * in parallel with the application threads.
 *
 * @author Christian Bannes
 */
@Immutable
public class MinorGCEvent extends AbstractGCEvent {

	/**
	 * Creates a minor gc event
	 *
	 * @param name the name of the minor gc event
	 * @param timeStats timing statistics
	 * @param memStats memory statistics
	 */
	public MinorGCEvent(final String name, final GCTimeStats timeStats, final GCMemStats memStats) {
		super(name, timeStats, Optional.of(memStats));
	}
	/**
	 * This garbage collector runs in parallel with the application
	 * threads.
	 * @return always false
	 */
	@Override
	public boolean isStopTheWorld() {
		return false;
	}

	/**
	 * @return always true
	 */
	@Override
	public boolean isMinor() {
		return true;
	}
}
