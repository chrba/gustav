package de.chrb.gustav.model.parser;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.message.MessageConsumer;


/**
 * The entry point of gc parsing. It delegates to gc parsers that are
 * annotated with {@link ActiveGCParser}.
 *
 * @author Christian Bannes
 */
public class GCParser {
	private List<MessageConsumer> parsers;

	public GCParser(final List<MessageConsumer> parsers) {
		this.parsers = parsers;
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

	private Optional<GCEvent> parse(final String line) {
		final Optional<MessageConsumer> parser = this.parsers.stream()
				.filter(p -> p.consume(line))
				.findFirst();

		parser.ifPresent(p ->
			parsers.stream()
				.filter(o -> (o != p))
				.forEach(MessageConsumer::reset));

		return parser.flatMap(MessageConsumer::dequeue);
	}


}
