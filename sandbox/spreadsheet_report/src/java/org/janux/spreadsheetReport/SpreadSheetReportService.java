package org.janux.spreadsheetReport;

import java.util.Map;

/**
 * This class provides services to create SpreadSheet reports.
 * 
 * @version 1.0
 * @created 01-Apr-2010 8:18:50 PM
 */
public interface SpreadSheetReportService {

	/**
	 * Return a SpreadSheet report from a jxls template. 
	 * 
	 * @param templateFile
	 * @param beans
	 * @param destFile
	 */
	public void getSpreadSheetFile(java.io.InputStream templateFile, Map beans, java.io.OutputStream destFile) throws Exception;

}