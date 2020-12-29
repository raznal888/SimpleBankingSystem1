package banking;

import java.util.Random;

class Account {
    private static final Random random = new Random();
    private String cardNumber;
    private String pin;
    private int balance;

    Account() {
        cardNumber = generateCardNumber();
        pin = generatePIN();
        balance = 0;
    }

    static int luhnAlgorithmChecksum(String cardNumber) {

        int end = cardNumber.length() == 16 ? cardNumber.length() - 1 : cardNumber.length();

        int luhnSum = 0;
        for (int i = 0; i < end; i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == 0) {
                digit = (2 * digit > 9) ? (2 * digit - 9) : 2 * digit;
            }

            luhnSum += digit;
        }

        if (cardNumber.length() == 16) {
            luhnSum += Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1));
        }

        return (10 - (luhnSum % 10)) % 10;

    }

    String getCardNumber() {
        return cardNumber;
    }

    String getPin() {
        return pin;
    }

    int getBalance() {
        return balance;
    }

    void setBalance(int amount) {
        balance += amount;
    }

    void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    void setPin(String pin) {
        this.pin = pin;
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder("400000");

        String accountIdentifier = String.format("%09d", random.nextInt(1_000_000_000));
        cardNumber.append(accountIdentifier);

        int lastDigit = luhnAlgorithmChecksum(cardNumber.toString());
        cardNumber.append(lastDigit);

        return cardNumber.toString();
    }

    private String generatePIN() {
        int pin = random.nextInt(10_000);

        return String.format("%04d", pin);
    }
}
