package com.op.admin.utils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 密码生成器
 *
 * @author cdrcool
 */
public class PasswordGenerator {

    /**
     * 随机生成指定长度的密码字符串
     *
     * @param length 密码长度
     * @return 密码字符串
     */
    public static String generate(int length) {
        String[] arr = new String[]{
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "~", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "|", "`", ".",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
        };
        Random random = new Random();

        return IntStream.range(0, length).mapToObj(i -> {
            int pos = random.nextInt(arr.length);
            return arr[pos];
        }).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        String pwd = generate(6);
    }
}
