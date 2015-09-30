package de.chrb.gustav.model.statistics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.gc.AnalyseResult;
import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.ParserRegistry;

/**
 * Represents the analyze result of one gc file.
 *
 * @author Christian Bannes
 */
@Immutable
public class GCAnalyzeResult {
	private final Map<String, List<GCEvent>> eventsByName;
	private final List<Statistics> statistics;
	private final GCFile gcFile;
	private final List<GCEvent> events;

	/**
	 * Constructor only used internally, use {@link #from(GCFile)}
	 *
	 * @param eventsByName
	 *            the events by gc name
	 * @param statistics
	 *            the statistics
	 */
	private GCAnalyzeResult(final GCFile gcFile, final Map<String, List<GCEvent>> eventsByName,
			final List<Statistics> statistics, final List<GCEvent> events) {
		this.eventsByName = eventsByName;
		this.statistics = statistics;
		this.gcFile = gcFile;
		this.events = events;
	}

	/**
	 * Parses the given gc file and creates a {@link AnalyseResult} object
	 *
	 * @param gcFile
	 *            the gc file to parse
	 * @return the {@link AnalyseResult}
	 */
	public static GCAnalyzeResult from(final GCFile gcFile) {
		Objects.requireNonNull(gcFile);
		final List<GCEvent> events = ParserRegistry.instance().parse(gcFile.toFile());
		final Map<String, List<GCEvent>> eventsByName = events.stream()
				.collect(Collectors.groupingBy(e -> e.getName()));

		final List<Statistics> statistics = eventsByName.entrySet().stream()
				.map(e -> new Statistics(gcFile.getName(), e.getKey(), e.getValue(), events))
				.collect(Collectors.toList());

		return new GCAnalyzeResult(gcFile, eventsByName, statistics, events);
	}

	/**
	 * Gets the analyzed statistics of a gc file
	 *
	 * @return the list of statistics.
	 */
	public List<Statistics> getStatistics() {
		return this.statistics;
	}

	/**
	 * Gets all found events by GC name
	 *
	 * @return all events
	 */
	public Map<String, List<GCEvent>> eventsByName() {
		return this.eventsByName;
	}

	public ChartSeries getChartSeries() {
		return new ChartSeries(this);
	}

	public GCFile getGCFile() {
		return this.gcFile;
	}

	public List<GCEvent> events() {
		return this.events;
	}

}
