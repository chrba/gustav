package de.chrb.gustav.model.gc;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Memory statistics containing the change of garbage
 * in the heap and in one generation.
 *
 * @author Christian Bannes
 */
@Immutable
public final class GCMemStats {
	private final GCMemChange heapChange;
	private final GCMemChange generationChange;

	/**
	 * Creates a memory statistics containing the change of garbage
	 * in the heap and in one generation
	 *
	 * @param generationChange
	 * @param heapChange
	 */
	public GCMemStats(final GCMemChange generationChange, final GCMemChange heapChange) {
		this.generationChange = Objects.requireNonNull(generationChange);
		this.heapChange = Objects.requireNonNull(heapChange);
	}

	/**
	 * Returns the memory total heap statistics of one GC event, i.e.
	 * how many garbage occupied the heap before and after the GC event.
	 *
	 * @return the mem stats
	 */
	@Nonnull public GCMemChange getHeapChange() {
		return heapChange;
	}

	/**
	 * Return the memory change of one generation e.g. the change in the young gen
	 * or the old gen
	 *
	 * @return the memory statistics of one generation
	 */
	@Nonnull public GCMemChange getGenerationChange() {
		return generationChange;
	}
}
