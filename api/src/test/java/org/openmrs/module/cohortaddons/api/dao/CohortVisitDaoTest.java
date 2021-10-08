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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collection;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.api.dao.GenericDao;
import org.openmrs.module.cohort.api.dao.PropValue;
import org.openmrs.module.cohortaddons.CohortVisit;
import org.openmrs.module.cohortaddons.SpringTestConfiguration;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(classes = SpringTestConfiguration.class, inheritLocations = false)
public class CohortVisitDaoTest extends BaseModuleContextSensitiveTest {
	
	private static final String COHORT_VISIT_INITIAL_DATA = "org/openmrs/module/cohortaddons/api/dao/CohortVisit_initial_dataset.xml";
	
	private static final String COHORT_VISIT_UUID = "94517bf9-d8d7-4726-b4f1-a2dff6b36e2d";
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private GenericDao<CohortVisit> dao;
	
	@Before
	public void setup() throws Exception {
		dao = new GenericDao<>();
		dao.setClazz(CohortVisit.class);
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_VISIT_INITIAL_DATA);
	}
	
	@Test
	public void shouldReturnCohortVisitWhenGetByUuid() {
		CohortVisit cohortVisit = dao.get(COHORT_VISIT_UUID);
		assertThat(cohortVisit, notNullValue());
		assertThat(cohortVisit.getCohortVisitId(), equalTo(100));
		assertThat(cohortVisit.getUuid(), equalTo(COHORT_VISIT_UUID));
	}
	
	@Test
	public void shouldReturnNullWhenGetByNonExistentUuid() {
		CohortVisit cohortVisit = dao.get("non-existent-cohort-visit-uuid");
		assertThat(cohortVisit, nullValue());
	}
	
	@Test
	public void shouldReturnCollectionOfCohortVisitsWhenFindByLocationID() {
		PropValue propValue = PropValue.builder().property("id").associationPath(Optional.of("location")).value(1).build();
		Collection<CohortVisit> cohortVisitsByLocation = dao.findBy(propValue);
		
		assertThat(cohortVisitsByLocation, notNullValue());
		assertThat(cohortVisitsByLocation, hasSize(1));
		assertThat(cohortVisitsByLocation, everyItem(hasProperty("location", hasProperty("name", is("Test location")))));
	}
	
	@Test
	public void shouldReturnEmptyCollectionWhenFindByBadLocationID() {
		PropValue propValue = PropValue.builder().property("id").associationPath(Optional.of("location")).value(123).build();
		
		assertThat(dao.findBy(propValue), empty());
	}
}
