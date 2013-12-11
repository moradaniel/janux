package org.janux.bus.processor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.StringReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 ***************************************************************************************************
 * <p>
 * Abstract base implementation of SpringProcessor that implements a template method for the
 * inherited method {@link Processor#process(InputStream, OutputStream)} that converts the
 * InputStream into a String, processes the String, and writes the processed String into the
 * OutputStream; sub-classes should provide an implementation of the 
 * {@link StringProcessor#process(String)} method.  
 * </p><p>
 * Optionally, implementing classes can specify different {@link Charset Charsets} for respectively
 * decoding/encoding the Input/Output streams.  If either is null, the default character set for the
 * system would be used for either decoding or encoding, respectively.  
 * </p>
 *
 * @see StringProcessor
 * @see Processor
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since version 0.2.03 - Jun 20, 2007
 ***************************************************************************************************
 */
public abstract class StringProcessorAbstract implements StringProcessor
{
	protected transient Log log = LogFactory.getLog(this.getClass());

	private Charset incomingCharset;	
	private Charset outgoingCharset;
	
	/**
	 * @return Returns the charset in which to decode the input.
	 */
	public Charset getIncomingCharset() {
		return this.incomingCharset;
	}
	
	/**
	 * @return Returns the charset in which to encode the output.
	 */
	public Charset getOutgoingCharset() {
		return this.outgoingCharset;
	}


	/** Derived classes should implement this method only */
	public abstract String process(String in);


  /**
   * Template method that transforms the InputStream into a String, calls
	 * this.process(String) and writes the resulting response to the OutputStream
	 *
	 * TODO: This default template method has not been tested, use at your own risk
   */
	public void process(InputStream in, OutputStream out)
	{
		String output = this.process(this.readStringInput(in));
		this.writeOutput(output, out);
	}


  /**
	 * Reads the input String from the InputStream, and traps all the IOExceptions along the way and
	 * converts them to RuntimeExceptions and/or logs them, depending on the severity.
	 *
	 * TODO: This default template method has not been tested, use at your own risk
   */
	private String readStringInput(InputStream in) 
	{
		BufferedReader reader = null;

		if (this.getIncomingCharset() instanceof Charset) {
			reader = new BufferedReader(new InputStreamReader(in, this.getIncomingCharset())); 
		}
		else {
			reader = new BufferedReader(new InputStreamReader(in));
		}

		StringBuffer stringBuffer = new StringBuffer();
		String line = null;

		try { 
			while ( (line = reader.readLine()) != null) stringBuffer.append(line);
		}
		catch (IOException e)
		{
			String msg = "Unable to read incoming stream";
			if (line != null) {
				if (line.length() < 200) {
					msg +=", failed at line: '" + line + "...'";
				}
				else {
					msg +=", failed at line: '" + line.substring(0,200) + "...'";
				}
			}
			log.error(msg, e);
			throw new RuntimeException(msg,e);
		}
		finally {
			try { 
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				String msg = "Unable to close the reader of the InputStream";
				log.error(msg, e);
			}
		}

		return stringBuffer.toString();
	}


  /**
	 * Takes the response String, writes it to the OutputStream, traps all the IOExceptions along
	 * the way and converts them to RuntimeExceptions and/or logs them, depending on the severity.
	 *
	 * TODO: This default template method has not been tested, use at your own risk
   */
	private void writeOutput(String output, OutputStream outputStream) 
	{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
		StringReader   in  = new StringReader(output);

		int c;

		try { 
			while ((c = in.read()) != -1) { 
				try { 
					out.write(c);
				}
				catch (IOException e)
				{
					String msg = "Unable to write character: " + c;
					log.error(msg, e);
					throw new RuntimeException(msg,e);
				}
			}
		}
		catch (IOException e)
		{
			String msg = "Unable to read the processed output";
			log.error(msg, e);
			throw new RuntimeException(msg,e);
		}
		finally {
			try { 
				if (out != null) out.close();
			}
			catch (IOException e)
			{
				String msg = "Unable to properly close the ouput writer after writing the output to the OutputStream";
				log.warn(msg, e);
			}

			if (in != null) in.close();
		}
	}
}
