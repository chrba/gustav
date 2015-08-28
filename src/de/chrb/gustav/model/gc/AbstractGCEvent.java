package de.chrb.gustav.model.gc;


import java.util.Objects;

import de.chrb.gustav.model.message.CorrelationId;


public abstract class  AbstractGCEvent implements GCEvent{

	/** time statistics of this gc event (timestamp, duration, ...) */
	private GCTimeStats timeStats;
	/** the name of the garbage collector (e.g. PSYoungGen, OldGC, ...) */
	private final String name;
	private CorrelationId correlationId;

	public AbstractGCEvent(final String name, final GCTimeStats timeStats, final CorrelationId correlationId) {
		this.name = Objects.requireNonNull(name);
		this.timeStats = Objects.requireNonNull(timeStats);
		this.correlationId = Objects.requireNonNull(correlationId);
	}

	public GCTimeStats getTimeStats() {
		return this.timeStats;
	}

	public String getName() {
		return this.name;
	}

	public CorrelationId getCorrelationId() {
		return this.correlationId;
	}

}
