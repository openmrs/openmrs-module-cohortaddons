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

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.api.dao.GenericDao;
import org.openmrs.module.cohort.api.dao.PropValue;
import org.openmrs.module.cohortaddons.CohortMemberVisit;
import org.openmrs.module.cohortaddons.SpringTestConfiguration;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = SpringTestConfiguration.class, inheritLocations = false)
public class CohortMemberVisitDaoTest extends BaseModuleContextSensitiveTest {
	
	private static final String COHORT_MEMBER_VISIT_INITIAL_DATA = "org/openmrs/module/cohortaddons/api/dao/CohortMemberVisit_initial_dataset.xml";
	
	private static final String COHORT_MEMBER_VISIT_UUID = "78610tf9-d8d7-4726-b4f1-a2dkk6b360y6";
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private GenericDao<CohortMemberVisit> dao;
	
	@Before
	public void setup() throws Exception {
		executeDataSet(COHORT_MEMBER_VISIT_INITIAL_DATA);
		dao = new GenericDao<>();
		dao.setClazz(CohortMemberVisit.class);
		dao.setSessionFactory(sessionFactory);
	}
	
	@Test
	public void shouldReturnCohortMemberVisitWhenGetByUuid() {
		CohortMemberVisit cohortMemberVisit = dao.get(COHORT_MEMBER_VISIT_UUID);
		assertThat(cohortMemberVisit, notNullValue());
		assertThat(cohortMemberVisit.getCohortMemberVisitId(), equalTo(123));
		assertThat(cohortMemberVisit.getUuid(), equalTo(COHORT_MEMBER_VISIT_UUID));
	}
	
	@Test
	public void shouldReturnNullWhenGetByNonExistentUuid() {
		CohortMemberVisit cohortMemberVisit = dao.get("non-existent-cohort-member-visit-uuid");
		assertThat(cohortMemberVisit, nullValue());
	}
	
	@Test
	public void shouldReturnCollectionOfCohortMemberVisitsWhenFindByCohortVisitID() {
		PropValue propValue = PropValue.builder().property("id").associationPath(Optional.of("cohortVisit")).value(100)
		        .build();
		Collection<CohortMemberVisit> cohortMemberVisitsByCohortVisit = dao.findBy(propValue);
		
		assertThat(cohortMemberVisitsByCohortVisit, notNullValue());
		assertThat(cohortMemberVisitsByCohortVisit, hasSize(1));
		assertThat(cohortMemberVisitsByCohortVisit,
		    everyItem(hasProperty("cohortVisit", hasProperty("uuid", is("94517bf9-d8d7-4726-b4f1-a2dff6b36e2d")))));
	}
	
	@Test
	public void shouldReturnEmptyCollectionWhenFindByBadCohortVisitID() {
		PropValue propValue = PropValue.builder().property("id").associationPath(Optional.of("cohortVisit")).value(9093)
		        .build();
		
		assertThat(dao.findBy(propValue), empty());
	}
	
}
