package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private double m_Balance;
    private List<AccountOperation> m_AccountOperationList;

    public Account() {
        this(0.0);
    }

    public Account(double i_Balance) {
        m_Balance = i_Balance;
        m_AccountOperationList = new ArrayList<>();
    }

   synchronized public void doOperation(AccountOperation.AccountOperationType i_AccountOperationType, LocalDate i_Date, Double i_Amount) {
        Double newBalance = i_AccountOperationType.doOperation(m_Balance, i_Amount);
        m_AccountOperationList.add(new AccountOperation(i_AccountOperationType, i_Date, i_Amount, m_Balance, newBalance));
        m_Balance = newBalance;
    }
}
