
package com.schoolmonitor.multitenacy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.schoolmonitor.model.TenantContext;

/**
 * @author PrabhjeetS
 * @version 1.0 November 21, 2019
 */

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private static final String DEFAULT_TENANT_ID = "defaultTenantOnAppStartUp";
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant= TenantContext.getCurrentTenant();
		return StringUtils.isEmpty(tenant) ?  DEFAULT_TENANT_ID:tenant;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
