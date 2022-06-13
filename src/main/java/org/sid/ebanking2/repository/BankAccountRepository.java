package org.sid.ebanking2.repository;


import org.sid.ebanking2.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustumerId(Long idCustumer);
}
