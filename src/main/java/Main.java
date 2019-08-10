
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {
        System.out.println("Enter an absolute path to your Node.js module folder: ");
        Scanner scanner = new Scanner(System.in);
        String rootDirectory = scanner.next();
        new FileFinderManager(rootDirectory).getLocalFiles().stream().forEach((file)->System.out.println(file));

    }

}
