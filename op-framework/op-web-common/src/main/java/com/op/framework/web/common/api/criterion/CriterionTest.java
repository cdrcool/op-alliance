package com.op.framework.web.common.api.criterion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CriterionTest {

    public static void main(String[] args) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("index", "aaa");
        ObjectMapper objectMapper = new ObjectMapper();
        Criterion criterion = new SimpleCriterion("name", "abc", Operator.EQ)
                .and(new SimpleCriterion("value", 18, Operator.GT))
                .or(new BatchCriterion(Collections.singletonList(new SimpleCriterion("code", "xxx", Operator.LIKE))))
                .and(new MapCriterion(map));
        String str = objectMapper.writeValueAsString(criterion);
        System.out.println(str);

        Criterion criterion1 = objectMapper.readValue(str, Criterion.class);
    }
}
