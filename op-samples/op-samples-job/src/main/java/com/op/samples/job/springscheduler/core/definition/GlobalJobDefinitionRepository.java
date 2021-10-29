package com.op.samples.job.springscheduler.core.definition;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基于内存的任务定义 repository
 *
 * @author cdrcool
 */
@Component
public class GlobalJobDefinitionRepository implements JobDefinitionRepository {
    private static final ConcurrentMap<String, JobDefinition> JOB_HANDLER_STORE = new ConcurrentHashMap<String, JobDefinition>();

    @Override
    public void add(String jobName, JobDefinition jobDefinition) {
        JOB_HANDLER_STORE.put(jobName, jobDefinition);
    }

    @Override
    public void remove(String jobName) {
        JOB_HANDLER_STORE.remove(jobName);
    }

    @Override
    public JobDefinition get(String jobName) {
        return JOB_HANDLER_STORE.get(jobName);
    }

    @Override
    public boolean exist(String jobName) {
        return JOB_HANDLER_STORE.containsKey(jobName);
    }

    @Override
    public List<JobDefinition> listAll() {
        return new ArrayList<>(JOB_HANDLER_STORE.values());
    }
}
