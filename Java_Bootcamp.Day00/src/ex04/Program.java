import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String inputString = console.nextLine();
        int[][] histogram = new int[128][65535];
        int maxScore = 0;
        int uniqSymbols = 0;
        for (char c : inputString.toCharArray()) {
            if (c >= '\u0000' && c <= '\u007F') {
                histogram[0][c]++;
                histogram[1][c] = c;
                if (maxScore < histogram[0][c]) {
                    maxScore = histogram[0][c];
                }
            }
        }
        for (int i = 0; i < inputString.length(); i++) {
            if (histogram[1][i] != 0) {
                uniqSymbols++;
            }
        }
        if(uniqSymbols < 10){
            System.err.print("Illegal Argument");
            System.exit(-1);
        }
        int[] arrayCountNumbers = new int[uniqSymbols];
        int[] arrayCountSymbols = new int[uniqSymbols];
        int count = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (histogram[0][i] != 0) {
                arrayCountNumbers[count] = histogram[0][i];
                count++;
            }
        }
        count = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (histogram[1][i] != 0) {
                arrayCountSymbols[count] = histogram[1][i];
                count++;
            }
        }
        for (int i = 0; i < arrayCountNumbers.length - 1; i++) {
            for (int j = 0; j < arrayCountNumbers.length - i - 1; j++) {
                if (arrayCountNumbers[j] < arrayCountNumbers[j + 1]) {
                    int temp = arrayCountNumbers[j];
                    int temp2 = arrayCountSymbols[j];
                    arrayCountNumbers[j] = arrayCountNumbers[j + 1];
                    arrayCountSymbols[j] = arrayCountSymbols[j + 1];
                    arrayCountNumbers[j + 1] = temp;
                    arrayCountSymbols[j + 1] = temp2;
                }
            }
        }
        System.out.println();
        System.out.print(maxScore + "\t");
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (arrayCountNumbers[j] * 10 / maxScore >= i)
                    System.out.print("#\t");
                if (arrayCountNumbers[j] * 10 / maxScore == i - 1) {
                    if (arrayCountNumbers[j] != 0)
                        System.out.print(arrayCountNumbers[j] + "\t");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            System.out.print((char)arrayCountSymbols[i] + "\t");
        }


    }
}