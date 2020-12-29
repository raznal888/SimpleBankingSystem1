package banking;

import java.sql.*;

class AccountManager {
    private Account currentAccount;
    private final String URL;

    AccountManager(String URL) {
        currentAccount = new Account();
        this.URL = URL;

        try (Connection con = DriverManager.getConnection(URL);
             Statement s = con.createStatement()) {

            String command =
                    "CREATE TABLE IF NOT EXISTS card (\n" +
                            "id INTEGER,\n" +
                            "number TEXT,\n"+
                            "pin TEXT,\n" +
                            "balance INTEGER DEFAULT 0\n" +
                    ");";

            s.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    String getCurrentCardNumber() { return currentAccount.getCardNumber(); }

    String getCurrentPIN() {
        return currentAccount.getPin();
    }

    int getCurrentBalance() {
        return currentAccount.getBalance();
    }

    void setCurrentBalance(int amount) {
        currentAccount.setBalance(amount);
    }

    boolean isLoginSuccessful(String[] loginData) {
        boolean check = false;

        String insert = "SELECT balance FROM card WHERE number = ? AND pin = ?";

        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, loginData[0]);
            ps.setString(2, loginData[1]);

            try (ResultSet accountData = ps.executeQuery()) {
                if (check = accountData.next()) {
                    currentAccount.setCardNumber(loginData[0]);
                    currentAccount.setPin(loginData[1]);
                    currentAccount.setBalance(accountData.getInt("balance"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return check;
    }

    boolean isCardInDatabase(String cardNumber) {

        boolean check = false;

        String insert = "SELECT 1 FROM card WHERE number = ?";

        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, cardNumber);

            try (ResultSet cardData = ps.executeQuery()) {
                check = cardData.next();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return check;
    }

    void addAccount(Account account) {
        setCurrentAccount(account);

        String insert = "INSERT INTO card (number, pin) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, currentAccount.getCardNumber());
            ps.setString(2, currentAccount.getPin());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateBalance(int amount) {
        currentAccount.setBalance(amount);

        String insert = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setInt(1, amount);
            ps.setString(2, getCurrentCardNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void transfer(int amount, String cardNumber) {
        try (Connection con = DriverManager.getConnection(URL)) {

            con.setAutoCommit(false);

            String insert = "UPDATE card SET balance = balance + ? WHERE number = ?";

            try (PreparedStatement sender = con.prepareStatement(insert);
                PreparedStatement receiver = con.prepareStatement(insert)) {

                sender.setInt(1, -1 * amount);
                sender.setString(2, getCurrentCardNumber());
                sender.executeUpdate();

                receiver.setInt(1, amount);
                receiver.setString(2, cardNumber);
                receiver.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        setCurrentBalance(-1 * amount);
    }

    void deleteCurrentAccount() {

        String insert = "DELETE FROM card WHERE number = ?";

        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, getCurrentCardNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
