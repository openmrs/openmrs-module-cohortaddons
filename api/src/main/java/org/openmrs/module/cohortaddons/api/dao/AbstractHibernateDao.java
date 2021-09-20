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
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Auditable;
import org.openmrs.OpenmrsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@SuppressWarnings("unchecked")
public class AbstractHibernateDao<W extends OpenmrsObject & Auditable> {
	
	private Class<W> wClazz;
	
	@Autowired
	@Qualifier("sessionFactory")
	@Getter(AccessLevel.PROTECTED)
	@Setter(AccessLevel.PROTECTED)
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void setClazz(Class<W> clazzToSet) {
		this.wClazz = clazzToSet;
	}
	
	public W get(String uuid) {
		return (W) getCurrentSession().createQuery("from " + wClazz.getName() + " where uuid = :uuid")
		        .setParameter("uuid", uuid).uniqueResult();
	}
	
	public Collection<W> findAll() {
		return getCurrentSession().createQuery("from " + wClazz.getName()).list();
	}
	
	public W createOrUpdate(W entity) {
		getCurrentSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(W entity) {
		getCurrentSession().delete(entity);
	}
	
	public void delete(String uuid) {
		this.delete(this.get(uuid));
	}
	
	public Collection<W> findBy(PropValue propValue) {
		return getCurrentSession()
		        .createQuery(
		            "from " + wClazz.getName() + " where " + propValue.getProperty() + " = :" + propValue.getProperty())
		        .setParameter(propValue.getProperty(), propValue.getValue()).list();
	}

	public Collection<W> findByOr(List<PropValue> propValues) {
		//FIXME
		return Collections.EMPTY_LIST;
	}

	public Collection<W> findByAnd(List<PropValue> propValues) {
		//FIXME
		return Collections.EMPTY_LIST;
	}
}
