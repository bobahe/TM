package ru.levin.tm.util;

import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceUtil {

    @Nullable
    public static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
