package de.chrb.gustav.model.parser.cms;


public class ConcurrentSweepParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-sweep";
	}

	@Override
	protected boolean startParsing(String message) {
		return message.contains("CMS-concurrent-sweep:");
	}

}
