import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        int iterations = 0;
        boolean isPrime = true;

        if (number <= 1) {
            System.out.println("Illegal Argument");
            System.exit(-1);
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            iterations++;
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }

        if (isPrime) {
            System.out.println("true " + iterations);
        } else {
            System.out.println("false " + iterations);
        }

        in.close();
    }
}
