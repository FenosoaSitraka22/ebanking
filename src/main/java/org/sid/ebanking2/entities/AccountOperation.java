package org.sid.ebanking2.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking2.enums.OperationType;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)// EnumeType.String si valeur ecrite dans l'Enum/ EnumeType.Ordinal raha 0/1/2
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
    private String description;
   // 50:00 from the end
}
