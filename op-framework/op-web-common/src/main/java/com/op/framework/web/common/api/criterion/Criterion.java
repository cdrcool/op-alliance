package com.op.framework.web.common.api.criterion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

/**
 * 查询条件接口
 *
 * @author cdrcool
 */
@ApiModel(description = "查询条件接口")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = SimpleCriterion.class, name = "simple"),
        @JsonSubTypes.Type(value = LogicCriterion.class, name = "logic"),
        @JsonSubTypes.Type(value = BatchCriterion.class, name = "batch"),
        @JsonSubTypes.Type(value = MapCriterion.class, name = "map")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public interface Criterion {

    /**
     * 与查询
     *
     * @param criterion 查询天搜剑
     * @return 新的查询条件
     */
    Criterion and(Criterion criterion);

    /**
     * 或查询
     *
     * @param criterion 查询天搜剑
     * @return 新的查询条件
     */
    Criterion or(Criterion criterion);

    /**
     * 转换为 SelectStatementProvider
     *
     * @param sqlTable {@link SqlTable}
     * @return {@link SelectStatementProvider}
     */
    SelectStatementProvider toSelectStatementProvider(SqlTable sqlTable);
}
