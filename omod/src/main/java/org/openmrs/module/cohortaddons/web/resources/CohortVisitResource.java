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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.rest.v1_0.resource.CohortRest;
import org.openmrs.module.cohortaddons.CohortVisit;
import org.openmrs.module.cohortaddons.api.CohortVisitService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + CohortRest.COHORT_NAMESPACE
        + "/cohortvisit", supportedClass = CohortVisit.class, supportedOpenmrsVersions = { "1.8 - 2.*" })
public class CohortVisitResource extends DataDelegatingCrudResource<CohortVisit> {
	
	private final CohortVisitService cohortVisitService;
	
	public CohortVisitResource() {
		this.cohortVisitService = Context.getRegisteredComponent("cohort.addons.cohortVisitService",
		    CohortVisitService.class);
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		
		DelegatingResourceDescription description = null;
		
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("visitType");
				description.addProperty("location");
				description.addProperty("startDate");
				description.addProperty("endDate");
				description.addProperty("cohortMemberVisits");
				description.addProperty("uuid");
				description.addSelfLink();
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("cohort");
				description.addProperty("visitType");
				description.addProperty("location");
				description.addProperty("startDate");
				description.addProperty("endDate");
				description.addProperty("cohortMemberVisits");
				description.addProperty("uuid");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("cohort");
		description.addRequiredProperty("visitType");
		description.addRequiredProperty("location");
		description.addRequiredProperty("startDate");
		description.addProperty("endDate");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		return getCreatableProperties();
	}
	
	@Override
	public CohortVisit save(CohortVisit cohortVisit) {
		return cohortVisitService.saveCohortVisit(cohortVisit);
	}
	
	@Override
	protected void delete(CohortVisit cohortVisit, String reason, RequestContext context) throws ResponseException {
		cohortVisit.setVoided(true);
		cohortVisit.setVoidReason(reason);
		cohortVisitService.saveCohortVisit(cohortVisit);
	}
	
	@Override
	public void purge(CohortVisit cohortVisit, RequestContext context) throws ResponseException {
		cohortVisitService.purgeCohortVisit(cohortVisit);
	}
	
	@Override
	public CohortVisit newDelegate() {
		return new CohortVisit();
	}
	
	@Override
	public CohortVisit getByUniqueId(String uuid) {
		return cohortVisitService.getCohortVisitByUuid(uuid);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String locationUuid = context.getParameter("location");
		List<CohortVisit> cohorts = new ArrayList<CohortVisit>();
		if (StringUtils.isNotBlank(locationUuid)) {
			cohorts = cohortVisitService.getCohortVisitsByLocationUuid(locationUuid);
		}
		
		return new NeedsPaging<CohortVisit>(cohorts, context);
	}
}
