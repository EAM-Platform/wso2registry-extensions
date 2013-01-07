package at.rbg.registry.processors.excel;

import java.io.InputStream;
import java.util.Iterator;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.model.ModelContainer;
import at.rbg.registry.utils.MultipartProcessor;

public class ExcelProcessor implements Processor {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(ExcelProcessor.class);

	private int firstRowToUse;

	public void setFirstRowToUse(int firstRowToUse) {
		this.firstRowToUse = firstRowToUse;
	}

	@Override
	public void process(Exchange ex) throws Exception {
		Message in = ex.getIn();
		InputStream is = null;
		String filename = null;

		Object headerFilename = in.getHeader(Exchange.FILE_NAME);
		// this comes from jetty: components
		if (headerFilename != null) {
			filename = (String) headerFilename;
			DataHandler data = in.getAttachment(filename);
			is = data.getDataSource().getInputStream();
		}
		// servlet
		else {
			String content_type = (String) in.getHeader(Exchange.CONTENT_TYPE);
			is = MultipartProcessor.parseInputStream((InputStream) in.getBody(), content_type);
		}
		Workbook wb = new HSSFWorkbook(is);
		Sheet sh = wb.getSheetAt(0); // first sheet

		// this will take the whole Excel in form of a tree of dependencies
		ModelContainer m = new ModelContainer();

		// default
		AbstractModelAssembler ma = new ExcelModelAssembler();

		String excelFileType = (String) ex.getIn().getHeader("EXCELFILETYPE");
		if ("hostexcel".equals(excelFileType)) {
			ma = new HostModelAssembler();
		}
		else if ("infrastructureexcel".equals(excelFileType)) {
			ma = new InfrastructureModelAssembler();
		}

		Iterator<Row> it = sh.rowIterator();
		while (it.hasNext()) {
			Row r = it.next();
			// invisible rows have zero height
			if (r.getRowNum() >= firstRowToUse && !r.getZeroHeight()) {
				ma.assembleModel(m, r, filename);
			}
		}
		in.setBody(m);
		ex.setIn(in);
	}

}
