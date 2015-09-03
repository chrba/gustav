package de.chrb.gustav.model.gc;


import java.util.List;


public class GCStatistics {
	private int min;
	private int max;

	public GCStatistics(final List<GCEvent> gcEvents) {
	}

	public int getMin() {
		return this.min;
	}

	public int getMax() {
		return this.max;
	}

}
