package de.chrb.gustav.model.gc;


import java.util.Objects;



public abstract class  AbstractGCEvent implements GCEvent{

	/** time statistics of this gc event (timestamp, duration, ...) */
	private GCTimeStats timeStats;
	/** the name of the garbage collector (e.g. PSYoungGen, OldGC, ...) */
	private final String name;

	public AbstractGCEvent(final String name, final GCTimeStats timeStats) {
		this.name = Objects.requireNonNull(name);
		this.timeStats = Objects.requireNonNull(timeStats);
	}

	public GCTimeStats getTimeStats() {
		return this.timeStats;
	}

	public String getName() {
		return this.name;
	}


}
