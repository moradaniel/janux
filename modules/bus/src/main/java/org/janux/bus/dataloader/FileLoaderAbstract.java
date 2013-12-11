package org.janux.bus.dataloader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;

public abstract class FileLoaderAbstract implements Loader
{
	Log log = LogFactory.getLog(this.getClass());

	private String[] filenames;
	private String charsetName;

	protected BufferedReader[] readers;

	private int currentFile = -1;
	private int currentLine = -1;


	/** 
	 * Constructor that creates BufferedReaders for all the filenames passed, 
	 * using the specified character set 
	 */
	public FileLoaderAbstract(String[] filenames, String charsetName)
	{
		if (ArrayUtils.isEmpty(filenames))
			throw new IllegalArgumentException("You must provide the name of at least one file to load");
		
		this.filenames   = filenames;
		this.charsetName = charsetName;
	}

	/** 
	 * Constructor that creates BufferedReaders for all the filenames passed, 
	 * using the default system character set
	 */
	public FileLoaderAbstract(String[] filenames) {
		this(filenames, null);
	}



	/** returns a 0-based count of the file being processed */
	public int getCurrentFile() {
		return this.currentFile;
	}

	public void setCurrentFile(int i) {
		this.currentFile = i;
	}


	/** returns a 0-based count of the line being processed inside a file */
	public int getCurrentLine() {
		return this.currentLine;
	}

	public void setCurrentLine(int i) {
		this.currentLine = i;
	}



	/**
	 * Template method that reads through all the files successively, calling processLine(String line)
	 * on each line of each file being read, and keeping a counter of the line and file being read.
	 */
	public void load()
	{
		this.openReaders();
		this.preProcess();

		for (int i=0; i < readers.length ; i++)
		{
			setCurrentFile(i);
			String line;

			if (log.isDebugEnabled()) log.debug("processing file " + i + " '" + filenames[i] + "'");
			
			try
			{ 
				while ( (line = readers[i].readLine()) != null)
				{
					setCurrentLine(getCurrentLine()+1);
					this.processLine(line);
				}
			}
			catch (IOException e)
			{
				String msg = "error while processing line " + getCurrentLine() + " in file " + getCurrentFile() + " " + filenames[getCurrentFile()];
				log.error(msg, e);
				this.closeReaders();
				throw new RuntimeException(msg,e);
			}

			if (log.isInfoEnabled()) log.info("successfully processed file " + i + " '" + filenames[i] + "'");
		} // end for

		this.closeReaders();
		this.postProcess();
	}


	/** 
	 * this method is called on each line of each file being processed; the intention is that this
	 * method may in turn delegate to a processor that is appropriate for each type of file
	 */
	public abstract void processLine(String line);

	/** perform some processing before the load - default implementation does nothing */
	public void preProcess() { }

	/** perform some processing at the end of the load - default implementation does nothing */
	public void postProcess() { }


	/** creates BufferedReaders for all the filenames to be read */
	protected void openReaders()
	{
		if (log.isDebugEnabled()) log.debug("opening readers...");

		if (filenames == null)
			throw new IllegalArgumentException("No files have been specified!");

		this.readers = new BufferedReader[filenames.length];

		for (int i=0; i < filenames.length ; i++)
		{
			try
			{ 
				InputStreamReader inputStreamReader;
				FileInputStream fileInputStream = new FileInputStream(filenames[i]);

				if (charsetName != null)
					inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
				else
					inputStreamReader = new InputStreamReader(fileInputStream);

				readers[i] = new BufferedReader(inputStreamReader);

				if (log.isInfoEnabled()) log.info("opened file " + i + " '" + filenames[i] + "' for reading");
			}
			catch (IOException e)
			{
				String msg = "Unable to create reader for file:'" + filenames[i] + "'";
				log.error(msg, e);
				throw new RuntimeException(msg,e);
			}
		} // end for
	}


	/** 
	 * closes all BufferedReaders; should be called after finishing the load, or in the 'finally'
	 * clause of any try-catch blocks 
	 */
	protected void closeReaders()
	{
		if (log.isDebugEnabled()) log.debug("closing readers...");
		if (this.readers == null)
			throw new IllegalStateException("There are no Readers to close!");

		for (int i=0; i < readers.length ; i++) 
		{
			try { 
				if (readers[i] != null) {
					readers[i].close();
					if (log.isInfoEnabled()) log.info("closed reader for file " + i + " '" + filenames[i] + "'"); 
				}
			}
			catch (IOException e)
			{
				String msg = "Unable to close reader for file:'" + filenames[i] + "'";
				log.error(msg,e);
			}
		}
	}


	/**
	 * This is only a convenience sample implementation that should be
	 * overridden, if appropriate.
	 * 
	 * @param args the name of one or more files to load
	 */
	public static void main(String[] args)
	{
    if (StringUtils.isEmpty(args[0]))
    {
      System.out.println("Useage: java -cp $CLASSPATH " + FileLoaderAbstract.class.getName() + "filename1 [filename2] ...");
      System.out.println("  running this loader from an Ant target or Maven goal may be easier, given the potentially complex classpath");
    }

		/*
		Loader loader = new FileLoaderAbstract(args);
		loader.load;
		*/
	}
}
