package at.rbg.registry.processors.excel;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.HostProgram;
import at.rbg.registry.model.ModelContainer;
import at.rbg.registry.model.Table;
import at.rbg.registry.model.relation.CallRelation;
import at.rbg.registry.model.relation.UseRelation;

public class ExcelModelAssembler extends AbstractModelAssembler {
	private static final Logger log = LoggerFactory.getLogger(ExcelModelAssembler.class);

	@Override
	public void assembleModel(ModelContainer m, Row r, String filename) {
		ExcelModel hm = fillModel(r);
		if (hm.isValid()) {
            m.addApplicationService(hm.getSrcArtifact());
            if(hm.getDestArtifact() != null)
            {
                m.addApplicationService(hm.getDestArtifact());
                CallRelation cr = new CallRelation(hm.getSrcArtifact(), hm.getDestArtifact());
                m.addCallRelation(cr);
            }
            if(hm.getTable()!= null)
            {
                m.addTable(hm.getTable());
                UseRelation ur = new UseRelation(hm.getSrcArtifact(), hm.getTable());
                m.addUseRelation(ur);
            }
		}
		else {
				log.error("Datei " + filename + " Sheet " + r.getSheet().getSheetName() + " Zeile " + r.getRowNum() + " konnte nicht verarbeitet werden!");
		}
	}

	private ExcelModel fillModel(Row r) {
        ExcelModel em = new ExcelModel();
        ApplicationService dest = null;
        Table tab = null;
        String srcApplCode = getValue(r, 0);
        if(srcApplCode == null || srcApplCode.isEmpty()) {
            srcApplCode = "Keine";
        }
        String destApplCode = getValue(r, 2);
        if(destApplCode == null || destApplCode.isEmpty()) {
            destApplCode = "Keine";
        }
        String artifactType = getValue(r, 8);
        if("Datenbank".equals(artifactType))
        {
            tab = new Table();
            tab.setName(getValue(r, 3));
            tab.setVersion("UNKNOWN");
            tab.setDescription(getValue(r, 4));
            tab.setApplCode(destApplCode);
            em.setTable(tab);
        } 
        else if("Host-Modul".equals(artifactType)) {
                dest = new HostProgram();
                ((HostProgram)dest).setTechnology(getValue(r, 6));
                ((HostProgram)dest).setInterfaceType(getValue(r, 5));
                dest.setName(getValue(r, 3));
                dest.setVersion("UNKNOWN");
                dest.setDescription(getValue(r, 4));
                dest.setApplCode(destApplCode);
                em.setDestArtifact(dest);
        } 
        else {
        	return em; // empty model is not valid
        }
        ApplicationService src = new HostProgram();
        src.setName(getValue(r, 1));
        src.setVersion("UNKNOWN");
        src.setApplCode(srcApplCode);
        em.setSrcArtifact(src);
        return em;
	}

}
