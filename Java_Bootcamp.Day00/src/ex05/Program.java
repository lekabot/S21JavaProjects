import java.util.Scanner;

public class Program {
    public static String[] weekDays = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
    public static String[] monthDays =
            {"TU", "WE", "TH", "FR", "SA", "SU",
                    "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                    "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                    "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                    "MO", "TU", "WE"};

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String[] nameStudents = new String[10];
        int[][] timeDay = new int[5][7];
        boolean[][][] MonthSchedule = new boolean[30][7][5];

        getStudents(console, nameStudents);
        int[][][][] generalSchedule = new int[nameStudents.length][5][30][1];
        getDay(console, timeDay);
        setMonthSchedule(MonthSchedule, timeDay);
        generalSchedule(console, nameStudents, generalSchedule);
        printHeadTable(MonthSchedule);
        printResultTable(nameStudents, generalSchedule, MonthSchedule);
    }

    public static void getStudents(Scanner console, String[] nameStudents) {
        for (int i = 0; i < 10; i++) {
            String currentName = console.nextLine();
            if (currentName.equals(".")) break;
            nameStudents[i] = currentName;
        }
    }

    public static void getDay(Scanner console, int[][] timeDay) {
        int i = 0;
        while (true) {
            String currentDayDateTraning = console.nextLine();
            if (currentDayDateTraning.equals(".")) break;
            String[] splitCurrentDay = currentDayDateTraning.split(" ");
            int time = Integer.parseInt(splitCurrentDay[0]);
            int day = getDayIndex(splitCurrentDay[1]);
            timeDay[time][day] = 1;
            i++;
        }
    }

    public static int getDayIndex(String day) {
        int result = 0;
        for (int i = 0; i < weekDays.length; i++) {
            if (weekDays[i].equals(day)) result = i;
        }
        return result;
    }

    public static int getStudentIndex(String[] studentName, String name) {
        int index = 0;
        for (int i = 0; i < studentName.length; i++) {
            if (name.equals(studentName[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void setMonthSchedule(boolean[][][] MonthSchedule, int[][] timeDay) {
        int dayWeek = 1;
        for (int i = 0; i < monthDays.length; i++) {
            for (int k = 0; k < 5; k++) {
                if (timeDay[k][dayWeek] == 1) {
                    MonthSchedule[i][dayWeek][k] = true;
                } else {
                    for (int j = 0; j < 7; j++) {
                        MonthSchedule[i][j][k] = false;
                    }
                }
            }
            dayWeek = (dayWeek + 1) % 7;
        }
    }

    public static void generalSchedule(Scanner console, String[] nameStudents,
                                       int[][][][] generalSchedule) {
        int i = 0;
        while (true) {
            String currentGeneralSchedule = console.nextLine();
            if (currentGeneralSchedule.equals(".")) break;
            String[] splitCurrentDayGeneralSchedule = currentGeneralSchedule.split(" ");
            int indexNameStudent = getStudentIndex(nameStudents, splitCurrentDayGeneralSchedule[0]);
            int time = Integer.parseInt(splitCurrentDayGeneralSchedule[1]);
            int day = Integer.parseInt(splitCurrentDayGeneralSchedule[2]);
            String isHere = splitCurrentDayGeneralSchedule[3];
            if (isHere.equals("HERE")) {
                generalSchedule[indexNameStudent][time][day - 1][0] = 1;
            } else if (isHere.equals("NOT_HERE")) {
                generalSchedule[indexNameStudent][time][day - 1][0] = -1;
            }

        }
    }

    public static void printHeadTable(boolean[][][] MonthSchedule) {
        String[] times = {"1:00", "2:00", "3:00", "4:00", "5:00"};
        System.out.format("%10s", "");
        for (int i = 0; i < MonthSchedule.length; i++) {
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 5; k++) {
                    if (MonthSchedule[i][j][k]) {
                        if (i < 10) {
                            System.out.format("%-5s %2s %2d|", times[k], weekDays[j], (i + 1));
                        } else {
                            System.out.format("%-5s %2s %2d|", times[k], weekDays[j], (i + 1));
                        }
                    }
                }
            }
        }
        System.out.println();
    }


    public static void printResultTable(String[] nameStudents, int[][][][] generalSchedule, boolean[][][] MonthSchedule) {
        for (int i = 0; i < nameStudents.length; i++) {
            if (nameStudents[i] != null) {
                System.out.format("%-10s", nameStudents[i]);
                for (int j = 0; j < MonthSchedule.length; j++) {
                    for (int k = 0; k < 7; k++) {
                        for (int l = 0; l < 5; l++) {
                            if (MonthSchedule[j][k][l]) {
                                if (generalSchedule[i][l][j][0] == 1) {
                                    System.out.format("%12s", "1|");
                                } else if (generalSchedule[i][l][j][0] == -1) {
                                    System.out.format("%12s", "-1|");
                                } else {
                                    System.out.format("%12s", "|");
                                }
                            }
                        }
                    }
                }
                System.out.println();
            }
        }
    }
}