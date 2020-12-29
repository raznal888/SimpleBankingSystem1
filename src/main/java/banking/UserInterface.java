package banking;

import java.util.Scanner;

class UserInterface {

    private final AccountManager manager;
    private final Scanner scanner;
    private final Menu mainMenu;
    private final Menu accountMenu;
    private Menu currentMenu;

    UserInterface(Scanner scanner, String url) {
        manager = new AccountManager(url);
        this.scanner = scanner;

        mainMenu = new Menu(this);
        mainMenu.addItem(new MenuItem("1", "Create an account", this::createAccount));
        mainMenu.addItem(new MenuItem("2", "Log into account", this::login));

        accountMenu = new Menu(this);
        accountMenu.addItem(new MenuItem("1", "Balance", this::printBalance));
        accountMenu.addItem(new MenuItem("2", "Add income", this::deposit));
        accountMenu.addItem(new MenuItem("3", "Do transfer", this::transfer));
        accountMenu.addItem(new MenuItem("4", "Close account", this::closeAccount));
        accountMenu.addItem(new MenuItem("5", "Log out", this::logout));

        currentMenu = mainMenu;
    }

    void start() {

        while (true) {
            System.out.println(currentMenu);

            String command = scanner.next();
            System.out.println();

            currentMenu.executeCommand(command);
            System.out.println();
        }

    }

    private void createAccount() {
        manager.addAccount(new Account());

        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" + manager.getCurrentCardNumber());
        System.out.println("Your card PIN:\n" + manager.getCurrentPIN());
    }

    private void login() {

        if (manager.isLoginSuccessful(enterLoginData())) {
            System.out.println("You have successfully logged in!");
            switchMenu();
        } else {
            System.out.println("Wrong card number or PIN!");
        }

    }

    private void printBalance() {
        System.out.println("Balance: " + manager.getCurrentBalance());
    }

    private void deposit() {
        System.out.println("Enter income:");
        if (scanner.hasNextInt()) {
            manager.updateBalance(scanner.nextInt());
            System.out.println("Income was added!");
        } else {
            String tmp = scanner.next();
            System.out.println("Enter an integer!");
        }

    }

    private void transfer() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber;

        if (!scanner.hasNextLong() || !passesLuhnAlgorithm(cardNumber = scanner.next())) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }

        if (cardNumber.equals(manager.getCurrentCardNumber())) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }

        if (!manager.isCardInDatabase(cardNumber)) {
            System.out.println("Such a card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        if (scanner.hasNextInt()) {
            int amount = scanner.nextInt();

            if (amount > manager.getCurrentBalance()) {
                System.out.println("Not enough money!");
                return;
            }

            manager.transfer(amount, cardNumber);
            System.out.println("Success!");
        } else {
            String tmp = scanner.next();
            System.out.println("Enter an integer!");
        }

    }

    private void closeAccount() {
        manager.deleteCurrentAccount();
        System.out.println("The account has been closed!");

        switchMenu();
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
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();

        loginData[0] = cardNumber;
        loginData[1] = pin;

        System.out.println();

        return loginData;
    }

    private void switchMenu() {
        currentMenu = currentMenu == mainMenu ? accountMenu : mainMenu;
    }

    private boolean passesLuhnAlgorithm(String cardNumber) {
        return Account.luhnAlgorithmChecksum(cardNumber) == 0;
    }
}
