package cn.wolfshadow.gs.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Md5Util {

    public static String md5(String ... strs) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        for(String str : strs) sb.append(str);
        return  md5(sb.toString());
    }

    /**
     * 使用MessageDigest的MD5加密
     *
     * @param str
     * @return
     * @author ServerZhang
     * @date 2015年6月18日
     */
    public static String md5(String str) throws NoSuchAlgorithmException {
        String newStr = "";
        MessageDigest md5 = MessageDigest.getInstance("MD5");// 申明使用MD5算法
        md5.update(str.getBytes());
        byte[] digest = md5.digest();
        // newStr = byte2Hex(digest);
        newStr = NumberSystemUtil.bytes2HexStr(digest);

        return newStr;
    }
}
