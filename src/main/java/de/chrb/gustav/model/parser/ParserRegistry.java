package de.chrb.gustav.model.parser;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.parser.cms.ConcurrentMarkParser;
import de.chrb.gustav.model.parser.cms.ConcurrentResetParser;
import de.chrb.gustav.model.parser.cms.ConcurrentSweepParser;
import de.chrb.gustav.model.parser.cms.PreCleanParser;
import de.chrb.gustav.model.parser.parnew.ParNewParser;


/**
 * The entry point of gc parsing. It delegates to gc parsers that are
 * annotated with {@link ActiveGCParser}.
 *
 * Singleton implementation
 *
 * @author Christian Bannes
 */
@Immutable
public final class ParserRegistry {
	private static Logger LOG = LoggerFactory.getLogger(ParserRegistry.class);
	private static final List<GCParser> parsers =  Arrays.asList(
			new ParNewParser(),
			new ConcurrentMarkParser(),
			new ConcurrentResetParser(),
			new ConcurrentSweepParser(),
			new PreCleanParser());

	private static final ParserRegistry INSTANCE = new ParserRegistry();

	/**
	 * Returns the singleton instance of the {@link ParserRegistry}
	 *
	 * @return the singleton
	 */
	public static ParserRegistry instance() {
		return INSTANCE;
	}
	/**
	 * Parses the lines of the given buffered reader. During the parse operation every recognised
	 * garbace collection event (like a minor gc or full gc) will result in a fired {@link GCEvent} that
	 * can be observed by any CDI enabled object. Every fired event is correlated with the given correlation id.
	 *
	 * @param correlationId the correlation id that will be passed to every fired gc event
	 * @param reader usually contains the content of a gc file written by the JVM.
	 *
	 * @throws IOException
	 */
	public List<GCEvent> parse(final File file)  {
		LOG.debug("Parsing file {}", file.getName());;
		try(Stream<String> lines = Files.lines(file.toPath())) {
			return lines.map(this::parse)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toList());
		}
		catch(IOException e) {
			return Collections.emptyList();
		}
	}

	/**
	 * Parses a single gc log entry
	 * @param line the gc log entry
	 * @return the gc event of absent
	 */
	private Optional<GCEvent> parse(final String line) {
		final Optional<GCParser> parser = parsers.stream()
				.filter(p -> p.consume(line))
				.findFirst();

		parser.ifPresent(p ->
			parsers.stream()
				.filter(o -> (o != p))
				.forEach(GCParser::reset));

		return parser.flatMap(GCParser::dequeue);
	}


}
