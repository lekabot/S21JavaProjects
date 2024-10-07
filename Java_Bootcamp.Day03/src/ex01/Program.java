package ex01;

public class Program {
    private static final String ARG_START = "--count=";

    public static void main(String[] args) {
        if (args.length != 1 && !args[0].startsWith(ARG_START)) {
            System.out.println("Incorrect input: Before starting the program, you must specify \"--count=\"");
            System.exit(-1);
        }
        
        int count;
        try {
            count = Integer.parseInt(args[0].substring(ARG_START.length()));
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input: count should be a valid number");
            System.exit(-1);
            return;
        }

        if (count <= 0) {
            System.out.println("Incorrect input: count should be positive " + count);
            System.exit(-1);
        }

        Printer printer = new Printer(count);
        Egg egg = new Egg(printer, count);
        Hen hen = new Hen(printer, count);

        egg.start();
        hen.start();

        try {
            egg.join();
            hen.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}