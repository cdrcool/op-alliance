package com.op.boot.mall.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
public class JdDESUtils {
    public static String encrypt(String message, String key) throws Exception {
        if (StringUtils.isEmpty(message)) {
            return message;
        }
        try {
            Cipher cipher = get(Cipher.ENCRYPT_MODE, key);

            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] decoded = cipher.doFinal(bytes);
            return Hex.encodeHexString(decoded);
        } catch (Exception e) {
            log.error("京东加密失败, 返回原数据:{}", message, e);
            return message;
        }
    }

    public static String decrypt(String message, String key) {
        if (StringUtils.isEmpty(message)) {
            return message;
        }
        try {
            Cipher cipher = get(Cipher.DECRYPT_MODE, key);

            byte[] encoded = Hex.decodeHex(message);
            byte[] bytes = cipher.doFinal(encoded);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("京东解密失败, 返回原数据:{}", message, e);
            return message;
        }
    }

    private static Cipher get(int flag, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(keyBytes);

        cipher.init(flag, secretKey, iv);
        return cipher;
    }

    public static String encodePassword(String password, String key) {
        if (StringUtils.isEmpty(password)) {
            return password;
        }


        return password;
    }


}
