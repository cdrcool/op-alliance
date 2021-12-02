package com.op.boot.recommendation;

import com.op.boot.recommendation.recommender.BarCodeBaseRecommender;
import com.op.boot.recommendation.recommender.HeatBasedRecommender;
import com.op.boot.recommendation.recommender.ItemBasedCfRecommender;
import com.op.boot.recommendation.recommender.UserBasedCfRecommender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendersTest {
    @Autowired
    private UserBasedCfRecommender userBasedCfRecommender;

    @Autowired
    private ItemBasedCfRecommender itemBasedCfRecommender;

    @Autowired
    private HeatBasedRecommender heatBasedRecommender;

    @Autowired
    private BarCodeBaseRecommender barCodeBaseRecommender;

    @Test
    public void testUserBasedCfRecommender() {
        userBasedCfRecommender.initialize();
    }

    @Test
    public void testItemBasedCfRecommender() {
        itemBasedCfRecommender.initialize();
    }

    @Test
    public void testHeatBasedRecommender() {
        heatBasedRecommender.initialize();
    }

    @Test
    public void testBarCodeBaseRecommender() {
        barCodeBaseRecommender.initialize();
    }
}
