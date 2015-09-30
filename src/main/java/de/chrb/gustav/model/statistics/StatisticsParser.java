package de.chrb.gustav.model.statistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;

@Immutable
public class StatisticsParser {
	private final ParserRegistry parserRegistry = new ParserRegistry();
	private final Map<String, List<GCEvent>> eventsByName;
	private final List<Statistics> statistics;

	public StatisticsParser(final GCFile gcFile) {
		final List<GCEvent> events = parserRegistry.parse(gcFile.toFile());
		this.eventsByName = events.stream()
				.collect(Collectors.groupingBy(e -> e.getName()));

		this.statistics = eventsByName.entrySet().stream()
				.map( e -> new Statistics(gcFile.getName(), e.getKey(), e.getValue(), events))
				.collect(Collectors.toList());
	}

}
