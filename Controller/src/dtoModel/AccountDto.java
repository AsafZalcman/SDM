package dtoModel;

import models.Account;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {

    private final double m_Balance;
    private final List<AccountMovementDto> m_AccountMovementsList;

    public AccountDto(Account i_Account)
    {
        m_Balance=i_Account.getBalance();
        m_AccountMovementsList = i_Account.getAccountOperationsHistory().stream().map(AccountMovementDto::new).collect(Collectors.toList());;
    }

    public AccountDto (double i_Balance,List<AccountMovementDto> i_AccountMovmentDtos)
    {
        m_Balance=i_Balance;
        m_AccountMovementsList = i_AccountMovmentDtos;
    }

    public double getBalance()
    {
        return m_Balance;
    }

    public Collection<AccountMovementDto> getAccountMovements()
    {
        return m_AccountMovementsList;
    }
}
