package at.rbg.registry.processors.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

import at.rbg.registry.model.ModelContainer;

public abstract class AbstractModelAssembler {
	abstract public void assembleModel(ModelContainer m,Row r, String filename);
	
	protected String getValue(Row r, int cellNr) {
		Cell c = r.getCell(cellNr);
		if (c!=null) {
			RichTextString rs = c.getRichStringCellValue();
	
			String retval = rs.getString();
			if (retval != null && retval.length()>0) {
				retval= retval.trim();
			}
			return retval;
		}
		return "";
	}

}
