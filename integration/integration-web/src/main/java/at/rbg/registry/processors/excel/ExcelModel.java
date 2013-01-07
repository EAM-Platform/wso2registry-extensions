package at.rbg.registry.processors.excel;

import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Table;

/** 
 * This is definied by an Excel sheet 
 *
 */
public class ExcelModel extends AbstractExcelModel {
	private Application srcApp ;
	private Application destApp ;
	private ApplicationService srcArtifact;
	private ApplicationService destArtifact ;
	private Table table ; // src uses table (instead of destination)
	
	private boolean valid = false;
	
	public ExcelModel() {
	}
	
	public ApplicationService getSrcArtifact() {
		return srcArtifact;
	}

	public void setSrcArtifact(ApplicationService srcArtifact) {
		this.srcArtifact = srcArtifact;
	}

	public ApplicationService getDestArtifact() {
		return destArtifact;
	}

	public void setDestArtifact(ApplicationService destArtifact) {
		this.destArtifact = destArtifact;
	}

	public Application getSrcApp() {
		return srcApp;
	}

	public Application getDestApp() {
		return destApp;
	}

	
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public boolean isValid() {
		// at least these must be filled
		if (isFilled(this.srcArtifact) &&
				(isFilled(this.destArtifact) ||	
						isFilled(this.table))) {
			valid = true;
		}
		return valid;
	}
	

	@Override
	public String toString() {
		return "ExcelModel [srcApp=" + srcApp + ", destApp=" + destApp
				+ ", srcArtifact=" + srcArtifact + ", destArtifact="
				+ destArtifact + ", table=" + table + "]";
	}

	
}
