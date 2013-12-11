package org.janux.bus.dataloader.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import org.janux.bus.dataloader.Loader;
import org.janux.bus.dataloader.FileLoaderAbstract;


/**
 ***************************************************************************************************
 * Concrete implementation used for unit testing FileLoaderAbstract
 *
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2005-11-17 19:48:42 $
 ***************************************************************************************************
 */
public class FileLoaderImpl extends FileLoaderAbstract
{
	Log log = LogFactory.getLog(this.getClass());


	/** simply calls parent constructor */
	public FileLoaderImpl(String[] filenames) {
		super(filenames);
	}

	/** processes a line read from one of the files being read */
	public void processLine(String line)
	{
		System.out.println("File #" + this.getCurrentFile() + " - Line #" + this.getCurrentLine());
		System.out.println(line);

	}

	/**
	 * Used to run this class from the command line or from an ant/maven target
	 *
	 * @param args the name of one or more files to load
	 */
	public static void main(String[] args)
	{
    if (StringUtils.isEmpty(args[0]))
    {
      System.out.println("Useage: java -cp $CLASSPATH " + FileLoaderImpl.class.getName() + "filename1 [filename2] ...");
      System.out.println("  running this loader from an Ant target or Maven goal may be easier, given the potentially complex classpath");
    }

		Loader loader = new FileLoaderImpl(args);
		loader.load();
	}

} // end class


