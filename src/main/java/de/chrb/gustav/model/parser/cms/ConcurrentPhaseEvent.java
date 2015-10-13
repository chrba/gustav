package de.chrb.gustav.model.parser.cms;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import de.chrb.gustav.model.gc.AbstractGCEvent;
import de.chrb.gustav.model.gc.GCTimeStats;

@Immutable
public class ConcurrentPhaseEvent extends AbstractGCEvent {

	public ConcurrentPhaseEvent(final String name, final GCTimeStats timeStats) {
		super(name, timeStats, Optional.empty());
	}

	/**
	 * The concurrent phase of CMS runs concurrently and does not
	 * stop the worlds
	 *
	 * @return always false
	 */
	@Override
	public boolean isStopTheWorld() {
		return false;
	}

	/**
	 * The ConcurrentPhaseEvent repesents one phase in the major GC of
	 * CMS.
	 *
	 * @return alwas false
	 */
	@Override
	public boolean isMinor() {
		return false;
	}
}
