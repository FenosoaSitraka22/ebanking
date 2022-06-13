package org.sid.ebanking2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Custumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "custumer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //tsy serialiserna/ignorer ilay bankAccounts
    private List<BankAccount> bankAccounts;
    //1:15:00
}
