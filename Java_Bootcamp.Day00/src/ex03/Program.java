import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String currentWeek = "";
        String result = "";
        int min = 0;
        int min2 = 0;
        for (int i = 1; i < 19; i++) {
            currentWeek = console.nextLine();
            if (currentWeek.equals("42")) {
                break;
            }
            if (currentWeek.equals("Week " + i)) {
                result = result + currentWeek + " ";
            } else {
                System.err.print("Illegal Argument");
                System.exit(-1);
            }
            min2 = console.nextInt();
            for (int j = 0; j < 4; j++) {
                min = console.nextInt();
                if (min2 > min) {
                    min2 = min;
                }
            }
            console.nextLine();
            for (int j = 0; j < min2; j++) {
                result += "=";
            }
            result += ">\n";
        }
        System.out.print(result);
    }
}