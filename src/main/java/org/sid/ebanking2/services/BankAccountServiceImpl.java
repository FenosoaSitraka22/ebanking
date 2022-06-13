package org.sid.ebanking2.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking2.dtos.*;
import org.sid.ebanking2.entities.*;
import org.sid.ebanking2.enums.OperationType;
import org.sid.ebanking2.exceptions.BalanceNotEnoughException;
import org.sid.ebanking2.exceptions.BankAccountNotFoundException;
import org.sid.ebanking2.exceptions.CustumerNotFoundException;
import org.sid.ebanking2.mapper.BankAccoutMapperImpl;
import org.sid.ebanking2.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j //Mog4j gerer la journalisation
public class BankAccountServiceImpl implements BankAccountService{
    private BankAccountRepository bankAccountRepository;
    private CustumerRepository custumerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccoutMapperImpl dtoMapper;
    private SavingAccountRepository savingAccountRepository;
    private CurrentAccountRepository currentAccountRepository;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName()); msg mipoitra eo am terminal  nosoloina @Slf4j
   /* @Override
    public Custumer saveCustmer(Custumer custumer) {
        log.info("saving new Custumer"); //miseho rehefa executena ny methode saceCustumer
     //   Custumer savedcustumer = custumerRepository.save(custumer);
        return custumerRepository.save(custumer);
    }*/
//51:330
    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long custumerId) throws CustumerNotFoundException {
        Custumer custumer = custumerRepository.findById(custumerId).orElse(null);
        if(custumer==null){
            throw  new CustumerNotFoundException("Custumer unfoundable");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustumer(custumer);
        bankAccountRepository.save(currentAccount);
        return  dtoMapper.fromCurrentAccount(currentAccount);
    }


    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long custumerId) throws CustumerNotFoundException {
        Custumer custumer = custumerRepository.findById(custumerId).orElse(null);
        if (custumer==null){
            throw new CustumerNotFoundException("Custumer unfoundable");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustumer(custumer);
        bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingAccount(savingAccount);
    }

    @Override
    public CustumerDTO getCustumerDTO(Long idCustumer) throws CustumerNotFoundException {
        return dtoMapper.fromCustumer(custumerRepository.findById(idCustumer)
                .orElseThrow(()-> new  CustumerNotFoundException("Custumer Not Found")));
    }

    @Override
    public void deleteCustumer(Long idCustumer) {
        List<BankAccount> accounts= bankAccountRepository.findByCustumerId(idCustumer);
        accounts.forEach(a->{
            accountOperationRepository.deleteByBankAccountId(a.getId());
            System.out.println("++++++++++"+a.getId()+"------------------");
            if (a instanceof CurrentAccount)currentAccountRepository.deleteById(a.getId());
            else savingAccountRepository.deleteById(a.getId());
        });
        custumerRepository.deleteById(idCustumer);
    }


    @Override
    public List<CustumerDTO> listCustumers() {
        log.info("loading custumer");
        List<Custumer> custumers =custumerRepository.findAll();
        List<CustumerDTO> custumerDTOS= custumers.stream()
                .map(custumer -> dtoMapper.fromCustumer(custumer))
                .collect(Collectors.toList());// programmation fonctionnelle
        /*List<CustumerDTO> custumerDTOS = new ArrayList<>();
        custumers.forEach(custumer -> {
            custumerDTOS.add(dtoMapper.fromCustumer(custumer));
        });*/ //Programation imperative classique
        return custumerDTOS;
    }

    @Override
    public BankAccountDTOS getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank unfoundable"));
        if(bankAccount instanceof SavingAccount){
            return dtoMapper.fromSavingAccount((SavingAccount) bankAccount);
        }else{
            return dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotEnoughException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank unfoundable"));
        if (bankAccount.getBalance()<amount){
            throw new BalanceNotEnoughException("balance not enough");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank unfoundable"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        //System.out.println("------------********"+accountOperation.getType());
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotEnoughException, BankAccountNotFoundException {
        debit(accountIdSource,amount,"transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from "+accountIdSource);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String bankAccountId) {
       return accountOperationRepository.findByBankAccountId(bankAccountId).stream()
                .map(accountOperation -> dtoMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccounthistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount =  bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new BankAccountNotFoundException("bank unfoundable");
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent()
                .stream().map(accountOperation -> dtoMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setTotalPage(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustumerDTO> fyndCustumer(String keyWord) {
        return custumerRepository.findByNameContains(keyWord).stream()
                .map(custumer -> dtoMapper.fromCustumer(custumer)).collect(Collectors.toList());
    }

    @Override
    public List<BankAccountDTOS> findBankAccountByCustumer(Long idCustumer) {
        return null;
    }

    @Override
    public CustumerDTO saveCustumer(CustumerDTO custumerDTO){
        log.info("saving new custumer");
        Custumer savedCustumer = custumerRepository.save(dtoMapper.fromCustumerDTO(custumerDTO));
        return dtoMapper.fromCustumer(savedCustumer);

    }
   public List<BankAccountDTOS> bankAccountDTOS() {
       return bankAccountRepository.findAll().stream()
               .map(bankAccount -> {
                   if (bankAccount instanceof SavingAccount) {
                       return dtoMapper.fromSavingAccount((SavingAccount) bankAccount);
                   } else {
                       return dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
                   }
               }).collect(Collectors.toList());
    }
}
