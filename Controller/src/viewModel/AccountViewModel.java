package viewModel;

import dtoModel.AccountDto;
import dtoModel.AccountMovementDto;
import managers.SuperDuperManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountViewModel {

    private SuperDuperManager m_SuperDuperManager = SuperDuperManager.getInstance();

    public AccountDto getAccount(int i_Id) {
        return new AccountDto(m_SuperDuperManager.getAccountManager().getAccount(i_Id));
    }

    public void loadBalance(int i_Id, LocalDate i_Date, double i_Amount) {

        m_SuperDuperManager.getAccountManager().loadBalance(i_Id, i_Date, i_Amount);
    }

    public void transferMoney(int i_FromId, LocalDate i_Date, double i_Amount, int i_ToId) {
        m_SuperDuperManager.getAccountManager().transferMoney(i_FromId, i_Date, i_Amount, i_ToId);
    }

    public List<AccountMovementDto> getAccountOperations(int i_Id, int i_FromIndex)
    {
        AccountDto accountDto = getAccount(i_Id);
        List<AccountMovementDto> accountMovmentDtoList = new ArrayList<>(accountDto.getAccountMovements());
        if (i_FromIndex < 0 || i_FromIndex > accountMovmentDtoList.size()) {
            i_FromIndex = 0;
        }
        return accountMovmentDtoList.subList(i_FromIndex, accountMovmentDtoList.size());
    }

    public int getVersion(int i_UserId) {
        return getAccount(i_UserId).getAccountMovements().size();
    }
}
