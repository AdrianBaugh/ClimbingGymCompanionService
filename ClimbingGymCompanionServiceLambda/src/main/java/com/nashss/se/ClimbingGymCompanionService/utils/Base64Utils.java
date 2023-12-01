package com.nashss.se.ClimbingGymCompanionService.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Utils {
    private static final String TMP_PATH = "/tmp/";
    private static final Logger log = LogManager.getLogger();
    private Base64Utils() {
    }

        public static Path convertBase64ToFile(String base64String, String fileName) {
            System.out.println("Attempting to convert base64String to file");
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
                 FileOutputStream outputStream = new FileOutputStream(TMP_PATH + fileName)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("returning file path to converted file");
            return Paths.get(TMP_PATH, fileName);
        }

    public static String convertBufferedReaderToBase64(BufferedReader bufferedReader) {
        System.out.println("Attempting to create Base64 string from BufferedReader");
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            byte[] fileContent = stringBuilder.toString().getBytes();
            String base64String = Base64.getEncoder().encodeToString(fileContent);
            System.out.println("Returning Base64 string from BufferedReader");
            return base64String;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
