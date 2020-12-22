package banking;

import java.util.Scanner;

class UserInterface {

    private final AccountManager MANAGER;
    private final Scanner SCANNER;
    private Menu mainMenu;
    private Menu accountMenu;
    private Menu currentMenu;

    UserInterface(Scanner scanner) {
        MANAGER = new AccountManager();
        this.SCANNER = scanner;

        mainMenu = new Menu(this);
        mainMenu.addItem(new MenuItem("1", "Create an account", this::createAccount));
        mainMenu.addItem(new MenuItem("2", "Log into account", this::login));

        accountMenu = new Menu(this);
        accountMenu.addItem(new MenuItem("1", "Balance", this::printBalance));
        accountMenu.addItem(new MenuItem("2", "Log out", this::logout));

        currentMenu = mainMenu;
    }

    void start() {

        while (true) {
            System.out.println(currentMenu);

            String command = SCANNER.next();
            System.out.println();

            currentMenu.executeCommand(command);

            System.out.println();
        }

    }

    private void createAccount() {
        MANAGER.add(new Account());

        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" + MANAGER.getCurrentAccount().getCARD_NUMBER());
        System.out.println("Your card PIN:\n" + MANAGER.getCurrentAccount().getPIN());
    }

    private void login() {
        if (MANAGER.isLoginSuccessful(enterLoginData())) {
            System.out.println("You have successfully logged in!");
            switchMenu();
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    private void printBalance() {
        System.out.println("Balance: " + MANAGER.getCurrentAccount().getBalance());
    }

    private void logout() {
        System.out.println("You have successfully logged out!");
        switchMenu();
    }

    void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private String[] enterLoginData() {
        String[] loginData = new String[2];

        System.out.println("Enter your card number:");
        String cardNumber = SCANNER.next();
        System.out.println("Enter your PIN:");
        String pin = SCANNER.next();

        loginData[0] = cardNumber;
        loginData[1] = pin;

        System.out.println();

        return loginData;
    }

    private void switchMenu() {
        if (currentMenu == mainMenu) {
            currentMenu = accountMenu;
        } else {
            currentMenu = mainMenu;
        }
    }
}
