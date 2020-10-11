package managers;

import models.Account;
import models.AccountMovement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {

    private Map<Integer, Account> m_IdToAccountMap;

    public AccountManager() {
        m_IdToAccountMap = new HashMap<>();
    }

    public void addAccount(int i_Id, Account i_Account) {
        m_IdToAccountMap.put(i_Id, i_Account);
    }

    public void addAccount(int i_Id) {
        addAccount(i_Id, new Account());
    }

    public Account getAccount(int i_Id) {
        return m_IdToAccountMap.get(i_Id);
    }

    public void loadBalance(int i_Id, LocalDate i_Date, double i_Amount)
    {
        m_IdToAccountMap.get(i_Id).doOperation(AccountMovement.AccountOperationType.LOAD,i_Date,i_Amount);
    }

    public void transferMoney(int i_FromId, LocalDate i_Date, double i_Amount , int i_ToId)
    {
        m_IdToAccountMap.get(i_FromId).doOperation(AccountMovement.AccountOperationType.TRANSFER_PAYMENT,i_Date,i_Amount);
        m_IdToAccountMap.get(i_ToId).doOperation(AccountMovement.AccountOperationType.ACCEPT_PAYMENT,i_Date,i_Amount);
    }

    private boolean isAccountExists(int i_Id) {
        return m_IdToAccountMap.get(i_Id) != null;
    }

}
