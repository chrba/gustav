package de.chrb.gustav.model.gc;



import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * Represents the result of ONE log file analysis.
 */
public class AnalyseResult implements Serializable {
	private static final long serialVersionUID = -2983890629567244775L;
	private final String name;
	private final Map<String, List<GCEvent>> events;
	private final LocalDateTime date;

	/**
	 * Create a analyse result of one log file with all found gc events.
	 *
	 * @param nameSuffix the suffix to use for the name of this result. Will be used
	 * 				     in the front end.
	 * @param events all events of this result
	 */
	public AnalyseResult(final String name, final Map<String, List<GCEvent>> events) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(events);

		this.date = LocalDateTime.now();
		this.events = Collections.unmodifiableMap(events);
		this.name = name;
	}

	/**
	 * The unique name of this log file analyse result
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * The time this result was created
	 * @return the date time
	 */
	public String getDateTime() {
		return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	/**
	 * An unmodifiable view to gc events of one log file
	 *
	 * @return all found gc events of one log file
	 */
	public Map<String, List<GCEvent>> getEvents() {
		return this.events;
	}
}
