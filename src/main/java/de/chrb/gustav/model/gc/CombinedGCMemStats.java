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
public class CombinedGCMemStats {
	private final GCMemStats heap;
	private final GCMemStats generation;

	/**
	 * Creates a memory statistics containing the change of garbage
	 * in the heap and in one generation
	 *
	 * @param generation
	 * @param heap
	 */
	public CombinedGCMemStats(final GCMemStats generation, final GCMemStats heap) {
		this.generation = Objects.requireNonNull(generation);
		this.heap = Objects.requireNonNull(heap);
	}

	/**
	 * Returns the memory total heap statistics of one GC event, i.e.
	 * how many garbage occupied the heap before and after the GC event.
	 *
	 * @return the mem stats
	 */
	@Nonnull public GCMemStats getHeapChange() {
		return heap;
	}

	/**
	 * Return the memory change of one generation e.g. the change in the young gen
	 * or the old gen
	 *
	 * @return the memory statistics of one generation
	 */
	@Nonnull public GCMemStats getGenerationChange() {
		return generation;
	}
}
