package ex02;

import java.util.Random;

public class Program {
    public static final String FIRST_ARG = "--arraySize=";
    public static final String SECOND_ARG = "--threadsCount=";

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith(FIRST_ARG) || !args[1].startsWith(SECOND_ARG)) {
            System.out.println("Incorrect input: Before starting the program, you must specify \"--arraySize=\" and \"--threadsCount=\"");
            System.exit(-1);
        }

        int arraySize;
        int threadsCount;

        try {
            arraySize = Integer.parseInt(args[0].substring(FIRST_ARG.length()));
            threadsCount = Integer.parseInt(args[1].substring(SECOND_ARG.length()));
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input: arraySize and threadsCount should be valid numbers");
            System.exit(-1);
            return;
        }

        if (arraySize <= 0 || threadsCount <= 0 || threadsCount > arraySize) {
            System.out.println("Incorrect input: arraySize should be positive and threadsCount should be positive and not greater than arraySize");
            System.exit(-1);
        }

        int[] array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(2001) - 1000;
        }

        int singleThreadSum = 0;
        for (int i : array) {
            singleThreadSum += i;
        }
        System.out.println("Sum: " + singleThreadSum);

        int[] threadSums = new int[threadsCount];
        Thread[] threads = new Thread[threadsCount];
        int sectionSize = arraySize / threadsCount;
        int remainder = arraySize % threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            int start = i * sectionSize;
            int end = (i == threadsCount - 1) ? 
                (start + sectionSize + remainder - 1) : (start + sectionSize - 1);

            threads[i] = new SumCalculator(array, start, end, threadSums, i);
            threads[i].start();
        }

        int multiThreadSum = 0;

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
                multiThreadSum += threadSums[i];
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Sum by threads: " + multiThreadSum);
    }
}
