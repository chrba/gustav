package de.chrb.gustav.model.message;



/**
 * A message endpoint, stateful: saves the messages until reset is
 * {@link #reset()} is called.
 *
 * @author Christian Bannes
 */
public interface  MessageConsumer {
	/**
	 * Tries to consume the given message. If the message is not accepted (not consumed)
	 * then false will be returned, else true.
	 *
	 * @param message the message to consume
	 * @return true, if the message was consumed, else false.
	 */
	boolean consume(Message message);

	/**
	 * Resets this message endpoint
	 */
	void reset();
}
