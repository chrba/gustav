package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

public interface Controller {
	public void add(GCAnalyzeResult result);
	public void remove(final String fromFileName);
}
