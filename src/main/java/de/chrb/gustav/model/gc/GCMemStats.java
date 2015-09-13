package de.chrb.gustav.model.gc;

import javax.annotation.concurrent.Immutable;

/**
 * Statistics of a generational memory (younggen, oldgen or permgen) before and after a gc.
 *
 * @author Christian Bannes
 */
@Immutable
public class GCMemStats {

	private final int occupancyBeforeGc;
	private final int occupancyAfterGc;
	private final int totalCapacity;

	/**
	 * Use this constructor then occupancy before and after GC is the same.
	 *
	 * @param occupancy the total occupancy
	 * @param totalCapacity the total capacity
	 */
	public GCMemStats(final int occupancy, final int totalCapacity) {
		this.occupancyAfterGc = occupancy;
		this.occupancyBeforeGc = occupancy;
		this.totalCapacity = totalCapacity;
	}

	/**
	 * The occupancy and total size of a generational memory (younggen, oldgen or permgen). The
	 * values must all belong to the same generational memory.
	 *
	 * @param occupancyBeforeGc the occupancy before GC in bytes
	 * @param occupancyAfterGc the occupancy after GC in bytes
	 * @param totalCapacity the total capacity
	 */
	public GCMemStats(final int occupancyBeforeGc, final int occupancyAfterGc, final int totalCapacity) {
		this.occupancyAfterGc = occupancyAfterGc;
		this.occupancyBeforeGc = occupancyBeforeGc;
		this.totalCapacity = totalCapacity;
	}

	/**
	 * Returns the occupancy of a generational memory (younggen, oldgen or permgen)
	 * before gc
	 *
	 * @return the occupancy in bytes
	 */
	public int occupancyBeforeGc() {
		return this.occupancyBeforeGc;
	}

	/**
	 * Returns the occupancy of a generational memory (younggen, oldgen or permgen)
	 * after gc
	 *
	 * @return the occupancy in bytes
	 */
	public int occupancyAfterGc() {
		return this.occupancyAfterGc;
	}

	/**
	 * The total size of the generational memory (youngge, oldgen or permgen)
	 *
	 * @return the total capacity in bytes
	 */
	public int totalCapacity() {
		return this.totalCapacity;
	}


}
