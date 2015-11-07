package de.chrb.gustav.model.parser;



import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.gc.GCTimeStats;
import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;

/**
 * Convenient implementation of a parser. A gc parser should always
 * extend this class.
 *
 * Not threadsafe.
 *
 * @author Christian Bannes
 */
public abstract class AbstractParser implements GCParser {

	private String buffer = new String();
	private LinkedList<GCEvent> queue = new LinkedList<>();

	@Override
	public boolean consume(final String message)
	{
		Objects.requireNonNull(message);
		return isMultiLine()? consumeMultiLine(message) : consumeSingleLine(message);
	}

	/**
	 * Resets the underlying buffer. Must be called before a new gc event parsing
	 * starts.
	 */
	@Override
	public void reset() {
		this.buffer = new String();
	}

	/**
	 * Publish an event the given match
	 *
	 * @param match the match
	 * @param message the message that matches on some parser
	 */
	protected abstract GCEvent createGCEventFrom(Match match, String message);

	/**
	 * Determines if an gc event that is supposed to be detected can
	 * consist of multiple log lines
	 *
	 * @return true, if the gc event can consist of multiple log lines, else false
	 */
	protected abstract boolean isMultiLine();

	/**
	 * A parser begins consuming messages when it detects a start marker.
	 *
	 * @param message the message containing one log line
	 * @return true if the start marker is detected, else false.
	 */
	protected abstract boolean startParsing(String message);

	/**
	 * The regex pattern that describes the match of an event the parser is responsible
	 * for, e.g. the regex to detect a FullGC.
	 *
	 * @return the regex
	 */
	protected abstract Regex pattern();

	/**
	 * Returns true if the parser is sure that this message cannot be
	 * the last line that makes up one event
	 *
	 * @param message the message
	 * @return true, if the message is definitely not the last line of
	 * 			a gc event
	 */
	protected abstract boolean definitelyNotLastLine(String message);

	/**
	 * Used to parse a single line gc event
	 * @param message the gc log entry
	 * @return true, if it was possible to parse the line, else false
	 */
	private boolean consumeSingleLine(final String message) {
		if(startParsing(message))
			return match(message);

		return false;
	}

	 /**
     * Used to parse a multi line gc event
     * @param a single line gc log entry
     * @return true, if it was possible to parse the line, else false
     */
	private boolean consumeMultiLine(final String message) {
		final boolean startDetected = startParsing(message);
		if(startDetected && alreadyStarted())
			this.buffer = new String();

		if(alreadyStarted() || startParsing(message)) {
			if(!buffer.isEmpty()) this.buffer += "\n";
			this.buffer += message;

			if(definitelyNotLastLine(message)) return false;

			final boolean success = match(this.buffer);
			if(success) reset();

			return true;
		}

		return false;
	}

	/**
	 * Checks if this parser matches the gc log entry
	 * @param message the gc log entry
	 * @return true if this parser could parse the entry
	 */
	private boolean match(String message) {
		final Regex pattern = pattern();
		final Match match = pattern.match(message);
		if(match.empty()) return false;

		final GCEvent event = createGCEventFrom(match, message);
		this.queue.add(event);
		return true;
	}

	/**
	 * True if the parser already parsed some lines before (only relevant for
	 * multi line gc events)
	 *
	 * @return true if the parser already parsed some lines
	 */
	private boolean alreadyStarted() {
		return this.buffer.length() > 0;
	}

	protected GCTimeStats readTimeStats(final Match match) {
	    //*****************************************************
	    //TODO: this is not correct! Do not use null values, use Optional instead
	    //*****************************************************
		LocalDateTime startup = null;
		if(match.getByName("timestamp") != null) {
			final String date = match.getByName("timestamp->date");
			final String time = match.getByName("timestamp->time");
			startup = LocalDateTime.parse(date+"T"+time);
		}

		final double ellapsedTimeInSecs = Double.valueOf(match.getByName("timeSinceStartup"));

		final double durationInSecs = Double.valueOf(match.getByName("duration"));
		final GCTimeStats timeStats = new GCTimeStats(startup, ellapsedTimeInSecs, durationInSecs);
		return timeStats;
	}


	@Override
	public Optional<GCEvent> dequeue() {
		return Optional.ofNullable(this.queue.poll());
	}

}
