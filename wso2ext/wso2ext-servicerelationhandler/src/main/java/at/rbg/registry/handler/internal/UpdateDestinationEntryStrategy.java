package at.rbg.registry.handler.internal;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.exception.GovernanceException;

public class UpdateDestinationEntryStrategy implements
		DestinationEntryProcessor {
	private static final Logger log = LoggerFactory.getLogger(UpdateDestinationEntryStrategy.class);

	@Override
	public void process(DestinationEntryProcessingInfo depi) {
		String[] entries;
		try {
			entries = depi.getGenericArtifact().getAttributes(depi.getAttrName());
			if (entries != null) {
				String newEntry = null;
				boolean addedEntry = false;
				Set<String> newArray = new HashSet<String>();
				for (String entry : entries) {

					newArray.add(entry);
					// an unclean delete left us already that entry...
					if (entry.endsWith(depi.getNewPath())) {
						continue;
					}
					if (entry.endsWith(depi.getOldPath())) {
						newEntry = entry.replace(depi.getOldPath(),
								depi.getNewPath());
						newArray.add(newEntry);
						addedEntry = true;
					}
				}
				if (addedEntry) {
					// there is one more now.. update
					if (entries.length < newArray.size()) {
						try {
							depi.getGenericArtifact().setAttributes(depi.getAttrName(),
									newArray.toArray(new String[0]));
						} catch (GovernanceException e) {
							log.error(e.getMessage());
						}
						depi.getGenericArtifactManager().updateGenericArtifact(depi.getGenericArtifact());
					}
				}
			}
		} catch (GovernanceException e) {
			log.error(e.getMessage());
		}
	}

}
