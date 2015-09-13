package de.chrb.gustav.model.gc;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * Statistics of a generational memory (younggen, oldgen or permgen) before and after a gc.
 *
 * @author Christian Bannes
 */
@Immutable
public class GCMemChange {

	private final int occupancyBeforeGc;
	private final int occupancyAfterGc;
	private final int totalCapacity;

	/**
	 * Use this constructor then occupancy before and after GC is the same.
	 *
	 * @param occupancy the total occupancy
	 * @param totalCapacity the total capacity
	 */
	public GCMemChange(final int occupancy, final int totalCapacity) {
		Preconditions.checkArgument(occupancy >= 0);
		Preconditions.checkArgument(totalCapacity >= 0);
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
	public GCMemChange(final int occupancyBeforeGc, final int occupancyAfterGc, final int totalCapacity) {
		Preconditions.checkArgument(occupancyBeforeGc >= 0);
		Preconditions.checkArgument(occupancyAfterGc >= 0);
		Preconditions.checkArgument(totalCapacity >= 0);

		this.occupancyAfterGc = occupancyAfterGc;
		this.occupancyBeforeGc = occupancyBeforeGc;
		this.totalCapacity = totalCapacity;
	}

	/**
	 * Returns the occupancy of a generational memory (younggen, oldgen or permgen)
	 * before gc
	 *
	 * @return the occupancy in bytes, always >= 0
	 */
	public int occupancyBeforeGc() {
		return this.occupancyBeforeGc;
	}

	/**
	 * Returns the occupancy of a generational memory (younggen, oldgen or permgen)
	 * after gc
	 *
	 * @return the occupancy in bytes, always >= 0
	 */
	public int occupancyAfterGc() {
		return this.occupancyAfterGc;
	}

	/**
	 * The total size of the generational memory (younggen, oldgen or permgen)
	 *
	 * @return the total capacity in bytes, always >= 0
	 */
	public int totalCapacity() {
		return this.totalCapacity;
	}


}
