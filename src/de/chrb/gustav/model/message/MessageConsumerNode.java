package de.chrb.gustav.model.message;


class MessageConsumerNode {

	private MessageConsumer data;
	private MessageConsumerNode next;
	private MessageConsumerNode prev;

	public MessageConsumerNode(MessageConsumer data)
	{
		this.data = data;
		this.next = null;
	}

	public void add(MessageConsumer data)
	{
		final MessageConsumerNode currentNode = new MessageConsumerNode(data);
		final MessageConsumerNode lastNode = getLastNode();
		lastNode.next = currentNode;
		currentNode.prev = lastNode;
	}

	private MessageConsumerNode getLastNode() {
		MessageConsumerNode t = this;

		while(t.next != null)
			t = t.next;

		return t;
	}

	public void moveToEnd() {
		if(next == null)
			return;

		final MessageConsumerNode lastNode = getLastNode();
		if(lastNode == this) return;

		lastNode.next = this;


		this.next.prev = this.prev;
		this.prev.next = this.next;

		this.prev = lastNode;
		this.next = null;
	}

	public void resetTail()
	{
		MessageConsumerNode t = this.next;
		while(t != null) {
			t.data.reset();
			t = t.next;
		}
	}

	public boolean consume(final String message) {
		MessageConsumerNode t = this;
		boolean consumed = false;
		while(t != null) {
			consumed = t.data.consume(message);
			if(consumed) {
				t.resetTail();
				t.moveToEnd();
				break;
			}
			t = t.next;
		}
		return consumed;
	}


	@Override public String toString() {
		final String name = this.data.getClass().getSimpleName();
		return this.next != null? name + "->" + this.next.toString() : name;
	}
}
