package org.sid.ebanking2.dtos;

import lombok.Data;
import org.sid.ebanking2.enums.OperationType;

import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
   // 50:00 from the end
}
