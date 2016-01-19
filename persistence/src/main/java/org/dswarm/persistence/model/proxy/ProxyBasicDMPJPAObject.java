/**
 * Copyright (C) 2013 – 2016 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dswarm.persistence.model.proxy;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dswarm.persistence.model.BasicDMPJPAObject;

/**
 * An abstract proxy POJO class for where the real objects can have a name and where the identifier of the real object should be
 * generated by the database.
 * 
 * @author tgaengler
 */
@XmlRootElement
public abstract class ProxyBasicDMPJPAObject<POJOCLASS extends BasicDMPJPAObject> extends ProxyDMPObject<POJOCLASS> {

	private static final Logger	LOG					= LoggerFactory.getLogger(ProxyBasicDMPJPAObject.class);

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Default constructor for handing over a freshly created object, i.e., no updated or already existing object.
	 * 
	 * @param basicDMPJPAObjectArg a freshly created object
	 */
	public ProxyBasicDMPJPAObject(final POJOCLASS basicDMPJPAObjectArg) {

		super(basicDMPJPAObjectArg);
	}

	/**
	 * Creates a new proxy with the given real object and the type how the object was processed by the persistence service, e.g.,
	 * {@link RetrievalType.CREATED}.
	 * 
	 * @param basicDMPJPAObjectArg an object that was processed by a persistence service
	 * @param typeArg the type how this object was processed by the persistence service
	 */
	public ProxyBasicDMPJPAObject(final POJOCLASS basicDMPJPAObjectArg, final RetrievalType typeArg) {

		super(basicDMPJPAObjectArg, typeArg);
	}

	/**
	 * Gets the name of the real entity.
	 * 
	 * @return the name of the real entity
	 */
	public String getName() {

		if (dmpObject == null) {

			return null;
		}

		return dmpObject.getName();
	}

	/**
	 * Sets the name of the real entity.
	 * 
	 * @param name the name of the real (/proxied) entity
	 */
	public void setName(final String name) {

		if (dmpObject == null) {

			ProxyBasicDMPJPAObject.LOG.debug("couldn't set the name to the real/proxied object, because the real/proxied object is null");

			return;
		}

		dmpObject.setName(name);
	}
}
