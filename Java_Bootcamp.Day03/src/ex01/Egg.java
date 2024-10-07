package ex01;

public class Egg extends Thread {
    private final Printer printer;
    private final int count;

    public Egg(Printer printer, int count) {
        this.printer = printer;
        this.count = count;
    }

    @Override
    public void run() {
        printer.printEgg();
    }

}
