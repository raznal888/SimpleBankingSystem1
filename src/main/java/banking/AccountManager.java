package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class AccountManager {
    private ArrayList<Account> accounts;
    private Account currentAccount;
    private String url;

    AccountManager(String url) {
        accounts = new ArrayList<>();
        currentAccount = null;
        this.url = url;

        try (Connection con = DriverManager.getConnection(url)) {
            Statement statement = con.createStatement();
            String command =
                    "CREATE TABLE IF NOT EXISTS card (\n" +
                            "id INTEGER,\n" +
                            "number TEXT,\n"+
                            "pin TEXT,\n" +
                            "balance INTEGER DEFAULT 0\n" +
                            ");";
            statement.executeUpdate(command);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    Account getCurrentAccount() {
        return currentAccount;
    }

    void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    void add(Account account) {
        accounts.add(account);
        setCurrentAccount(account);

        try (Connection con = DriverManager.getConnection(url)) {
            Statement statement = con.createStatement();
            String command =
                    "INSERT INTO card (number, pin) " +
                    "VALUES ('" + account.getCARD_NUMBER() + "', "
                    + "'" + account.getPIN() + "');";
            statement.executeUpdate(command);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    boolean isLoginSuccessful(String[] loginData) {
        for (Account account: accounts) {
            if (account.getCARD_NUMBER().equals(loginData[0]) && account.getPIN().equals(loginData[1])) {
                currentAccount = account;
                return true;
            }
        }

        return false;
    }
}
