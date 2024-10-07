package ex00;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        Map<String, String> signatures = SigatureRead.read();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.equals("42")) {
            SignaturesChecker.analise(signatures, input);
            input = scanner.nextLine();
        }
    }
}
