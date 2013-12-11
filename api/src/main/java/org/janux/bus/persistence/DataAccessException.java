package org.janux.bus.persistence;

public class DataAccessException extends RuntimeException 
{
	
	private static final long serialVersionUID = 121575386588757033L;
	public DataAccessException()                { super(); }
	public DataAccessException(String msg)      { super(msg); }
	public DataAccessException(Throwable cause) { super(cause); }
	public DataAccessException(String msg, Throwable cause) { super(msg, cause); }
}
