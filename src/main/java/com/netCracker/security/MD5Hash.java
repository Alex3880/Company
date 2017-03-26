package com.netCracker.security;

/**
 * Created by aleksandr on 10.02.17.
 */
public class MD5Hash {

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public boolean isValidMD5(String s) {
        return s.matches("[a-fA-F0-9]{32}");
    }

}
