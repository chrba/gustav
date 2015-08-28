package de.chrb.gustav.model.gc;

import de.chrb.gustav.model.message.CorrelationId;

/**
 * A {@link CorrelationId} is used to correlate gc events
 * that are part of the same log file.
 *
 * @author Christian Bannes
 */
public interface CorrelationIdentifiable {
	public CorrelationId getCorrelationId();
}
