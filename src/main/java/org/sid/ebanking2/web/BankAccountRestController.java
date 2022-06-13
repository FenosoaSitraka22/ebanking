package org.sid.ebanking2.web;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.sid.ebanking2.dtos.*;
import org.sid.ebanking2.exceptions.BalanceNotEnoughException;
import org.sid.ebanking2.exceptions.BankAccountNotFoundException;
import org.sid.ebanking2.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class BankAccountRestController {
    @Autowired
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{idBankAccount}")
    public BankAccountDTOS getBankAccount(@PathVariable String idBankAccount) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(idBankAccount);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTOS> getBankAccounts(){
        return  bankAccountService.bankAccountDTOS();
    }
    @GetMapping("/accounts/{accountId}/history")
    public List<AccountOperationDTO> gethistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO gethistory(@PathVariable String accountId,
                                        @RequestParam(name = "page",defaultValue = "0") int page,
                                        @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccounthistory(accountId,page,size);
    }
    @PostMapping("/account/debitOperations")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
        bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/account/creditOperations")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
       // System.out.println(creditDTO.getAccountId()+"------------------------");
        bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/account/transfertOperations")
    public TransfertDTO transfert(@RequestBody TransfertDTO transfertDTO) throws BalanceNotEnoughException, BankAccountNotFoundException {
        bankAccountService.transfert(transfertDTO.getAccountIdSource(),transfertDTO.getAccountIdDestination(), transfertDTO.getAmount());
        return transfertDTO;
    }
    /*@PostMapping("/bankAccount")
    public BankAccountDTOS saveBankAccount(BankAccountDTOS bankAccountDTOS){
        bankAccountService.sa
    }
*/
}
