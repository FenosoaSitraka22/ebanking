package org.sid.ebanking2.repository;

import org.sid.ebanking2.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long>{
    public List<AccountOperation> findByBankAccountId(String accountId);
    public Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
    public void deleteByBankAccountId(String accountId);
}
