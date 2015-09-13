package de.chrb.gustav.model.gc;


import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;


/**
 * Provides common implementations for all {@link GCEvent}s
 *
 * @author Christian Bannes
 */
public abstract class  AbstractGCEvent implements GCEvent{

	private GCTimeStats timeStats;
	private final String name;
	private Optional<GCMemStats> memStats;

	public AbstractGCEvent(final String name, final GCTimeStats timeStats, final Optional<GCMemStats> memStats) {
		this.name = Objects.requireNonNull(name);
		this.timeStats = Objects.requireNonNull(timeStats);
		this.memStats = Objects.requireNonNull(memStats);
	}

	@Override
	@Nonnull public GCTimeStats getTimeStats() {
		return this.timeStats;
	}

	@Override
	@Nonnull public String getName() {
		return this.name;
	}

	@Override
	@Nonnull public final Optional<GCMemStats> getMemStats() {
		return this.memStats;
	}


}
