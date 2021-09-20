/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohortaddons.web.resources;

import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.rest.v1_0.resource.CohortRest;
import org.openmrs.module.cohortaddons.CohortMemberVisit;
import org.openmrs.module.cohortaddons.api.CohortMemberVisitService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + CohortRest.COHORT_NAMESPACE
        + "/cohortmembervisit", supportedClass = CohortMemberVisit.class, supportedOpenmrsVersions = { "1.8 - 2.*" })
public class CohortMemberVisitResource extends DataDelegatingCrudResource<CohortMemberVisit> {
	
	private final CohortMemberVisitService cohortMemberVisitService;
	
	public CohortMemberVisitResource() {
		this.cohortMemberVisitService = Context.getRegisteredComponent("cohort.addons.cohortMemberVisitService",
		    CohortMemberVisitService.class);
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		
		DelegatingResourceDescription description = null;
		
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("visit", Representation.FULL);
				description.addProperty("uuid");
				description.addSelfLink();
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("visit", Representation.FULL);
				description.addProperty("cohortVisit", Representation.REF);
				description.addProperty("uuid");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}
	
	@Override
	public CohortMemberVisit getByUniqueId(String s) {
		return cohortMemberVisitService.getCohortMemberVisitByUuid(s);
	}
	
	@Override
	protected void delete(CohortMemberVisit cohortMemberVisit, String s, RequestContext requestContext)
	        throws ResponseException {
		
	}
	
	@Override
	public CohortMemberVisit newDelegate() {
		return new CohortMemberVisit();
	}
	
	@Override
	public CohortMemberVisit save(CohortMemberVisit cohortMemberVisit) {
		return cohortMemberVisitService.saveCohortMemberVisit(cohortMemberVisit);
	}
	
	@Override
	public void purge(CohortMemberVisit cohortMemberVisit, RequestContext requestContext) throws ResponseException {
		
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("visit");
		description.addProperty("cohortVisit");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		return getCreatableProperties();
	}
}
