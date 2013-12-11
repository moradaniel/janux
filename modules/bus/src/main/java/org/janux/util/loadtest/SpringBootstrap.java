package org.janux.util.loadtest;

import java.util.Map;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.InitializingBean;


/**
 ***************************************************************************************************
 * Bootstrap class that reads one or more Spring application context files and runs a LoadRunner
 * instance defined in the application context files. 
 * <p>
 * The main method of this bootstrap class will look for a bean named loadRunner of type
 * org.janux.util.loadtest.LoadRunner to run.  If no such bean, it will look for any bean of type LoadRunner.  If either of
 * these beans is found, the method will check wether the bean implements the Spring
 * org.springframework.beans.factory.InitializingBean.  If so, it will assume that the LoadRunner is
 * started in that method.  If not so, than it will invoke loadRunner.startThreads() to start the
 * test. If the method cannot find any bean of type LoadRunner, it will throw a RunTimeException.
 * </p><p>
 * The name of the application context files are passed as command line arguments and are
 * interpreted to be on the file system, rather than the classpath; 
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2007-06-16 01:13:26 $
 ***************************************************************************************************
 */
public class SpringBootstrap
{
	/** the expected name of the Load Runner bean: the string 'loadRunner' */
	public static final String LOAD_RUNNER_BEAN_ID = "loadRunner";

	public static void main (String[] args)
	{
		try {
			if (args.length == 0) printUsage();

			ApplicationContext context = new FileSystemXmlApplicationContext(args);

			LoadRunner loadRunner = null;

			try { 
				loadRunner = (LoadRunner)context.getBean("LoadRunner");
			}
			catch (NoSuchBeanDefinitionException e) {
				// do nothing
			}

			if ((loadRunner instanceof LoadRunner) == false )
				loadRunner = (LoadRunner)SpringBootstrap.getFirstBeanFound(context, LoadRunner.class);

			if ((loadRunner instanceof LoadRunner) == false )
				throw new RuntimeException(
						"Unable to find a LoadRunner class in the Spring Application Context.  Did you properly " +
						"define a bean of type org.janux.util.loadtest.LoadRunner therein ?");

			// we assume that if the loadRunner implements InitializingBean it will be started 
			// in its afterPropertiesSet() method; otherwise, start it here explicitly
			if ((loadRunner instanceof InitializingBean) == false ) 
				loadRunner.startThreads();

				System.exit(0);

		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			System.exit(1);
		}
		
	}


	/** 
	 * convenience method to return the first bean in the Application Context with the type provided;
	 * this should be called when there is only one bean of that class defined in the Application Context
	 */
	private static Object getFirstBeanFound(ApplicationContext aContext, Class aClass)
	{
		Map map = aContext.getBeansOfType(aClass);
		Iterator i = map.entrySet().iterator();

		return (i.hasNext()) ? ((Map.Entry)i.next()).getValue() : null;
	}

	private static void printUsage() 
	{
		String[] msg = {
			"Usage:",
			"java -jar loadtester.jar aLoadTestContext.xml [otherLoadTestContext.xml ...]"
		};

		for (int i=0 ; i < msg.length ; i++)
		{
			System.out.println(msg[i]);
		} // end for
	}


} // end class LoadRunner
