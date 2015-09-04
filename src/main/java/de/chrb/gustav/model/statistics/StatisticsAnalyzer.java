package de.chrb.gustav.model.statistics;

import java.util.List;
import java.util.stream.Collectors;

import de.chrb.gustav.model.gc.GCEvent;

public class StatisticsAnalyzer {
	public List<Statistics> create(final List<GCEvent> events) {
		return 	events.stream()
					.collect(Collectors.groupingBy(e -> e.getName()))
					.entrySet()
					.stream()
					.map( k -> new Statistics(k.getKey(), k.getValue(), events))
					.collect(Collectors.toList());
	}
}
