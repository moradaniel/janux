package biz.janux.payment;

import java.io.Serializable;

/**
 * This service takes into account how the objects have to be translated to return to client. 
 * For example: Translates hibernate collections by java collections for a serialization in the future
 * @author albertobuffagni@gmail.com
 *
 */
public interface RemotingService extends Serializable {

}
