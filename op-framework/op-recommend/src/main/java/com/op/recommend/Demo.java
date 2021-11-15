package com.op.recommend;

import java.util.Arrays;
import java.util.List;

/**
 * Demo
 *
 * @author cdrcool
 */
public class Demo {

    public static void main(String[] args) {
        List<Double> martrix = Arrays.asList(5d, 1d, 2d, 4d);
        List<Double> titanic = Arrays.asList(1d, 5d, null, 3d);
        List<Double> dieHard = Arrays.asList(null, 2d, 3d, 5d);
        List<Double> forrestGump = Arrays.asList(2d, 5d, 5d, 3d);
        List<Double> wallE = Arrays.asList(2d, 5d, 4d, null);
        System.out.println("result1: " + CosineSimilarity.calc(martrix, titanic));
        System.out.println("result2: " + CosineSimilarity.calc(martrix, dieHard));
        System.out.println("result3: " + CosineSimilarity.calc(martrix, forrestGump));
        System.out.println("result4: " + CosineSimilarity.calc(martrix, wallE));
        System.out.println("result5: " + CosineSimilarity.calc(titanic, dieHard));
        System.out.println("result6: " + CosineSimilarity.calc(titanic, forrestGump));
        System.out.println("result7: " + CosineSimilarity.calc(titanic, wallE));
        System.out.println("result8: " + CosineSimilarity.calc(dieHard, forrestGump));
        System.out.println("result9: " + CosineSimilarity.calc(dieHard, wallE));
        System.out.println("result10: " + CosineSimilarity.calc(forrestGump, wallE));

        List<Double> user1 = Arrays.asList(5d, 1d, null, 2d, 2d);
        List<Double> user2 = Arrays.asList(1d, 5d, 2d, 5d, 5d);
        List<Double> user3 = Arrays.asList(2d, null, 3d, 5d, 4d);
        List<Double> user4 = Arrays.asList(4d, 3d, 5d, 3d, null);
        System.out.println("result11: " + CosineSimilarity.calc(user1, user2));
        System.out.println("result12: " + CosineSimilarity.calc(user1, user3));
        System.out.println("result13: " + CosineSimilarity.calc(user1, user4));
        System.out.println("result14: " + CosineSimilarity.calc(user2, user3));
        System.out.println("result15: " + CosineSimilarity.calc(user2, user4));
        System.out.println("result16: " + CosineSimilarity.calc(user3, user4));
    }
}
