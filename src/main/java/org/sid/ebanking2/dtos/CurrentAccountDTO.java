package org.sid.ebanking2.dtos;

import lombok.Data;
import org.sid.ebanking2.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentAccountDTO extends BankAccountDTOS {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustumerDTO custumerDTO;
    public  double overDraft;
}
