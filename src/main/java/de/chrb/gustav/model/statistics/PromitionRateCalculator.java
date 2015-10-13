package de.chrb.gustav.model.statistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.chrb.gustav.model.gc.GCEvent;

public class PromitionRateCalculator {

    /**
     * Determine how many garbage is produced between two major collections.
     *
     * @param allEvents all gc events
     * @return the promition rate, always >= 0
     */
    public double promitionRateOf(final List<GCEvent> allEvents) {
        if(true) return 0;
		final List<Double> garbage = new ArrayList<>();

		Double firstTenuredMeasureAfterGC = null;
		for(final Iterator<GCEvent> iter = allEvents.iterator(); iter.hasNext();) {
			final GCEvent e = iter.next();
			if(firstTenuredMeasureAfterGC != null && isStartOfMajorCollection(e)) {
			    garbage.add(getTenured(e) - firstTenuredMeasureAfterGC);
			}
			if(isEndOfMajorCollection(e)) {
			    firstTenuredMeasureAfterGC = null;
			}
			if(firstTenuredMeasureAfterGC == null) {
			    firstTenuredMeasureAfterGC = tryMeasureTenured(e);
			}
		}
		return garbage.stream()
		        .mapToDouble(Double::doubleValue)
		        .sum();
	}

	private Double tryMeasureTenured(GCEvent e) {
        // TODO Auto-generated method stub
        return null;
    }


    private double getTenured(GCEvent e) {
        //if(!e.getMemStats().isPresent()) throw new IllegalStateException("No memstat present");
		//e.getMemStats().get().getGenerationChange().occupancyBeforeGc();
		return 0;
	}

	private boolean isStartOfMajorCollection(GCEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

    private boolean isEndOfMajorCollection(GCEvent e) {
        // TODO Auto-generated method stub
        return false;
    }



}
