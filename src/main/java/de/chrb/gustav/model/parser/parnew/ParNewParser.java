package de.chrb.gustav.model.parser.parnew;


import javax.annotation.RegEx;

import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.gc.GCMemStats;
import de.chrb.gustav.model.gc.GCTimeStats;
import de.chrb.gustav.model.gc.MinorGCEvent;
import de.chrb.gustav.model.parser.AbstractParser;
import de.chrb.gustav.model.parser.Patterns;
import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;


/**
 *
 * @author Christian Bannes
 */
public class ParNewParser extends AbstractParser {

	private final static Regex PAR_NEW = createParNewPattern();
	Object event;

	@Override
	public boolean isMultiLine() {
		return true;
	}

	@Override
	public boolean startParsing(final String message) {
		return message.contains("[ParNew");
	}

	@Override
	public boolean definitelyNotLastLine(final String message) {
		return !message.trim().endsWith("]");
	}

	@Override
	protected Regex pattern() {
		return PAR_NEW;
	}

	/**
	 * Creates the pattern that matches a ParNew entry
	 * @return the created {@link RegEx}
	 */
	private static Regex createParNewPattern() {
		return RegexBuilder.create()
				.regex(Patterns.timestampOfGcStart()).any()
				.regex("#eden", Patterns.memStatOccupancyBeforeAfterAndTotal()).any()
				.regex("#heap", Patterns.memStatOccupancyBeforeAfterAndTotal()).any()
				.regex(Patterns.eofTotalGCDuration()).any()
				.build();
	}

	/**
	 * This method is called when a match was created. The values can now be extracted
	 * from the match.
	 *
	 * This method creates a gc event and publishes the event using the
	 * {@link EventPublisher}.
	 */
	@Override
	protected GCEvent createGCEventFrom(final Match match, final String message) {

		final GCMemStats youngGenChange = readYoungGenChange(match);
		final GCMemStats oldGenChange = readOldGenChange(match);
		final GCTimeStats timeStats = readTimeStats(match);

		return new MinorGCEvent("ParNew", timeStats, youngGenChange, oldGenChange);
	}

	/**
	 * Reads the younggen change from the match
	 * @param match
	 * @return
	 */
	private GCMemStats readYoungGenChange(final Match match)
	{
		final int edenBefore = Integer.valueOf(match.getByName("eden->occupancyPriorGc"));
		final int edenAfter = Integer.valueOf(match.getByName("eden->occupancyAfterGc"));
		final int edenTotal = Integer.valueOf(match.getByName("eden->totalSize"));
		final GCMemStats youngGenChange = new GCMemStats(edenBefore, edenAfter, edenTotal);
		return youngGenChange;
	}

	private GCMemStats readOldGenChange(final Match match)
	{
		final int oldBefore = Integer.valueOf(match.getByName("heap->occupancyPriorGc"));
		final int oldAfter = Integer.valueOf(match.getByName("heap->occupancyAfterGc"));
		final int heapTotal = Integer.valueOf(match.getByName("heap->totalSize"));
		final GCMemStats oldGenChange = new GCMemStats(oldBefore, oldAfter, heapTotal);
		return oldGenChange;
	}



}