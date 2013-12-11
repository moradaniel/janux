package biz.janux.geography;

import java.io.Serializable;

import org.janux.bus.persistence.Persistent;

/**
 ***************************************************************************************************
 * Interface representing a State or Province; States or Provinces are defined relative to the
 * Country that contains them, and hence are required to contain a reference to this Country
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.4 $ - $Date: 2007-12-18 20:29:48 $
 * @since 2005-10-01
 ***************************************************************************************************
 */
public interface StateProvince extends Serializable, Persistent
{
	/** a Code that uniquely identifies a State or Province within a Country */
	String getCode();
	void setCode(String code);

	/** the name of the State or Province; TODO: this field should be internationalized */
	String getName();
	void setName(String name);

	/** the Country containing this State/Province, must be not null */
	Country getCountry();
	void setCountry(Country c);

	/** 
	 * implementation specific sorting order, for example to display the
	 * State/Provinces in a list or drop-down in an arbitrary order
	 */
	Integer getSortOrder();
	void setSortOrder(Integer i);
}
