package ex03;

import java.io.*;
import java.util.*;

public class Program {
    public static final String URLS_FILE = "files_urls.txt";
    private static final String ARG_THREADS_COUNT = "--threadsCount=";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(ARG_THREADS_COUNT)) {
            System.out.println("Incorrect input: Before starting the program, you must specify \"--threadsCount=\"");
            System.exit(-1);
        }

        int threadsCount;
        try {
            threadsCount = Integer.parseInt(args[0].substring(ARG_THREADS_COUNT.length()));
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input: threadsCount should be a valid number");
            System.exit(-1);
            return;
        }

        if (threadsCount <= 0) {
            System.out.println("Incorrect input: threadsCount should be positive");
            System.exit(-1);
        }

        List<String> urls = readUrlsFromFile(URLS_FILE);
        if (urls.isEmpty()) {
            System.out.println("The URL list is empty or file not found.");
            System.exit(-1);
        }

        DownloadManager downloadManager = new DownloadManager(urls, threadsCount);
        downloadManager.startDownload();
    }

    private static List<String> readUrlsFromFile(String fileName) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return urls;
    }
}