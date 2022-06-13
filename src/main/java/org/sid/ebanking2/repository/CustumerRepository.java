package org.sid.ebanking2.repository;

import org.sid.ebanking2.entities.Custumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustumerRepository extends JpaRepository<Custumer,Long>{
    List<Custumer> findByNameContains(String keyWord);

}
