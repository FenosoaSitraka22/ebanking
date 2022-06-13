package org.sid.ebanking2.repository;

import org.sid.ebanking2.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, String> {
}
