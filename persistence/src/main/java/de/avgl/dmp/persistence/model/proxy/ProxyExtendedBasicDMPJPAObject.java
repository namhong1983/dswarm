package de.avgl.dmp.persistence.model.proxy;

import javax.xml.bind.annotation.XmlRootElement;

import de.avgl.dmp.persistence.model.ExtendedBasicDMPJPAObject;

/**
 * An abstract proxy POJO class for where the real objects can have a name, a descrption and where the identifier of the real
 * object should be generated by the database.
 * 
 * @author tgaengler
 */
@XmlRootElement
public abstract class ProxyExtendedBasicDMPJPAObject<POJOCLASS extends ExtendedBasicDMPJPAObject> extends ProxyBasicDMPJPAObject<POJOCLASS> {

	private static final org.apache.log4j.Logger	LOG					= org.apache.log4j.Logger.getLogger(ProxyExtendedBasicDMPJPAObject.class);

	/**
	 *
	 */
	private static final long						serialVersionUID	= 1L;

	/**
	 * Default constructor for handing over a freshly created object, i.e., no updated or already existing object.
	 * 
	 * @param extendedBasicDMPJPAObjectArg a freshly created object
	 */
	public ProxyExtendedBasicDMPJPAObject(final POJOCLASS extendedBasicDMPJPAObjectArg) {

		super(extendedBasicDMPJPAObjectArg);
	}

	/**
	 * Creates a new proxy with the given real object and the type how the object was processed by the persistence service, e.g.,
	 * {@link RetrievalType.CREATED}.
	 * 
	 * @param extendedBasicDMPJPAObjectArg an object that was processed by a persistence service
	 * @param typeArg the type how this object was processed by the persistence service
	 */
	public ProxyExtendedBasicDMPJPAObject(final POJOCLASS extendedBasicDMPJPAObjectArg, final RetrievalType typeArg) {

		super(extendedBasicDMPJPAObjectArg, typeArg);
	}

	/**
	 * Gets the description of the real entity.
	 * 
	 * @return the description of the real entity
	 */
	public String getDescription() {

		if (dmpObject == null) {

			return null;
		}

		return dmpObject.getDescription();
	}

	/**
	 * Sets the description of the real entity.
	 * 
	 * @param description the description of the real (/proxied) entity
	 */
	public void setDescription(final String description) {

		if (dmpObject == null) {

			ProxyExtendedBasicDMPJPAObject.LOG
					.debug("couldn't set the description to the real/proxied object, because the real/proxied object is null");

			return;
		}

		dmpObject.setDescription(description);
	}
}
