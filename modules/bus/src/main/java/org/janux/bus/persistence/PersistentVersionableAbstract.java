package org.janux.bus.persistence;

import java.util.Date;
import java.sql.Timestamp;

/**
 ***************************************************************************************************
 * Utility class that provides an Integer identity field, and date created/updated fields
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public abstract class PersistentVersionableAbstract extends PersistentAbstract 
	implements Versionable
{
	private Date dateCreated;
	private Date dateUpdated;

  public Date getDateCreated() {
    return this.dateCreated;
  }

  public void setDateCreated(Date datetime) {
    // prevents rounding issues in unit tests when using hibernate
    if (datetime instanceof Timestamp) {
      ((Timestamp)datetime).setNanos(0);
    }
    this.dateCreated = datetime;
  }


  public Date getDateUpdated() {
    return this.dateUpdated;
  }

  public void setDateUpdated(Date datetime) {
    // prevents rounding issues in unit tests
    if (datetime instanceof Timestamp) {
      ((Timestamp)datetime).setNanos(0);
    }
    this.dateUpdated = datetime;
  }
	
} // end class PersistentVersionableAbstract
