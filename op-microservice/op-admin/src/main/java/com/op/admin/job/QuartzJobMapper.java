package com.op.admin.job;

import static com.op.admin.job.QuartzJobDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

/**
 * @author Mybatis Generator
 * @date 2021/08/16 03:51
 */
@Mapper
public interface QuartzJobMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, jobId, jobName, jobClass, cronExps, status);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<QuartzJob> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("QrtzJobResult")
    Optional<QuartzJob> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="QrtzJobResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="job_id", property="jobId", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_name", property="jobName", jdbcType=JdbcType.VARCHAR),
        @Result(column="job_class", property="jobClass", jdbcType=JdbcType.VARCHAR),
        @Result(column="cron_exps", property="cronExps", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<QuartzJob> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(QuartzJob record) {
        return MyBatis3Utils.insert(this::insert, record, qrtzJob, c ->
            c.map(jobId).toProperty("jobId")
            .map(jobName).toProperty("jobName")
            .map(jobClass).toProperty("jobClass")
            .map(cronExps).toProperty("cronExps")
            .map(status).toProperty("status")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(QuartzJob record) {
        return MyBatis3Utils.insert(this::insert, record, qrtzJob, c ->
            c.map(jobId).toPropertyWhenPresent("jobId", record::getJobId)
            .map(jobName).toPropertyWhenPresent("jobName", record::getJobName)
            .map(jobClass).toPropertyWhenPresent("jobClass", record::getJobClass)
            .map(cronExps).toPropertyWhenPresent("cronExps", record::getCronExps)
            .map(status).toPropertyWhenPresent("status", record::getStatus)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<QuartzJob> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<QuartzJob> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<QuartzJob> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<QuartzJob> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, qrtzJob, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(QuartzJob record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobId).equalTo(record::getJobId)
                .set(jobName).equalTo(record::getJobName)
                .set(jobClass).equalTo(record::getJobClass)
                .set(cronExps).equalTo(record::getCronExps)
                .set(status).equalTo(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(QuartzJob record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobId).equalToWhenPresent(record::getJobId)
                .set(jobName).equalToWhenPresent(record::getJobName)
                .set(jobClass).equalToWhenPresent(record::getJobClass)
                .set(cronExps).equalToWhenPresent(record::getCronExps)
                .set(status).equalToWhenPresent(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(QuartzJob record) {
        return update(c ->
            c.set(jobId).equalTo(record::getJobId)
            .set(jobName).equalTo(record::getJobName)
            .set(jobClass).equalTo(record::getJobClass)
            .set(cronExps).equalTo(record::getCronExps)
            .set(status).equalTo(record::getStatus)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(QuartzJob record) {
        return update(c ->
            c.set(jobId).equalToWhenPresent(record::getJobId)
            .set(jobName).equalToWhenPresent(record::getJobName)
            .set(jobClass).equalToWhenPresent(record::getJobClass)
            .set(cronExps).equalToWhenPresent(record::getCronExps)
            .set(status).equalToWhenPresent(record::getStatus)
            .where(id, isEqualTo(record::getId))
        );
    }
}
