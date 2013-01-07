package at.rbg.registry.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailureResponseProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(FailureResponseProcessor.class);


	@Override
	public void process(org.apache.camel.Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
				Exception.class);
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage());
		sb.append("\nBODY: ");
		sb.append(body);
		exchange.getIn().setBody(sb.toString());
		
		if (exchange.getException()!= null)
			log.error(exchange.getException().getMessage());
	}

}
