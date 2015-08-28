package de.chrb.gustav.model.parser;



import java.util.Objects;

import de.chrb.gustav.model.message.Message;
import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;

/**
 * Convenient implementation of a parser. A gc parser should always
 * extend this class.
 *
 * @author Christian Bannes
 */
public abstract class AbstractParser implements MessageConsumer {

	private String buffer = new String();

	@Override
	public boolean consume(final Message message)
	{
		Objects.requireNonNull(message);
		return isMultiLine()? consumeMultiLine(message) : consumeSingleLine(message);
	}

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
	protected abstract void publishEventFor(Match match, Message message);

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
	protected abstract boolean startParsing(Message message);

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
	protected abstract boolean inlineDetected(Message message);


	private boolean consumeSingleLine(final Message message)
	{
		if(startParsing(message))
			return match(message);

		return false;
	}

	private boolean consumeMultiLine(final Message message) {
		final boolean startDetected = startParsing(message);
		if(startDetected && alreadyStarted())
			this.buffer = new String();

		if(alreadyStarted() || startParsing(message))
		{
			if(!buffer.isEmpty()) this.buffer += "\n";
			this.buffer += message.text();

			if(inlineDetected(message)) return false;

			final boolean success = match(message.createCorrelatedMessage(this.buffer));
			if(success) reset();

			return true;
		}

		return false;
	}


	private boolean match(Message message) {
		final Regex pattern = pattern();
		double d = System.currentTimeMillis();
		final Match match = pattern.match(message.text());
		if(match.empty()) return false;

		publishEventFor(match, message);
		return true;
	}


	private boolean alreadyStarted() {
		return this.buffer.length() > 0;
	}

	protected GCTimeStats readTimeStats(final Match match)
	{
		DateTime startup = null;
		if(match.getByName("timestamp") != null)
		{
			final String date = match.getByName("timestamp->date");
			final String time = match.getByName("timestamp->time");
			DateTimeFormatter parser = ISODateTimeFormat.dateTime();
			startup = parser.parseDateTime(date+"T"+time + "+0100");
		}

		final double ellapsedTimeInSecs = Double.valueOf(match.getByName("timeSinceStartup"));

		final double durationInSecs = Double.valueOf(match.getByName("duration"));
		final GCTimeStats timeStats = new GCTimeStats(startup, ellapsedTimeInSecs, durationInSecs);
		return timeStats;
	}


}
