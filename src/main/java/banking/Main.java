package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        if (!args[0].equals("-fileName") && args[1].matches(".+\\.s3db")) {
            throw new IllegalArgumentException("Usage: -fileName [database name].s3db");
        }

        String url = "jdbc:sqlite:" + args[1];

        final Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(scanner, url);

        ui.start();
    }
}
