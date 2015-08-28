package de.chrb.gustav.model.message;




public class MessageConsumerChain {
	private final MessageConsumerNode root = new MessageConsumerNode(new NullTaskConsumer());
	public MessageConsumerChain(final CorrelationId correlationId, final Iterable<? extends MessageConsumer> list)
	{
		for(final MessageConsumer t : list)
		{
			this.root.add(t);
		}
	}

	public boolean consume(final Message message)
	{
		return this.root.consume(message);
	}

	public static class NullTaskConsumer implements MessageConsumer {
		@Override
		public boolean consume(final Message message) {
			return false;
		}

		@Override
		public void reset() {
		}
	}

}
