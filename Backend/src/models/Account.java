package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private double m_Balance;
    private final List<AccountMovement> m_AccountMovementList;

    public Account() {
        this(0.0);
    }

    public Account(double i_Balance) {
        m_Balance = i_Balance;
        m_AccountMovementList = new ArrayList<>();
    }

   synchronized public void doOperation(AccountMovement.AccountOperationType i_AccountOperationType, LocalDate i_Date, Double i_Amount) {
        Double newBalance = i_AccountOperationType.doOperation(m_Balance, i_Amount);
        m_AccountMovementList.add(new AccountMovement(i_AccountOperationType, i_Date, i_Amount, m_Balance, newBalance));
        m_Balance = newBalance;
    }

    public double getBalance()
    {
        return m_Balance;
    }

    public  List<AccountMovement> getAccountOperationsHistory()
    {
        return  Collections.unmodifiableList(m_AccountMovementList);
    }
}
