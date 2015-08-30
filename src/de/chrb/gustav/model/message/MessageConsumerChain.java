package de.chrb.gustav.model.message;

import java.util.List;
import java.util.Optional;

import de.chrb.gustav.model.gc.GCEvent;

public class MessageConsumerChain {
	private final MessageConsumerNode root = new MessageConsumerNode(new NullTaskConsumer());
	public MessageConsumerChain(final List<MessageConsumer> list)
	{
		for(final MessageConsumer t : list)
		{
			this.root.add(t);
		}
	}

	public boolean consume(final String message)
	{
		return this.root.consume(message);
	}

	public static class NullTaskConsumer implements MessageConsumer {
		@Override
		public boolean consume(final String message) {
			return false;
		}

		@Override
		public void reset() {
		}

		@Override
		public Optional<GCEvent> dequeue() {
			return Optional.empty();
		}
	}

}
