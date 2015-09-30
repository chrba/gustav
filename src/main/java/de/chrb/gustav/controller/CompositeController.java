package de.chrb.gustav.controller;

import java.util.List;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

public class CompositeController implements Controller {
	private List<Controller> controllers;

	public CompositeController(final List<Controller> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void add(GCAnalyzeResult result) {
		this.controllers.forEach(c -> c.add(result));
	}

	@Override
	public void remove(String fromFileName) {
		this.controllers.forEach(c -> c.remove(fromFileName));
	}
}
