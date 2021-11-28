package com.op.recommend;

import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengdr01
 */
public class UJMPExamples {

    public static void main(String[] args) {
        Matrix sparse = SparseMatrix.Factory.zeros(2, 3000000);
        sparse.setAsInt(1, 0L, 0L);
        sparse.setAsInt(2, 0L, 1L);
        sparse.setAsInt(3, 0L, 2L);
        sparse.setAsInt(4, 1L, 0L);
        sparse.setAsInt(5, 1L, 1L);
        sparse.setAsInt(6, 1L, 2L);
        System.out.println(sparse);

        Matrix similarity = sparse.cosineSimilarity(Calculation.Ret.NEW, true);
        System.out.println(similarity);
        double[][] array = sparse.cosineSimilarity(Calculation.Ret.NEW, true).toDoubleArray();

        int index = 0;
        int n = 10;

        List<Double> collect = Arrays.stream(array[index]).boxed().sorted(Comparator.reverseOrder()).limit(n).collect(Collectors.toList());
        System.out.println(collect);

        ArrayList<String> xx = new ArrayList<>();
        xx.add("aaa,bbb,ccc");
        xx.add("aaa,sss,ccc");
        xx.add("aaa,ddd,ccc");
        xx.add("aaa,bbb,ccc");
        ArrayList<String> result = oneHot(xx, 1);
    }

    public static ArrayList<String> oneHot(ArrayList<String> list, int index)  {

//      建立键值
        HashSet<String> set = new HashSet<>();
        for (String l : list) {
            set.add(l.split(",")[index]);
        }
        System.out.println(set.size());

//        为键值映射数组下表
        HashMap<String, Integer> toIndex = new HashMap<>();
        int ind = 0;
        for (String a : set) {
            toIndex.put(a, ind);
            ind++;
        }
//      开始编码
        for (int i=0; i<list.size(); i++) {
            String a[] = new String[set.size()];
            a[ toIndex.get( list.get(i).split(",")[index] ) ] = "1";
            list.set(i, list.get(i) + ","+String.join(",", a));
        }

        return list;
    }
}
