package de.chrb.gustav.model.parser.cms;


import de.morten.model.gc.AbstractGCEvent;
import de.morten.model.gc.GCMemStats;
import de.morten.model.gc.GCTimeStats;
import de.morten.model.message.CorrelationId;

public class InitialMarkGCEvent extends AbstractGCEvent {

	final GCMemStats tenuredState;
	final GCMemStats heapState;

	public InitialMarkGCEvent(final String name, final GCTimeStats timeStats, final GCMemStats tenuredState, final GCMemStats heapState,
			final CorrelationId correlationId) {
		super(name, timeStats, correlationId);
		this.tenuredState = tenuredState;
		this.heapState = heapState;
	}

	@Override
	public boolean isStopTheWorld() {
		return true;
	}
}
