package at.rbg.registry.processors.excel;

import at.rbg.registry.model.Infrastructure;
import at.rbg.registry.model.RBGGovernanceArtifact;

public class InfrastructureModel extends AbstractExcelModel {
	private Infrastructure infrastructure;
	private boolean valid = false;

	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(Infrastructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	@Override
	protected boolean isFilled(RBGGovernanceArtifact rbg) {
		if (rbg!=null && rbg instanceof Infrastructure) {
			Infrastructure inf = (Infrastructure) rbg;
			if ((inf.getName()!=null && !inf.getName().isEmpty()) && 
				(inf.getComponentnumber()!=null && !inf.getComponentnumber().isEmpty())) {
				// hm, that is the header.. ugly
				if (!"Komponentenname".equals(inf.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isValid() {
		if (isFilled(infrastructure)) {
			valid = true;
		}
		return valid;
	}

}
