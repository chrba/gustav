package de.chrb.gustav.model.parser.cms;


public class PreCleanParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-preclean";
	}

	@Override
	protected boolean startParsing(String message) {
		return message.contains("CMS-concurrent-preclean:");
	}

}
