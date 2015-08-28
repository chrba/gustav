package de.chrb.gustav.model.message;


import java.util.Objects;


/**
 * A message that can be consumed by a {@link MessageConsumer}.
 * Multiple Messages can be correlated by using the same {@link CorrelationId}
 * for those messages.
 *
 * During gc log file analysis every line is represented as a message. The
 * correlation id is used to correlate messages that are part of the same
 * log file.
 *
 * @author Christian Bannes
 */
public class Message {
	private final String text;
	private final CorrelationId correlationId;

	/**
	 * Creates a message without a {@link CorrelationId}, meaning
	 * this message should be seen in isolation and is not correlated
	 * with other messages.
	 *
	 * @param text the message text
	 */
	public Message(final String text) {
		this(text, new NullCorrelationId());
	}

	public boolean correlates(final Message that) {
		return this.correlationId.equals(that.correlationId());
	}

	/**
	 * Creates a message with a {@link CorrelationId}, meaning
	 * this message should be correlated to other messages with the
	 * same correlation is.
	 *
	 * @param text the message text
	 */
	public Message(final String text, final CorrelationId correlationId) {
		this.text = Objects.requireNonNull(text);
		this.correlationId = Objects.requireNonNull(correlationId);
	}

	/**
	 * Returns the message text
	 *
	 * @return the message text
	 */
	public String text() {
		return text;
	}

	/**
	 * Creates a new message with the given text that is correlated
	 * with this message
	 *
	 * @param text the text of the newly created message
	 *
	 * @return a correlated message
	 */
	public Message createCorrelatedMessage(final String text) {
		return new Message(text, this.correlationId);
	}

	/**
	 * Creates a new text with the given text added
	 *
	 * @param text the text to add
	 * @return a new message
	 */
	public Message addText(final String text) {
		return new Message(this.text + text, this.correlationId);
	}

	/**
	 * Returns the {@link CorrelationId}
	 *
	 * @return the correlation id
	 */
	public CorrelationId correlationId() {
		return correlationId;
	}

	@Override public int hashCode() {
		return Objects.hash(this.correlationId, this.text);
	}

	@Override public boolean equals(final Object other) {
		if(this == other) return true;
		if(!(other instanceof Message)) return false;
		final Message that = (Message)other;

		return Objects.equals(this.correlationId, that.correlationId) &&
				Objects.equals(this.text, that.text);
	}

	@Override public String toString() {
		return "Message[" + this.text + ", " + this.correlationId + "]";
	}
}
