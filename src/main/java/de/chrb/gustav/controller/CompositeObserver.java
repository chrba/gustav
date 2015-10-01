package de.chrb.gustav.controller;

import java.util.List;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

public class CompositeObserver implements GCResultObserver {
	private List<GCResultObserver> controllers;

	public CompositeObserver(final List<GCResultObserver> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void observe(GCAnalyzeResult result) {
		this.controllers.forEach(c -> c.observe(result));
	}

	@Override
	public void remove(String fromFileName) {
		this.controllers.forEach(c -> c.remove(fromFileName));
	}
}
