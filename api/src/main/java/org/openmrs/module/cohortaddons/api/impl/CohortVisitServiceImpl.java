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

import java.util.Date;
import java.util.List;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cohort.api.dao.IGenericDao;
import org.openmrs.module.cohort.api.dao.PropValue;
import org.openmrs.module.cohortaddons.CohortVisit;
import org.openmrs.module.cohortaddons.api.CohortVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component(value = "cohort.addons.cohortVisitService")
public class CohortVisitServiceImpl extends BaseOpenmrsService implements CohortVisitService {
	
	private final IGenericDao<CohortVisit> dao;
	
	@Autowired
	public CohortVisitServiceImpl(IGenericDao<CohortVisit> iGenericDao) {
		this.dao = iGenericDao;
		this.dao.setClazz(CohortVisit.class);
	}
	
	@Override
	public CohortVisit getCohortVisitByUuid(String uuid) {
		return dao.get(uuid);
	}
	
	@Override
	public List<CohortVisit> getCohortVisitByType(Integer visitType) {
		PropValue propValues = PropValue.builder().property("visit_type_id").value(visitType).build();
		return (List<CohortVisit>) dao.findBy(propValues);
	}
	
	@Override
	public CohortVisit saveCohortVisit(CohortVisit cohortVisit) {
		return dao.createOrUpdate(cohortVisit);
	}
	
	@Override
	public List<CohortVisit> getCohortVisitsByLocationUuid(String locationUuid) {
		Location cohortLocation = Context.getService(LocationService.class).getLocationByUuid(locationUuid);
		if (cohortLocation == null) {
			throw new ResourceNotFoundException("Location with UUID " + locationUuid + " is unknown");
		} else {
			PropValue propValues = PropValue.builder().property("location_id").value(cohortLocation.getLocationId()).build();
			return (List<CohortVisit>) dao.findBy(propValues);
		}
	}
	
	@Override
	public List<CohortVisit> getCohortVisitsByDate(Date startDate, Date endDate) {
		//FIXME
		return null;
	}
	
	@Override
	public void purgeCohortVisit(CohortVisit cohortVisit) {
		dao.delete(cohortVisit);
	}
}
