package de.chrb.gustav.model.message;

import java.util.Optional;

import de.chrb.gustav.model.gc.GCEvent;

/**
 * A message endpoint, stateful: saves the messages until
 * {@link #reset()} is called.
 *
 * @author Christian Bannes
 */
public interface  GCParser {
	/**
	 * Tries to consume the given message. If the message is not accepted (not consumed)
	 * then false will be returned, else true.
	 *
	 * @param message the message to consume
	 * @return true, if the message was consumed, else false.
	 */
	boolean consume(String message);

	/**
	 * After a message have been successfully consumed a {@link GCEvent} may be available.
	 * More specifically, for multi-line consumers a GCEvent will only be available after
	 * the last line has been consumed. For one-line consumers a GCEvent will always be available
	 * after a line has been consumed.
	 *
	 * @return maybe an {@link GCEvent}
	 */
	Optional<GCEvent> dequeue();

	/**
	 * Resets this message endpoint
	 */
	void reset();
}
