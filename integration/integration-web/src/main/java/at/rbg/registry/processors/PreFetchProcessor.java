package at.rbg.registry.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.communication.RegistryInteraction;
import at.rbg.registry.communication.query.QueryRequest;


public class PreFetchProcessor implements Processor {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryInteraction.class);

	@Override
	public void process(Exchange ex) throws Exception {
		log.info("Prefetching artifacts");

		QueryRequest qr = new QueryRequest();
		Message mess = new DefaultMessage();
		mess.setBody(qr);
		ex.setIn(mess);
	}

}
