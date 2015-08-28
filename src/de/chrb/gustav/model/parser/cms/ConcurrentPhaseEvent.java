package de.chrb.gustav.model.parser.cms;

import de.chrb.gustav.model.gc.GCTimeStats;
import de.chrb.gustav.model.message.CorrelationId;

public class ConcurrentPhaseEvent extends AbstractGCEvent {

	public ConcurrentPhaseEvent(final String name, final GCTimeStats timeStats, final CorrelationId correlationId) {
		super(name, timeStats, correlationId);
	}

	@Override
	public boolean isStopTheWorld() {
		return false;
	}

}
