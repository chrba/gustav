package de.chrb.gustav.model.parser.cms;

import de.chrb.gustav.model.gc.AbstractGCEvent;
import de.chrb.gustav.model.gc.GCMemStats;
import de.chrb.gustav.model.gc.GCTimeStats;

public class InitialMarkGCEvent extends AbstractGCEvent {

	final GCMemStats tenuredState;
	final GCMemStats heapState;

	public InitialMarkGCEvent(final String name, final GCTimeStats timeStats, final GCMemStats tenuredState, final GCMemStats heapState) {
		super(name, timeStats);
		this.tenuredState = tenuredState;
		this.heapState = heapState;
	}

	@Override
	public boolean isStopTheWorld() {
		return true;
	}
}
