package dtoModel;

import models.AccountMovement;

public class AccountMovementDto {

    private final String m_MovementType;
    private final String m_Date;
    private final double m_Amount;
    private final double m_BalanceBefore;
    private final double m_BalanceAfter;

    public AccountMovementDto(String i_AccountOperationType, String i_Date, double i_Amount, double i_BalanceBefore, double i_BalanceAfter) {

        this.m_MovementType = i_AccountOperationType;
        this.m_Date = i_Date;
        this.m_Amount = i_Amount;
        this.m_BalanceBefore = i_BalanceBefore;
        this.m_BalanceAfter = i_BalanceAfter;
    }

    public AccountMovementDto(AccountMovement i_AccountMovement) {
        this(i_AccountMovement.getAccountOperationType().getValue(),
                i_AccountMovement.getDate().toString(),
                i_AccountMovement.getAmount(),
                i_AccountMovement.getBalanceBefore(),
                i_AccountMovement.getBalanceAfter());
    }

    public String getMovementType() {
        return m_MovementType;
    }

    public String getDate() {
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
