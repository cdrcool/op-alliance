package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

/**
 * 查询条件接口
 *
 * @author cdrcool
 */
@ApiModel(description = "查询条件接口")
public abstract class AbstractCriterion implements Criterion {

    @Override
    public final Criterion and(Criterion criterion) {
        return new LogicCriterion(this, criterion, LogicOperator.AND);
    }

    @Override
    public final Criterion or(Criterion criterion) {
        return new LogicCriterion(this, criterion, LogicOperator.OR);
    }

    @Override
    public final SelectStatementProvider toSelectStatementProvider(SqlTable sqlTable) {
        QueryExpressionDSL<SelectModel> queryExpressionDSL = select(SqlColumn.of("*", sqlTable)).from(sqlTable);
        queryExpressionDSL
                .where(sqlTable.column("id"), isEqualTo(1))
                .and(sqlTable.column("id"), isEqualTo(1)
        );
        return queryExpressionDSL.build()
                .render(RenderingStrategies.MYBATIS3);
    }
}
