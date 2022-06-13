package org.sid.ebanking2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking2.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // heritage objet single_tabel ou Table_per_colum ou Joined Table
//@DiscriminatorColumn(name = "type",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING) //ordinal : 0/1/2    string
    private AccountStatus status;
    @ManyToOne
    private Custumer custumer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.EAGER)//FetchType.LAZY ne charge pas les lists des accountOperation / eager charge tout en memoire
    private List<AccountOperation> accountOperations;

}
