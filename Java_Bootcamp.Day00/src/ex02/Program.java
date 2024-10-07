import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> weekData = new TreeMap<>();
        boolean[] weekEntered = new boolean[19];
        boolean inputEnded = false;

        while (!inputEnded) {
            String line = scanner.nextLine();
            
            if (line.equals("42")) {
                inputEnded = true;
                continue;
            }
            
            if (line.startsWith("Week ")) {
                try {
                    int weekNumber = Integer.parseInt(line.split(" ")[1]);
                    
                    if (weekNumber < 1 || weekNumber > 18 || weekEntered[weekNumber]) {
                        throw new IllegalArgumentException();
                    }
                    
                    weekEntered[weekNumber] = true;

                    String[] grades = scanner.nextLine().split(" ");
                    if (grades.length != 5) {
                        throw new IllegalArgumentException();
                    }
                    
                    int minGrade = Integer.MAX_VALUE;
                    for (String grade : grades) {
                        int score = Integer.parseInt(grade);
                        if (score < 1 || score > 9) {
                            throw new IllegalArgumentException();
                        }
                        minGrade = Math.min(minGrade, score);
                    }
                    
                    weekData.put(weekNumber, minGrade);
                    
                } catch (Exception e) {
                    System.out.println("IllegalArgument");
                    System.exit(-1);
                }
            } else {
                System.out.println("IllegalArgument");
                System.exit(-1);
            }
        }
        
        for (Map.Entry<Integer, Integer> entry : weekData.entrySet()) {
            int weekNumber = entry.getKey();
            int minGrade = entry.getValue();
            
            System.out.print("Week " + weekNumber + " ");
            for (int i = 0; i < minGrade; i++) {
                System.out.print("=");
            }
            System.out.println(">");
        }

        scanner.close();
    }
}
