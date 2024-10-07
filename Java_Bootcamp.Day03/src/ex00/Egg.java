package ex00;

public class Egg extends Thread {
    private final int count;

    public Egg(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.err.println("Egg");
        }
    }

}
