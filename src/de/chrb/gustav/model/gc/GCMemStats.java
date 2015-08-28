package de.chrb.gustav.model.gc;


/**
 * Statistics of a generational memory (younggen, oldgen or permgen) before and after a gc.
 *
 * @author Christian Bannes
 */
public class GCMemStats {
	/** the occupancy of a generational memory (younggen, oldgen or permgen) before gc */
	private final int occupancyBeforeGc;
	/** the occupancy of a generational memory (younggen, oldgen or permgen) after gc */
	private final int occupancyAfterGc;
	/** not the occupancy but the total size of the generational memory (youngge, oldgen or permgen) */
	private final int totalCapacity;

	public GCMemStats(final int occupancy, final int totalCapacity) {
		this.occupancyAfterGc = occupancy;
		this.occupancyBeforeGc = occupancy;
		this.totalCapacity = totalCapacity;
	}

	/**
	 * The occupancy and total size of a generational memory (younggen, oldgen or permgen). The
	 * values must all belong to the same generational memory.
	 */
	public GCMemStats(final int occupancyBeforeGc, final int occupancyAfterGc, final int totalCapacity) {
		this.occupancyAfterGc = occupancyAfterGc;
		this.occupancyBeforeGc = occupancyBeforeGc;
		this.totalCapacity = totalCapacity;
	}

	public int occupancyBeforeGc() {
		return this.occupancyBeforeGc;
	}

	public int occupancyAfterGc() {
		return this.occupancyAfterGc;
	}

	public int totalCapacity() {
		return this.totalCapacity;
	}


}
