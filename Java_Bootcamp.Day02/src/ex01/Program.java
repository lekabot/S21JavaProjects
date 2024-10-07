package ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {

    private static int[] firstVector;
    private static int[] secondVector;
    private static final List<String> dictionary = new ArrayList<>();

    public static void main(String[] args) {
        checkArgs(args);
        readFiles(args);
        getSimilarity();
    }
    
    private static  void checkArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("You have to pass two files as arguments");
            System.exit(-1);
        }
    }

    private static void getSimilarity() {
        double numerator = 0.0;
        double firstSqrt = 0.0;
        double secondSqrt = 0.0;

        for (int i = 0; i < firstVector.length; i++) {
            firstSqrt += firstVector[i] * firstVector[i];
            secondSqrt += secondVector[i] * secondVector[i];
            numerator += firstVector[i] * secondVector[i];
        }

        double similarity = 0;

        if (firstSqrt > 0 && secondSqrt > 0) {
            similarity = numerator / (Math.sqrt(firstSqrt) * Math.sqrt(secondSqrt));
        }
        System.out.println("Similarity = " + Math.floor(similarity * 100.00) / 100.00);
    }

    private static void readFiles(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            BufferedReader firsReader = new BufferedReader(new FileReader(args[0]));
            BufferedReader secondReader = new BufferedReader(new FileReader(args[1]));

            fillDictionary(firsReader);
            fillDictionary(secondReader);

            firstVector = new int[dictionary.size()];
            secondVector = new int[dictionary.size()];

            firsReader = new BufferedReader(new FileReader(args[0]));
            secondReader = new BufferedReader(new FileReader(args[1]));

            fillVector(firsReader, firstVector);
            fillVector(secondReader, secondVector);

            for (String word : dictionary) {
                writer.write(word);
                writer.newLine();
            }


        } catch (IOException e) {
            System.out.println("File not found exception");
        }
    }

    private static void fillDictionary(BufferedReader bf) throws IOException {
        String string;

        while ((string = bf.readLine()) != null) {
            String[] words = string.replace("\\p{Punct}", "")
                .toLowerCase().split("\\s+");

            for (String word : words) {
                if (!dictionary.contains(word) && !word.isEmpty()) {
                    dictionary.add(word);
                }
            }
        }
        bf.close();
    }

    private static void fillVector(BufferedReader reader, int[] vector) throws IOException {
        String string;
        int index;

            while ((string = reader.readLine()) != null) {
                String[] words = string.replace("\\p{Punct}", "")
                    .toLowerCase().split("\\s+");

            for (String word : words) {
                if (!word.isEmpty()) {
                    index = dictionary.indexOf(word);
                    vector[index]++;
                }
            }
        }
        reader.close();
    }
}
