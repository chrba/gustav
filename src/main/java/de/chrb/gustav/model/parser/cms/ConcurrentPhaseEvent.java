package de.chrb.gustav.model.parser.cms;

import de.chrb.gustav.model.gc.AbstractGCEvent;
import de.chrb.gustav.model.gc.GCTimeStats;

public class ConcurrentPhaseEvent extends AbstractGCEvent {

	public ConcurrentPhaseEvent(final String name, final GCTimeStats timeStats) {
		super(name, timeStats);
	}

	@Override
	public boolean isStopTheWorld() {
		return false;
	}

}
