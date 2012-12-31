package at.rbg.registry.handler.internal;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.exception.GovernanceException;

public class DeleteDestinationEntryStrategy implements
		DestinationEntryProcessor {
	private static final Logger log = LoggerFactory.getLogger(DeleteDestinationEntryStrategy.class);

	@Override
	public void process(DestinationEntryProcessingInfo depi) {
		String[] entries;
		try {
			entries = depi.getGenericArtifact().getAttributes(
					depi.getAttrName());
			if (entries != null) {
				boolean toDelete = false;
				Set<String> newArray = new HashSet<String>();
				for (String entry : entries) {
					// we have to remove this one
					if (entry.endsWith(depi.getOldPath())) {
						toDelete = true;
						continue;
					}
					newArray.add(entry);
				}
				if (toDelete) {
					// we removed stuff
					try {
						depi.getGenericArtifact().setAttributes(
								depi.getAttrName(),
								newArray.toArray(new String[0]));
						depi.getGenericArtifactManager().updateGenericArtifact(
								depi.getGenericArtifact());
					} catch (GovernanceException e) {
						log.error(e.getMessage());
					}
				}
			}
		} catch (GovernanceException e) {
			log.error(e.getMessage());
		}
	}

}
