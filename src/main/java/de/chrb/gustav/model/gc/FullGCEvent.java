package de.chrb.gustav.model.gc;


/**
 * Represents one garbage collection event of the  full gc collector. The FULL GC collector
 * is single threaded and runs in a stop-the-world fashion i.e. all application threads
 * are stopped while it is running.
 *
 * @author Christian Bannes
 */
public class FullGCEvent extends AbstractGCEvent {
	/** the unique name of this garbage collector */
	private final static String NAME = "FULL GC";

	/**
	 * Creates a new GC event that represents a FULL GC
	 *
	 * @param timeStats statistics of this GC event
	 */
	public FullGCEvent(final GCTimeStats timeStats) {
		super(NAME, timeStats);
	}

	/**
	 * The FULL GC collector uses stop-the-world, i.e. all application
	 * threads are stopped while this gc was running
	 * @return always true
	 */
	@Override
	public boolean isStopTheWorld() {
		return true;
	}

}
