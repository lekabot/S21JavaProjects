package ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class SignaturesChecker {
    
    public static void analise(Map<String, String> signatures, String input) {
        try (FileInputStream fileInputStream = new FileInputStream(input)) {
            byte[] bytes = new byte[8];
            int resultReading = fileInputStream.read(bytes);
            if (resultReading != -1) {
                String result = bytesToHex(bytes);
                writeSignature(signatures, result);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private static void writeSignature(Map<String, String> signatures, String result) {
        try (FileOutputStream outputStream = new FileOutputStream("./result.txt", true)) {
            for (Map.Entry<String, String> item : signatures.entrySet()) {
                if (result.contains(item.getValue())) {
                    outputStream.write(item.getKey().getBytes());
                    outputStream.write('\n');
                    System.out.println("PROCESSED");
                    return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
