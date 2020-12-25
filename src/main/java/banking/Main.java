package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner SCANNER = new Scanner(System.in);

        if (!args[0].equals("-fileName") && args[1].matches(".+\\.s3db")) {
            throw new IllegalArgumentException("Usage: -fileName [database name].s3db");
        }

        String url = "jdbc:sqlite:" + args[1];

        UserInterface ui = new UserInterface(SCANNER, url);

        ui.start();
    }
}
