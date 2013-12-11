package org.janux.spreadsheetReport;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * {@inheritDoc}
 */
public class SpreadSheetReportServiceImpl implements SpreadSheetReportService{

	private XLSTransformer transformer;

	public SpreadSheetReportServiceImpl(){

	}

	/**
	 * {@inheritDoc}
	 */
	public void getSpreadSheetFile(java.io.InputStream templateFile, Map beans, java.io.OutputStream destFile) throws Exception{
		getTransformer().transformXLS(templateFile, beans).write(destFile);
	}

	/**
	 * @param transformer the transformer to set
	 */
	public void setTransformer(XLSTransformer transformer) {
		this.transformer = transformer;
	}

	/**
	 * @return the transformer
	 */
	public XLSTransformer getTransformer() {
		return transformer;
	}

}