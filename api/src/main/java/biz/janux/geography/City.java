package biz.janux.geography;

import java.io.Serializable;

import org.janux.bus.persistence.Persistent;

public interface City extends Serializable, Persistent
{
	String getCode();
	void setCode(String code);

	String getName();
	void setName(String name);

	StateProvince getState();
	void setState(StateProvince o);

	/** alias for getState **/
	StateProvince getProvince();
	void setProvince(StateProvince o);

	/** shortcut for getState().getCountry() */
	Country getCountry();
	void setCountry(Country o);
}
