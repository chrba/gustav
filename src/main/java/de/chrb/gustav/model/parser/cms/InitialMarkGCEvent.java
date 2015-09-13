package de.chrb.gustav.model.parser.cms;

import java.util.Optional;

import de.chrb.gustav.model.gc.AbstractGCEvent;
import de.chrb.gustav.model.gc.CombinedGCMemStats;
import de.chrb.gustav.model.gc.GCTimeStats;

public class InitialMarkGCEvent extends AbstractGCEvent {

	public InitialMarkGCEvent(final String name, final GCTimeStats timeStats, final CombinedGCMemStats memStats) {
		super(name, timeStats, Optional.of(memStats));
	}

	@Override
	public boolean isStopTheWorld() {
		return true;
	}

	@Override
	public boolean isMinor() {
		return false;
	}
}
