package models;


import java.time.LocalDate;

public class AccountMovement {

    public enum AccountOperationType {

        LOAD("load") {
            @Override
            public Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount) {
                return i_CurrentAmount + i_AcceptedAmount;
            }
        }, ACCEPT_PAYMENT("accept payment") {
            @Override
            public Double doOperation(Double i_CurrentAmount, Double i_AcceptedAmount) {
                return i_CurrentAmount + i_AcceptedAmount;
            }
        }, TRANSFER_PAYMENT("transfer payment") {
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
    private final double m_Amount;
    private final double m_BalanceBefore;
    private final double m_BalanceAfter;

    public AccountMovement(AccountOperationType i_AccountOperationType, LocalDate i_Date, double i_Amount, double i_BalanceBefore, double i_BalanceAfter) {
        this.m_AccountOperationType = i_AccountOperationType;
        this.m_Date = i_Date;
        this.m_Amount = i_Amount;
        this.m_BalanceBefore = i_BalanceBefore;
        this.m_BalanceAfter = i_BalanceAfter;
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
