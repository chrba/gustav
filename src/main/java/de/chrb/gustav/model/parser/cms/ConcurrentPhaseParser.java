package de.chrb.gustav.model.parser.cms;



import de.chrb.gustav.model.gc.GCEvent;
import de.chrb.gustav.model.gc.GCTimeStats;
import de.chrb.gustav.model.parser.AbstractParser;
import de.chrb.gustav.model.parser.Patterns;
import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;

/**
 * CMS has six phases in total, four of them are concurrent phases, namely
 *
 * 	- concurrent mark
 *  - preclean
 *  - concurrent sweep
 *  - concurrent reset
 *
 * For every of the four phase a subclass from this class will be provided.
 *
 * An example log line for a concurrent mark phase:
 *
 * 012-11-14T21:01:38.684+0100: 1257.639: [CMS-concurrent-mark: 1.142/1.148 secs] [Times: user=6.76 sys=0.15, real=1.14 secs]
 *
 * @author Christian Bannes
 */
public abstract class ConcurrentPhaseParser extends AbstractParser {

	@Override
	protected GCEvent createGCEventFrom(Match match, String message) {
		final GCTimeStats timeStats = readTimeStats(match);
    	return new ConcurrentPhaseEvent(getName(), timeStats);
	}

	/**
	 * Must be overritten by subclasses to provide the name of the
	 * concurrent pase.
	 *
	 * @return the name of the concurrent phase.
	 */
	protected abstract String getName();

	@Override
	protected boolean isMultiLine() {
		return false;
	}

	@Override
	protected Regex pattern() {
		return RegexBuilder.create()
			.regex(Patterns.timestampOfGcStart()).any()
			.number("#elapsedTime").constant("/").number("#wallClockTime").constant(" secs]").any()
			.regex(Patterns.endOfLastLineTotalGCDuration()).any()
			.build();
	}

	@Override
	protected boolean definitelyNotLastLine(String message) {
		return false;
	}



}
