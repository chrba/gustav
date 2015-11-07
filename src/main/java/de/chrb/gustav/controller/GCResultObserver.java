package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;

/**
 * Implementation of the observer pattern. Used to data bind a
 * gc analyze result to gui components.
 *
 * @author Christian Bannes
 */
public interface GCResultObserver {
    /**
     * Register a data binding of the gc analyze result
     *
     * @param result a gc analyze result
     */
	void observe(GCAnalyzeResult result);

	/**
	 * Unregisteres the data binding for the given file
	 *
	 * @param fromFileName references the file to unregister
	 */
	void remove(final String fromFileName);
}
