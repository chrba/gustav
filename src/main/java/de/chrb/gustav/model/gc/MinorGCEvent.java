package de.chrb.gustav.model.gc;

/**
 * Represents one garbage collection event of the
 * parallel minor garbage collector (PsYoungGen). The
 * PsYoungGen collector is not a stop-the-world collector, it runs
 * in parallel with the application threads.
 *
 * @author Christian Bannes
 */
public class MinorGCEvent extends AbstractGCEvent {
	/** the change of young generation during this gc event */
	private GCMemStats youngGenChange;
	/** the change ofl old generation during this gc event */
	private GCMemStats oldGenChange;


	public MinorGCEvent(final String name, final GCTimeStats timeStats, final GCMemStats youngGenChange, final GCMemStats oldGenChange) {
		super(name, timeStats);
		this.youngGenChange = youngGenChange;
		this.oldGenChange = oldGenChange;
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

	public GCMemStats getYoungGenChange() {
		return this.youngGenChange;
	}

	public GCMemStats getOldGenChange() {
		return this.oldGenChange;
	}



}
