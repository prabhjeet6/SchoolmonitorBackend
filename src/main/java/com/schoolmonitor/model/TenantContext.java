package com.schoolmonitor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PrabhjeetS
 * @version 1.0
 */


public class TenantContext {
    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());
    private static ThreadLocal<String> currentTenant = new ThreadLocal<String>();
    public static void setCurrentTenant(String tenant) {
        logger.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
    public static void clear() {
        currentTenant.remove();
    }
}
