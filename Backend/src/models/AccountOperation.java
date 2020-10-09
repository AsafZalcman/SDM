package models;


import java.time.LocalDate;

public class AccountOperation {

    public enum AccountOperationType {

        LOAD("Load") {
            @Override
            public Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount) {
                return i_CurrentAmount + i_AcceptedAmount;
            }
        }, ACCEPT_PAYMENT("Accept payment") {
            @Override
            public Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount) {
                return i_CurrentAmount + i_AcceptedAmount;
            }
        }, TRANSFER_PAYMENT("Transfer payment") {
            @Override
            public Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount) {
                return i_CurrentAmount - i_AcceptedAmount;

            }
        };
        private final String m_Value;

        AccountOperationType(String i_Value) {
            m_Value = i_Value;
        }

        public String getValue() {
            return m_Value;
        }

        public abstract Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount);

        @Override
        public String toString() {
            return getValue();
        }
    }

    private final AccountOperationType m_AccountOperationType;
    private final LocalDate m_Date;
    private final Double m_Amount;
    private final Double m_BalanceBefore;
    private final Double m_BalanceAfter;

    public AccountOperation(AccountOperationType m_AccountOperationType, LocalDate m_Date, Double m_Amount, Double m_BalanceBefore, Double m_BalanceAfter) {
        this.m_AccountOperationType = m_AccountOperationType;
        this.m_Date = m_Date;
        this.m_Amount = m_Amount;
        this.m_BalanceBefore = m_BalanceBefore;
        this.m_BalanceAfter = m_BalanceAfter;
    }

    public AccountOperationType getAccountOperationType() {
        return m_AccountOperationType;
    }

    public LocalDate getDate() {
        return m_Date;
    }

    public Double getAmount() {
        return m_Amount;
    }

    public Double getBalanceBefore() {
        return m_BalanceBefore;
    }

    public Double getBalanceAfter() {
        return m_BalanceAfter;
    }
}
