package org.janux.spreadsheetReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Unit test for simple App.
 *
 */
public class SpreadSheetReportTest 
	extends AbstractDependencyInjectionSpringContextTests
{
	
    private SpreadSheetReportService spreadSheetReportService;
    private SpreadSheetReportTestConfiguration spreadSheetReportTestConfiguration;
	
    public String[] getConfigLocations(){ return new String[] {
			"classpath:ApplicationContext.xml",
			"classpath:ApplicationContext-test.xml"
		};}

	public SpreadSheetReportTest() {
		System.out.println("ok");
		//setAutowireMode(AUTOWIRE_NO);
	}

	public SpreadSheetReportTest(String testName) {
		super(testName);
		//setAutowireMode(AUTOWIRE_NO);
	}

	/**
     * Creating a SpreadSheet Report
     */
    public void testCreteSpreadSheetReport()
    {
    	List<Employee> staff = new ArrayList<Employee>();
        staff.add(new Employee("Derek", 35, 3000, 30));
        staff.add(new Employee("Elsa", 28, 1500, 15));
        staff.add(new Employee("Oleg", 32, 2300, 25));
        staff.add(new Employee("Neil", 34, 2500, 0));
        staff.add(new Employee("Maria", 34, 1700, 15));
        staff.add(new Employee("John", 35, 2800, 20));
        staff.add(new Employee("Leonid", 29, 1700, 20));
        Map beans = new HashMap();
        beans.put("staff", staff);
        
		try {
			
			InputStream templateFile = new FileInputStream(new File(getSpreadSheetReportTestConfiguration().getBasedir()+getSpreadSheetReportTestConfiguration().getTestTemplateDirectory(),getSpreadSheetReportTestConfiguration().getNameTemplate()));
			
			File destDirectory = new File (getSpreadSheetReportTestConfiguration().getBasedir()+getSpreadSheetReportTestConfiguration().getTestDestDirectory(),"reports");
			boolean success= destDirectory.mkdir();
			OutputStream destFile= new FileOutputStream (new File(destDirectory,"report_output.xls")); 
			getSpreadSheetReportService2().getSpreadSheetFile(templateFile, beans, destFile);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		} 

		assertFalse(false);
    }

	/**
	 * @param spreadSheetReportService the spreadSheetReportService to set
	 */
	public void setSpreadSheetReportService(SpreadSheetReportService spreadSheetReportService) {
		this.spreadSheetReportService = spreadSheetReportService;
	}

	/**
	 * @return the spreadSheetReportService
	 */
	public SpreadSheetReportService getSpreadSheetReportService2() {
		return spreadSheetReportService;
	}

	/**
	 * @param spreadSheetReportTestConfiguration the spreadSheetReportTestConfiguration to set
	 */
	public void setSpreadSheetReportTestConfiguration(
			SpreadSheetReportTestConfiguration spreadSheetReportTestConfiguration) {
		this.spreadSheetReportTestConfiguration = spreadSheetReportTestConfiguration;
	}

	/**
	 * @return the spreadSheetReportTestConfiguration
	 */
	public SpreadSheetReportTestConfiguration getSpreadSheetReportTestConfiguration() {
		return spreadSheetReportTestConfiguration;
	}

}
