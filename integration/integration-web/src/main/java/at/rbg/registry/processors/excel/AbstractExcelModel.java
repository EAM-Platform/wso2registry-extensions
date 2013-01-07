package at.rbg.registry.processors.excel;

import at.rbg.registry.model.RBGGovernanceArtifact;

public abstract class AbstractExcelModel {
	
	protected boolean isFilled(RBGGovernanceArtifact rbg) {
		if (rbg!=null) {
			if ((rbg.getName()!=null && !rbg.getName().isEmpty()) && 
				(rbg.getVersion()!=null && !rbg.getVersion().isEmpty())) {
				return true;
			}
		}
		return false;
	}
	
	abstract public boolean isValid();
}
