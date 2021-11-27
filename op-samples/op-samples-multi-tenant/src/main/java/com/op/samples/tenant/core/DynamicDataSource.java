package com.op.samples.tenant.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author chengdr01
 */
public class DynamicDataSource  extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceKey();
    }
}
