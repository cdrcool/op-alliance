package com.op.framework.web.common.api.criterion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CriterionTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Criterion criterion = new SimpleCriterion("name", "abc", Operator.EQ)
                .and(new SimpleCriterion("value", 18, Operator.GT))
                .or(new SimpleCriterion("code", "xxx", Operator.LIKE));
        String str = objectMapper.writeValueAsString(criterion);
        System.out.println(str);

        Criterion criterion1 = objectMapper.readValue(str, Criterion.class);
    }
}
