package de.chrb.gustav.model.gc;


import java.util.Objects;
import java.util.Optional;


/**
 * Provides common implementations for all {@link GCEvent}s
 *
 * @author Christian Bannes
 */
public abstract class  AbstractGCEvent implements GCEvent{

	private GCTimeStats timeStats;
	private final String name;
	private Optional<CombinedGCMemStats> memStats;

	public AbstractGCEvent(final String name, final GCTimeStats timeStats, final Optional<CombinedGCMemStats> memStats) {
		this.name = Objects.requireNonNull(name);
		this.timeStats = Objects.requireNonNull(timeStats);
		this.memStats = Objects.requireNonNull(memStats);
	}

	@Override
	public GCTimeStats getTimeStats() {
		return this.timeStats;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public final Optional<CombinedGCMemStats> getMemStats() {
		return this.memStats;
	}


}
