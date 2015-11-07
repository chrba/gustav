package de.chrb.gustav.controller;

import java.util.List;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

/**
 * Implementation of the composite pattern. Represents a
 * collection of {@link GCResultObserver}s.
 *
 * @author Christian Bannes
 */
public class CompositeObserver implements GCResultObserver {
	private List<GCResultObserver> controllers;

	/**
	 * Ctor
	 * @param controllers a reference to {@link GCResultObserver}s
	 */
	public CompositeObserver(final List<GCResultObserver> controllers) {
		this.controllers = controllers;
	}

	/**
	 * Registers the gc result to all underlying {@link GCResultObserver}s
	 *
	 * @param result the result to register
	 */
	@Override
	public void observe(GCAnalyzeResult result) {
		this.controllers.forEach(c -> c.observe(result));
	}

	/**
	 * Unregisters the given gc file name from all underlying {@link GCResultObserver}
	 *
	 * @param fromFileName references the file to unregister
	 */
	@Override
	public void remove(String fromFileName) {
		this.controllers.forEach(c -> c.remove(fromFileName));
	}
}
