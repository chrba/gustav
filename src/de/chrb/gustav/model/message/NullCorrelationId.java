package de.chrb.gustav.model.message;


/**
 * Reprensents an non existing correlation id
 *
 * @author Christian Bannes
 */
public class NullCorrelationId extends CorrelationId {

	public NullCorrelationId() {
		super("null");
	}

}
