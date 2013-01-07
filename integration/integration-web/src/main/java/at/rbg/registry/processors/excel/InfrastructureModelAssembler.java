package at.rbg.registry.processors.excel;

import java.io.UnsupportedEncodingException;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.model.Infrastructure;
import at.rbg.registry.model.ModelContainer;

public class InfrastructureModelAssembler extends AbstractModelAssembler {
	private static final Logger log = LoggerFactory.getLogger(InfrastructureModelAssembler.class);

	@Override
	public void assembleModel(ModelContainer m, Row r, String filename) {
		InfrastructureModel model;
		try {
			model = fillModel(r);
			if (model.isValid()) {
				m.addInfrastructure(model.getInfrastructure());
			}
			else {
				log.error("Datei " + filename + " Sheet " + r.getSheet().getSheetName() + " Zeile " + r.getRowNum() + " konnte nicht verarbeitet werden!");
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
	}

	private InfrastructureModel fillModel(Row r) throws UnsupportedEncodingException {
		InfrastructureModel model = new InfrastructureModel();
		Infrastructure inf = new Infrastructure();

		String componentnumber = getValue(r,0);
		String name = getValue(r,1);
		String category = getValue(r, 2);
		String subcategory = getValue(r, 3);
		String version = getValue(r, 4);
		
		String rawLifecycle = getValue(r, 5);
			
		// lifecycleName is default from RBGartifact
//		if ("ungültig".equals(rawLifecycle)) {
//			inf.setLifecycleState(LifecycleState.DEPRECATED);
//		}
//		else if ("in Bearbeitung".equals(rawLifecycle)) {
//			inf.setLifecycleState(LifecycleState.DEVELOPMENT);
//		}
//		else if ("gültig".equals(rawLifecycle)) {
//			inf.setLifecycleState(LifecycleState.PRODUCTION);
//		}
		
		inf.setName(name);
		inf.setCategory(category);
		inf.setComponentnumber(componentnumber);
		inf.setSubcategory(subcategory);
		inf.setVersion(version);		
		inf.setStatus(rawLifecycle);
		
		model.setInfrastructure(inf);
		return model;		
	}
}
