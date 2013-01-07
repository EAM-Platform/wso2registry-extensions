package at.rbg.registry.processors;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

import at.rbg.registry.communication.RegistryFactory;

public class WsdlProcessor implements Processor {
	private static final Logger log = LoggerFactory
			.getLogger(WsdlProcessor.class);

	private RegistryFactory registryFactory;
	private String archiveMediaType;

	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}

	public void setArchiveMediaType(String archiveMediaType) {
		this.archiveMediaType = archiveMediaType;
	}

	@Override
	public void process(Exchange ex) throws Exception {
		registryFactory.checkAlive();

		Registry reg = (Registry) registryFactory.getRemoteRegistryInstance();
		Message msg = ex.getIn();
		String filename = (String) msg.getHeader(Exchange.FILE_NAME);
		InputStream in = (InputStream) msg.getBody();
		Resource r = reg.newResource();
		r.setContentStream(in);
		r.setMediaType(archiveMediaType);
		reg.put("/" + filename, r);

		log.info("File " + filename + " processed.");
	}
}