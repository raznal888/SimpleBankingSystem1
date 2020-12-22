package banking;

import java.util.Random;

class Account {
    private final Random RANDOM = new Random();
    private final String CARD_NUMBER;
    private final String PIN;
    private double balance;

    Account() {
        CARD_NUMBER = generateCardNumber();
        PIN = generatePIN();
        balance = 0;
    }

    String getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    String getPIN() {
        return PIN;
    }

    double getBalance() {
        return balance;
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder("400000");

        int luhnSum = 8;
        for (int i = 0; i < 9; i++) {
            int digit = RANDOM.nextInt(10);
            cardNumber.append(digit);
            if (i % 2 == 0) {
                digit = 2 * digit > 9 ? 2 * digit - 9 : 2 * digit;
            }
            luhnSum += digit;
        }

        int lastDigit = (10 - (luhnSum % 10)) % 10;
        cardNumber.append(lastDigit);

        return cardNumber.toString();
    }

    private String generatePIN() {
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(RANDOM.nextInt(10));
        }

        return pin.toString();
    }
}
