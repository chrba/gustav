package de.chrb.gustav.model.parser.cms;


import de.morten.model.message.Message;
import de.morten.model.parser.ActiveGCParser;

public class PreCleanParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-preclean";
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-concurrent-preclean:");
	}

}
