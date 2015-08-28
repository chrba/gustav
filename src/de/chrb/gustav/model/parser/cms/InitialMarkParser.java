package de.chrb.gustav.model.parser.cms;



import javax.annotation.RegEx;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.model.gc.GCMemStats;
import de.morten.model.gc.GCTimeStats;
import de.morten.model.message.Message;
import de.morten.model.parser.AbstractParser;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.parser.Patterns;

/**
 * Initial mark indicates the start of the CMS garbage collection. It is the first of 7 phases.
 * The initial mark phase does not do any garbage collection, it just marks root
 * references, i.e. no garbage is collected in this phase.
 *
 * To mark all root references gc needs to stop the world.
 *
 * Example log line:
 *
 * 2012-11-14T21:01:37.171+0100: 1256.127:  [GC	[1 CMS-initial-mark: 3166449K(6291456K)] \
 * 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]
 *
 *
 * 3166449K is the occupancy of tenured (old gen) at start of the initial mark phase.
 * 6291456K is the total tenured (old gen) capacity.
 * 3644014K is the occupancy of the heap.
 * 8912896K is the total heap capacity.
 *
 *
 * @author Christian Bannes
 */
@ActiveGCParser
public class InitialMarkParser extends AbstractParser {

	private final static Regex INITIAL_MARK = createInitialMarkPattern();
	@Inject private Event<InitialMarkGCEvent> event;

	@Override
	public boolean isMultiLine() {
		return true;
	}

	@Override
	public boolean startParsing(final Message message) {
		return message.text().contains("CMS-initial-mark");
	}

	@Override
	public boolean inlineDetected(final Message message) {
		return !message.text().trim().endsWith("]");
	}

	@Override
	protected Regex pattern() {
		return INITIAL_MARK;
	}

	/**
	 * Creates the pattern that matches a ParNew entry
	 * @return the created {@link RegEx}
	 */
	private static Regex createInitialMarkPattern() {
		return  RegexBuilder.create()
		.regex(Patterns.timestampOfGcStart()).any()
		.regex("#tenured", Patterns.memStatOccupancyBeforeAndTotal()).any()
		.regex("#heap", Patterns.memStatOccupancyBeforeAndTotal()).any()
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
	protected void publishEventFor(final Match match, final Message message) {

		final GCMemStats tenuredState = currentStateTenured(match);
		final GCMemStats heapState = currentStateHeap(match);
		final GCTimeStats timeStats = readTimeStats(match);

		final InitialMarkGCEvent initialMarkEvent = new InitialMarkGCEvent("InitialMark", timeStats, tenuredState, heapState, message.correlationId());

		this.event.fire(initialMarkEvent);
	}

	/**
	 * Reads the younggen change from the match
	 * @param match
	 * @return
	 */
	private GCMemStats currentStateTenured(final Match match)
	{
		final int occupancyTenured = Integer.valueOf(match.getByName("tenured->occupancyPriorGc"));
		final int totalTenuredCapacity = Integer.valueOf(match.getByName("tenured->occupancyAfterGc"));
		final GCMemStats tenuredState = new GCMemStats(occupancyTenured, totalTenuredCapacity);
		return tenuredState;
	}

	private GCMemStats currentStateHeap(final Match match)
	{
		final int occupancyHeap = Integer.valueOf(match.getByName("heap->occupancyPriorGc"));
		final int totalHeapCapacity = Integer.valueOf(match.getByName("heap->occupancyAfterGc"));
		final GCMemStats heapState = new GCMemStats(occupancyHeap, totalHeapCapacity);
		return heapState;
	}



}