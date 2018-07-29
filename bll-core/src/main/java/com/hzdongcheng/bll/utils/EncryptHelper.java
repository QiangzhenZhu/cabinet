package com.hzdongcheng.bll.utils;

import android.text.StaticLayout;
import android.util.Base64;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptHelper {

    public static String md5Encrypt(String encryptString) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 加密后的字符串
            byte[] md5bytes = md5.digest(encryptString.getBytes("utf-8"));
            String newstr = "";
            for (byte temp : md5bytes) {
                String tmp = Integer.toHexString(temp & 0xFF);
                newstr += tmp.length() < 2 ? ("0" + tmp) : tmp;
            }
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static RSAPublicKey rsaPublicKey = null;
    public static String str_Public_Key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0NhQfIeSzLjUyPlvY9/" +
            "BfzHt4t9Vi9PQ3CtshVioPrSV59IFRay/WO06CAcCavf3Qq/Mc9TZqfkVTy9Rdx3vNlHHgmN9m0oku9VTSxtIX" +
            "Qkn5t43AIRW7ZUIsnDSxl8bkzPCC1wKt5DevveAkla3181J9fCVIdyfEM0gGp42IvQIDAQAB";


    /**
     *      * BCD转字符串
     *     
     */
    public static String bcd2Str(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];
        char val;
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    public static void loadPublicKey(String pubKey) {
        RSAPublicKey publicKey = null;
        try {
            byte[] buffer = Base64.decode(pubKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rsaPublicKey = publicKey;
    }

    /**************************** RSA 公钥加密解密**************************************/
    public static String decryptWithRSA(String encryedData) {
        if (rsaPublicKey == null) {
            loadPublicKey(str_Public_Key);
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] output = cipher.doFinal(encryedData.getBytes());
            return bcd2Str(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final int RADIX = 16;
    private static final String SEED = "0933910847463829232312312";

    /**
     *  加密
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }
        BigInteger biPassword = new BigInteger(password.getBytes());
        BigInteger bi_r0 = new BigInteger(SEED);
        BigInteger bi_r1 = bi_r0.xor(biPassword);

        return bi_r1.toString(RADIX);
    }
}
