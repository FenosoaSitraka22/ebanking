package org.sid.ebanking2.dtos;

import lombok.Data;

@Data
public class TransfertDTO {
    private String accountIdSource;
    private String accountIdDestination;
    private double amount;
    private String description;
}
