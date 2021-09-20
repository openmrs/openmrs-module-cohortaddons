package org.openmrs.module.cohortaddons.api.dao;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohortaddons.CohortVisit;
import org.openmrs.module.cohortaddons.SpringTestConfiguration;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = SpringTestConfiguration.class, inheritLocations = false)
public class IGenericDaoTest extends BaseModuleContextSensitiveTest {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	private GenericDao<CohortVisit> dao;

	@Before
	public void setup() {
		dao = new GenericDao<>();
		dao.setClazz(CohortVisit.class);
		dao.setSessionFactory(sessionFactory);
	}

	@Test
	public void shouldGetByUuid() {
		CohortVisit cohortVisit = dao.get("");
	}

}
