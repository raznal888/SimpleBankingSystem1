package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner SCANNER = new Scanner(System.in);
        UserInterface ui = new UserInterface(SCANNER);

        ui.start();
    }
}
