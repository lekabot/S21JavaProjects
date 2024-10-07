package ex02;

public class SumCalculator extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private final int[] threadSums;
    private final int threadIndex;

    public SumCalculator(int[] array, int start, int end, int[] threadSums, int threadIndex) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.threadSums = threadSums;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += array[i];
        }
        threadSums[threadIndex] = sum;
        System.out.println("Thread " + (threadIndex + 1) + ": from " + start + " to " + end + " sum is " + sum);
    }
}