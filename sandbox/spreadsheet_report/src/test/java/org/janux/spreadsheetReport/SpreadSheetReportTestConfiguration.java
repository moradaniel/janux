package org.janux.spreadsheetReport;

public class SpreadSheetReportTestConfiguration {

	/** 
     * Basedir for all file I/O. Important when running tests from
     * the reactor.
     */
    public String basedir =  System.getProperty("basedir");
    public String testDestDirectory;
    public String testTemplateDirectory;
    public String nameTemplate;
    
	public String getBasedir() {
		return basedir;
	}
	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}
	public String getTestDestDirectory() {
		return testDestDirectory;
	}
	public void setTestDestDirectory(String testDestDirectory) {
		this.testDestDirectory = testDestDirectory;
	}
	public String getTestTemplateDirectory() {
		return testTemplateDirectory;
	}
	public void setTestTemplateDirectory(String testTemplateDirectory) {
		this.testTemplateDirectory = testTemplateDirectory;
	}
	public String getNameTemplate() {
		return nameTemplate;
	}
	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}
	public SpreadSheetReportTestConfiguration() {
		super();
	}

}
