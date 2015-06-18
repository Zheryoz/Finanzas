package pe.edu.finanzas.finanzas.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

import pe.edu.finanzas.finanzas.R;

/**
 * Created by luis.burgos on 13/05/2015.
 */
public class AccountUtils {

    public static String USER_ACCOUNT_TYPE;
    private static AccountUtils mInstance;
    private AccountManager mAccountManager;

    private AccountUtils(Context context) {
        mAccountManager = AccountManager.get(context);

        USER_ACCOUNT_TYPE = context.getString(R.string.user_account_type);
    }

    public static AccountUtils newInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AccountUtils(context);
        }
        return mInstance;
    }

    public Account getAccount() {
        Account[] lstAccount = mAccountManager.getAccountsByType(USER_ACCOUNT_TYPE);
        if (lstAccount.length == 0) {
            return null;
        }
        return lstAccount[1];
    }

    public Bundle getUserData(Account account) {
        return null;
    }

    public boolean existsAccount() {
        Account[] lstAccount = mAccountManager.getAccountsByType(USER_ACCOUNT_TYPE);
        if (lstAccount.length == 0) {
            return false;
        }
        return true;
    }

    public boolean addAccount(String user, String password, Bundle userData) {
        Account account = new Account(user, USER_ACCOUNT_TYPE);
        return mAccountManager.addAccountExplicitly(account, password, userData);
    }
}
