package de.chrb.gustav.model.parser;


import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;

/***
 * Utility class providing common regex patterns
 *
 * @author Christian Bannes
 */
public class Patterns {

	public static Regex date() {
		return RegexBuilder.create()
				.number("#year").constant("-").number("#month").constant("-").number("#day")
				.build();
	}

	public static Regex time() {
		return RegexBuilder.create()
				.number("#hour").constant(":").number("#minute").constant(":").number("#secs")
				.build();
	}

	public static Regex memStatOccupancyBeforeAfterAndTotal() {
		return RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K->").number("#occupancyAfterGc").pattern("K\\(").number("#totalSize").constant("K)")
				.build();
	}

	/**
	 * Matches at end of line on the total duration of the gc. This is printed at the end of
	 * the last line of the gc log.
	 *
	 * Example: The regex will match on "0.38 secs" when the line ends with
	 * 		    [Times: user=2.33 sys=0.26, real=0.38 secs]
	 *
	 * @return the regex
	 */
	public static Regex endOfLastLineTotalGCDuration() {
		return RegexBuilder.create()
			.pattern("[^\\d\\.]")
			.number("#duration")
			.pattern("\\s*")
			.constant("secs]")
			.build();
	}

	/**
	 * Every gc line starts the the timestamp of the garbage collection (which is optional, may not
	 * be present if the flag -XX:+PrintGCDateStamps is not given), followed by time since jvm startup.
	 *
	 * Example:  The regex will match if the line starts with 2012-11-14T20:43:06.188+0100: 145.144
	 * 			 Here 2012-11-14T20:43:06.188 is the timestamp of the gc start and 145.144 is the
	 * 			 time since jvm startup.
	 *
	 * @return the regex
	 */
	public static Regex timestampOfGcStart() {
		return RegexBuilder.create()
		 		.optional("#timestamp")
				.regex("#date", Patterns.date())
				.constant("T")
				.regex("#time", Patterns.time())
				.constant("+").number().constant(": ")
			.end()
			.number("#timeSinceStartup")
			.build();
	}

	/**
	 * Example: 3166449K(6291456K)
	 * @return
	 */
	public static Regex memStatOccupancyBeforeAndTotal() {
		return RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K(").number("#occupancyAfterGc").constant("K)")
				.build();
	}


}
