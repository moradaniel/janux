package org.janux.bus.persistence;

/**
 ***************************************************************************************************
 * RuntimeException used to denote that we could not load an Entity from persistence using its
 * primary key or natural key, although we expected the entity to exist; used to denote an
 * application error, rather than, for example, a mistyped business code on a UI.
 * <p>
 * In this respect, Janux uses the following semantic conventions, mostly inspired by the hibernate
 * project:
 * </p>
 * <ul>
 * 	<li>methods prefixed with <code>find</code> return null if the entity cannot be materialized</li>
 * 	<li>methods prefixed with <code>load</code> throw this EntityNotFoundException if the entity cannot be materialized</li>
 * </ul>
 * <p>
 * The first type of method would be used, for example, when attempting to retrieve an entity using
 * a business identifier that was supplied by a user on a UI.  It is possible that the identifier
 * was mistyped, and the failure to find the Entity is not necessarely an application error.
 * </p><p>
 * The second type of method may be used to deep load an Entity after an initial query returns a
 * collection of instances of the same class, for example when the user performs a boolean search
 * using keywords (find all people with a Paris address). Under such circumstances, and discounting
 * infrastructure exceptions, the failure to find an entity that is known to exist most likely points to
 * an application error. It is this sort of scenario that this exception is meant to represent.
 * </p>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.5 $ - $Date: 2006-10-28 04:13:08 $
 ***************************************************************************************************
 */
public class EntityNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -2720977021261617012L;
	public EntityNotFoundException()                { super(); }
	public EntityNotFoundException(String msg)      { super(msg); }
	public EntityNotFoundException(Throwable cause) { super(cause); }
	public EntityNotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
