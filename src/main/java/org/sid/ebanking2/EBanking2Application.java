package org.sid.ebanking2;

import org.sid.ebanking2.entities.AccountOperation;
import org.sid.ebanking2.entities.CurrentAccount;
import org.sid.ebanking2.entities.Custumer;
import org.sid.ebanking2.entities.SavingAccount;
import org.sid.ebanking2.enums.AccountStatus;
import org.sid.ebanking2.enums.OperationType;
import org.sid.ebanking2.repository.AccountOperationRepository;
import org.sid.ebanking2.repository.BankAccountRepository;
import org.sid.ebanking2.repository.CustumerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBanking2Application {

	public static void main(String[] args) {
		SpringApplication.run(EBanking2Application.class, args);
	}
	@Bean
	CommandLineRunner start(CustumerRepository custumerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("Sitraka","Dina","Stephanie").forEach(name->{
				Custumer custumer = new Custumer();
				custumer.setName(name);
				custumer.setEmail(name+"@gmail.com");
				custumerRepository.save(custumer);
			});
			custumerRepository.findAll().forEach(custumer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustumer(custumer);
				currentAccount.setOverDraft(Math.random()*60000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustumer(custumer);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(bankAccount -> {
				for (int i=0;i<10;i++){
					AccountOperation accountOperation= new AccountOperation();
					accountOperation.setOperationDate( new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
					accountOperation.setBankAccount(bankAccount);
					accountOperationRepository.save(accountOperation);
					System.out.println(bankAccount.getId()+"++++++++++++++++++++++++++++++");
				}
			});
		};
	}
}
