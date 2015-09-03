package de.chrb.gustav.model.statistics;

import java.util.List;

import de.chrb.gustav.model.gc.GCEvent;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * Collection of statistic information provided to the front end.
 *
 * @author Christian Bannes
 */
public class Statistics {
	public final ReadOnlyStringProperty fileName;
	public final ReadOnlyIntegerProperty num;
	public final ReadOnlyDoubleProperty numPerc;
	public final ReadOnlyDoubleProperty tocalGC;


	public Statistics(final String fileName, final String gcEventName, final List<GCEvent> events, final List<GCEvent> allEvents) {
		this.fileName = new ReadOnlyStringWrapper(fileName).getReadOnlyProperty();
		this.num = new ReadOnlyIntegerWrapper(events.size()).getReadOnlyProperty();
		this.numPerc = new ReadOnlyDoubleWrapper(numPerc(events, allEvents)).getReadOnlyProperty();
		this.tocalGC = new ReadOnlyDoubleWrapper(sumSecs(events)).getReadOnlyProperty();
	}

	private double numPerc(final List<GCEvent> events, final List<GCEvent> allEvents) {
		return allEvents.isEmpty()? 0 :events.size() / (double)allEvents.size() * 100;
	}

	private double sumSecs(final List<GCEvent> vals) {
		return vals.stream()
			.map(v -> v.getTimeStats().getDuration())
			.mapToDouble(Double::doubleValue)
			.sum();
	}
}