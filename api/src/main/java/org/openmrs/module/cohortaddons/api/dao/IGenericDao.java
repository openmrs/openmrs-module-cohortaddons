/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohortaddons.api.dao;

import java.util.Collection;
import java.util.List;

import org.openmrs.Auditable;
import org.openmrs.OpenmrsObject;
import org.springframework.transaction.annotation.Transactional;

public interface IGenericDao<W extends OpenmrsObject & Auditable> {
	
	void setClazz(Class<W> clazz);
	
	@Transactional(readOnly = true)
	W get(final String uuid);
	
	W createOrUpdate(W object);

	void delete(W object);

	void delete(String uuid);

	Collection<W> findBy(PropValue propValue);

	Collection<W> findByOr(List<PropValue> propValues);

	Collection<W> findByAnd(List<PropValue> propValues);
}
