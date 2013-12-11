package biz.janux.people;

/**
 * Simple bean to store the name of a Person
 */
public interface PersonName extends PartyName
{		
	String getFirst();
	void setFirst(String s);

	String getMiddle();
	void setMiddle(String s);

	String getLast();
	void setLast(String s);

	/** titles that may be used before a name such as: Mr., Ms., Dr., etc...*/
	String getHonorificPrefix();
	void setHonorificPrefix(String s);

	/** titles that are used after a name such as Jr., Sr., M.D., C.P.A., etc... */
	String getHonorificSuffix();
	void setHonorificSuffix(String s);
}
