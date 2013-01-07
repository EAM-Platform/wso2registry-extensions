package at.rbg.registry.processors.excel;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.model.HostProgram;
import at.rbg.registry.model.LifecycleState;
import at.rbg.registry.model.ModelContainer;

public class HostModelAssembler extends AbstractModelAssembler {
	private static final Logger log = LoggerFactory.getLogger(HostModelAssembler.class);

	@Override
	public void assembleModel(ModelContainer m, Row r, String filename) {
		HostModel hm = fillHostModel(r);
		if (hm.isValid()) {
			m.addApplicationService(hm.getHostprogram());
		}
		else {
			log.error("Datei " + filename + " Sheet " + r.getSheet().getSheetName() + " Zeile " + r.getRowNum() + " konnte nicht verarbeitet werden!");
		}
	}
	
	private HostModel fillHostModel(Row r) {
		HostModel hm = new HostModel();
		HostProgram hp = new HostProgram();
		String name = getValue(r,0);
		String beschreibung = getValue(r, 10);
		if ("NULL".equals(beschreibung)) {
			beschreibung = null;
		}
		String applCode = getValue(r, 9);
		if ("NULL".equals(applCode)) {
			applCode = null;
		}

		String schnsichtbar = getValue(r, 5);
		String technologie = getValue(r, 6);
		String zugriffsart = getValue(r, 7);
		if ("NULL".equals(zugriffsart)) {
			zugriffsart = null;
		}
		String version = getValue(r, 12);
		String release = getValue(r, 13);
		String rawLifecycle = getValue(r, 19);
		
		@SuppressWarnings("unused")
		String library = getValue(r, 17); //hm.. 
		
		// lifecycleName is default from RBGartifact
		if ("USEANFO".equals(rawLifecycle)) {
			hp.setLifecycleState(LifecycleState.DEVELOPMENT);
		}
		else if ("USE".equals(rawLifecycle)) {
			hp.setLifecycleState(LifecycleState.PRODUCTION);
		}
		
		hp.setTier("Host-Tier");
		hp.setApplCode(applCode);
		hp.setName(name);
		hp.setDescription(beschreibung);
		hp.setInterfaceType(schnsichtbar);
		hp.setTechnology(technologie);
		hp.setAccesstype(zugriffsart);
		hp.setVersion(version);
		hp.setRelease(release);
		
		hm.setHostprogram(hp);
		return hm;
	}

}
