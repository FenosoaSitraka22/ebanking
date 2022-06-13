package org.sid.ebanking2.mapper;

import org.sid.ebanking2.dtos.AccountOperationDTO;
import org.sid.ebanking2.dtos.CurrentAccountDTO;
import org.sid.ebanking2.dtos.CustumerDTO;
import org.sid.ebanking2.dtos.SavingAccountDTO;
import org.sid.ebanking2.entities.AccountOperation;
import org.sid.ebanking2.entities.CurrentAccount;
import org.sid.ebanking2.entities.Custumer;
import org.sid.ebanking2.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccoutMapperImpl {
    public CustumerDTO fromCustumer(Custumer custumer){
        CustumerDTO custumerDTO = new CustumerDTO();
        BeanUtils.copyProperties(custumer,custumerDTO);
        // manao copy ny valeur attribus custumer --> custumerDTO au lieu de set/get
        return custumerDTO;
    }
    public Custumer fromCustumerDTO(CustumerDTO custumerDTO){
        Custumer custumer = new Custumer();
        BeanUtils.copyProperties(custumerDTO,custumer);
        return custumer;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setCustumer(fromCustumerDTO(currentAccountDTO.getCustumerDTO()));
        return  currentAccount;
    }
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        currentAccountDTO.setCustumerDTO(fromCustumer(currentAccount.getCustumer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return  currentAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO,savingAccount);
        savingAccount.setCustumer(fromCustumerDTO(savingAccountDTO.getCustumerDTO()));
        return  savingAccount;
    }
    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccountDTO.setCustumerDTO(fromCustumer(savingAccount.getCustumer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return  accountOperationDTO;
    }
}
