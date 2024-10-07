package ex00;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SigatureRead {
    public static final String SIGNATURES_TXT_PATH = "./signatures.txt";

    public static Map<String, String> read() {
        Map<String, String> signatures = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(SIGNATURES_TXT_PATH)) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String format = scanner.next().replace(',', ' ').trim();
                String byteCode = scanner.nextLine().trim();
                signatures.put(format, byteCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return signatures;
    }

}
