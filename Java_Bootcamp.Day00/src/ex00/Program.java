public class Program {
    public static void main(String[] args) {
        int number = 479598;

        int sum = sumOfDigits(number);

        System.out.println(sum);
    }

    public static int sumOfDigits(int number) {
        int sum = 0;

        number = Math.abs(number);

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
