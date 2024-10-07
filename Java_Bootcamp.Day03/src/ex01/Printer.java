package ex01;

public class Printer {
    private final int count;
    private boolean isEggTurn = true;

    public Printer(int count) {
        this.count = count;
    }

    public synchronized void printEgg() {
        for (int i = 0; i < count; i++) {
            while (!isEggTurn) { 
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Egg");
            isEggTurn = false;
            notify();
        }
    }

    public synchronized void printHen() {
        for (int i = 0; i < count; i++) {
            while (isEggTurn) { 
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Hen");
            isEggTurn = true;
            notify();
        }
    }
}
