package at.rbg.registry.processors.excel;

import at.rbg.registry.model.HostProgram;

/**
 * this class models the PAD hostprogram excel
 * @author ubba
 *
 */
public class HostModel extends AbstractExcelModel {
	private HostProgram hostprogram;
	private boolean valid = false;

	public HostProgram getHostprogram() {
		return hostprogram;
	}

	public void setHostprogram(HostProgram hostprogram) {
		this.hostprogram = hostprogram;
	}

	@Override
	public boolean isValid() {
		// at least these must be filled
		if (isFilled(this.hostprogram)) {
			// hostprogs need a applCode bec. of the path in registry
			if (this.hostprogram.getApplCode()!=null) {
				valid = true;
			}
		}
		return valid;
	}
	

}
