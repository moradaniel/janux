package biz.janux.geography;

import java.io.Serializable;

import org.janux.bus.persistence.Persistent;

/**
 ***************************************************************************************************
 * Interface representing a Country; contains convenience classes for storing ISO Code and
 * International Phone code; all State/Provinces and Cities in the system contain references to
 * their containing Country
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.5 $ - $Date: 2007-12-18 20:29:48 $
 * @since 2005-10-01
 ***************************************************************************************************
 */
public interface Country extends Serializable, Persistent
{
	/** the unique two-letter ISO code identifying this Country */
	String getCode();
	void setCode(String code);

	/** the International Code used to place a telephone call in this Country */
	int getPhoneCode();
	void setPhoneCode(int phoneCode);

	/** the name of the Country; TODO: this field should be internationalized */
	String getName();
	void setName(String name);

	/** 
	 * implementation specific sorting order, for example to display the
	 * Countries in a list or drop-down in an arbitrary order
	 */
	Integer getSortOrder();
	void setSortOrder(Integer i);
}
