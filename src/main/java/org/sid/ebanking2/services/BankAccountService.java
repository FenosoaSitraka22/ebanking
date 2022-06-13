package org.sid.ebanking2.services;


import org.sid.ebanking2.dtos.*;
import org.sid.ebanking2.exceptions.BalanceNotEnoughException;
import org.sid.ebanking2.exceptions.BankAccountNotFoundException;
import org.sid.ebanking2.exceptions.CustumerNotFoundException;

import java.util.List;

public interface BankAccountService {
  //  Custumer saveCustmer(Custumer custumer);
  CustumerDTO saveCustumer(CustumerDTO custumerDTO);
  CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long custumerId) throws CustumerNotFoundException;

  SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long custumerId) throws CustumerNotFoundException;
  //List<Custumer> listCustumer();
  CustumerDTO getCustumerDTO(Long id) throws CustumerNotFoundException;
  void deleteCustumer(Long idCustumer);
    List<CustumerDTO> listCustumers();
  BankAccountDTOS getBankAccount(String accountId) throws BankAccountNotFoundException;
  void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotEnoughException;
  void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
  List<BankAccountDTOS> bankAccountDTOS();
    void transfert(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotEnoughException, BankAccountNotFoundException;
  List<AccountOperationDTO> accountHistory(String bankAccountId);

  AccountHistoryDTO getAccounthistory(String accountId, int page, int size) throws BankAccountNotFoundException;
  List<CustumerDTO> fyndCustumer(String keyWord);
  List<BankAccountDTOS> findBankAccountByCustumer(Long idCustumer);
}
