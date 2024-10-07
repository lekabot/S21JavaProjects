package ex01;

public class Hen extends Thread {
    private final Printer printer;
    private final int count;

    public Hen(Printer printer, int count) {
        this.count = count;
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.printHen();
    }
}
