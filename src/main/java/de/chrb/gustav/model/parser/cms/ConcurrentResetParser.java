package de.chrb.gustav.model.parser.cms;


public class ConcurrentResetParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-reset";
	}

	@Override
	protected boolean startParsing(String message) {
		return message.contains("CMS-concurrent-reset:");
	}

}
