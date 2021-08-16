package com.op.admin.job;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class QuartzJobDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final QrtzJob qrtzJob = new QrtzJob();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = qrtzJob.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobId = qrtzJob.jobId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobName = qrtzJob.jobName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobClass = qrtzJob.jobClass;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> cronExps = qrtzJob.cronExps;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> status = qrtzJob.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class QrtzJob extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> jobId = column("job_id", JDBCType.VARCHAR);

        public final SqlColumn<String> jobName = column("job_name", JDBCType.VARCHAR);

        public final SqlColumn<String> jobClass = column("job_class", JDBCType.VARCHAR);

        public final SqlColumn<String> cronExps = column("cron_exps", JDBCType.VARCHAR);

        public final SqlColumn<Integer> status = column("status", JDBCType.INTEGER);

        public QrtzJob() {
            super("QRTZ_JOB");
        }
    }
}
