package com.nashss.se.ClimbingGymCompanionService.utils;

import java.util.Base64;

public class DecoderUtils {
    private DecoderUtils() {

    }

    /**
     *
     * @param jsonString to convert to byte array
     * @return the byte array
     */
    public static byte[] byteBase64Decode(String jsonString) {
        return Base64.getDecoder().decode(jsonString.getBytes());
    }
}
