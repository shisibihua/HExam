package com.library.bexam.common.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 生成token
 * @author  caoqina
 * @date 20181213
 */
public final class IDUtil {

    /**
     * 生成token
     * 生成规则：前缀_uuid_MD532(userName)
     * @param prefix 前缀
     * @param userName 用户名
     * @return
     */
    public static String getToken(String prefix,String userName){
        if(MD5(userName)==null) return null;
        return prefix+"_"+ UUID.randomUUID().toString().replaceAll("-","")+"_"+MD5(userName);
    }

    /**
     * 进行MD5加密，
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        if(s==null) return null;
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
