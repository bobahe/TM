package ru.levin.tm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceUtil {
    public static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
