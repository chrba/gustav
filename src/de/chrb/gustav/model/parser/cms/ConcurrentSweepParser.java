package de.chrb.gustav.model.parser.cms;


import de.morten.model.message.Message;
import de.morten.model.parser.ActiveGCParser;

@ActiveGCParser
public class ConcurrentSweepParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-sweep";
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-concurrent-sweep:");
	}

}
