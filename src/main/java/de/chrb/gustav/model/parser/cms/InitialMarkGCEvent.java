package de.chrb.gustav.model.parser.cms;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import de.chrb.gustav.model.gc.AbstractGCEvent;
import de.chrb.gustav.model.gc.GCMemStats;
import de.chrb.gustav.model.gc.GCTimeStats;

@Immutable
public final class InitialMarkGCEvent extends AbstractGCEvent {

	public InitialMarkGCEvent(final String name, final GCTimeStats timeStats, final GCMemStats memStats) {
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
