package ex02;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;;

public class Program {

    private static Path path, nPath;
    private static final String ARG_START = "--current-folder=";

    public static void main(String[] args) {
        checkArgs(args);
        path = Paths.get(args[0].substring(ARG_START.length()));
        checkPath(path);
        System.out.println(path);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            scanInput(scanner);
        }
    }

    private static void checkArgs(String[] args) {
        if (args.length != 1 || !args[0].contains(ARG_START)) {
            System.out.println("You should write one argument like \"--current-folder=YOUR_FOLDER\"");
            System.exit(-1);
        }
    }

    private static void checkPath(Path path) {
        if (!path.isAbsolute()) {
            System.out.println("Error: Path must be absolute");
            System.exit(-1);
        }
        if (!Files.isDirectory(path)) {
            System.out.println("Error: Path must be a directory");
            System.exit(-1);
        }
    }    

    private static void scanInput(Scanner scanner) {
        String inputString = scanner.nextLine().trim();
        String[] inputArray = inputString.split("\\s+");

        if (inputArray.length == 1 && inputArray[0].equals("exit")) {
            scanner.close();
            System.exit(0);
        } else if (inputArray.length == 1 && inputArray[0].equals("ls")) {
            list();
        } else if (inputArray[0].equals("cd")) {
            if (inputArray.length == 2) {
                changeDirectory(inputArray[1]);
            } else {
                System.out.println("cd: wrong number of arguments");
            }
        } else if (inputArray[0].equals("mv")) {
            if (inputArray.length == 3) {
                move(inputArray[1], inputArray[2]);
            } else {
                System.out.println("mv: missing file operand");
            }
        } else {
            System.out.println(inputArray[0] + " Command not found");
        }
    }

    private static void changeDirectory(String dir) {
        if (isDirectory(dir) && Files.exists(nPath)) {
            path = nPath;
            System.out.println(path);
        } else {
            System.out.println("cd: " + dir + ": Not a directory");
        }
    }

    private static void move(String what, String where) {
        Path source = null;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path temp : files) {
                if (temp.getFileName().toString().equals(what) && Files.isRegularFile(temp)) {
                    source = temp;
                    break;
                }
            }

            if (source == null) {
                System.out.println("mv: " + what + " no such file");
                return;
            }

            if (isDirectory(where)) {
                Files.move(source, nPath.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source, source.resolveSibling(Paths.get(where)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void list() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path path : files) {
                long size = 0;
                if (Files.isDirectory(path)) {
                    size = directorySize(path);
                } else {
                    size = Files.size(path);
                }
                System.out.println(path.getFileName() + " " + size + " KB");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static long directorySize(Path path) throws IOException {
        return Files.walk(path)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }

    private static boolean isDirectory(String strPath) {
        nPath = Paths.get(strPath);
        nPath = path.resolve(nPath).normalize();

        return Files.isDirectory(nPath);
    }

}
