package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

public interface GCResultObserver {
	public void observe(GCAnalyzeResult result);
	public void remove(final String fromFileName);
}
