package banking;

import java.util.ArrayList;

class AccountManager {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account currentAccount = null;

    Account getCurrentAccount() {
        return currentAccount;
    }

    void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    void add(Account account) {
        accounts.add(account);
        setCurrentAccount(account);
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
