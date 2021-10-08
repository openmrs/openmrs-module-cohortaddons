/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohortaddons.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cohort.api.dao.IGenericDao;
import org.openmrs.module.cohortaddons.CohortMemberVisit;
import org.openmrs.module.cohortaddons.api.CohortMemberVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "cohort.addons.cohortMemberVisitService")
public class CohortMemberVisitServiceImpl extends BaseOpenmrsService implements CohortMemberVisitService {
	
	private final IGenericDao<CohortMemberVisit> dao;
	
	@Autowired
	public CohortMemberVisitServiceImpl(IGenericDao<CohortMemberVisit> iGenericDao) {
		this.dao = iGenericDao;
		this.dao.setClazz(CohortMemberVisit.class);
	}
	
	@Override
	public CohortMemberVisit getCohortMemberVisitByUuid(String uuid) {
		return dao.get(uuid);
	}
	
	@Override
	public CohortMemberVisit saveCohortMemberVisit(CohortMemberVisit cohortMemberVisit) {
		return dao.createOrUpdate(cohortMemberVisit);
	}
}
