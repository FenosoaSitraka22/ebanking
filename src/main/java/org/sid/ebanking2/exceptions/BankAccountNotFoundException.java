package org.sid.ebanking2.exceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String bank_unfoundable) {
        super(bank_unfoundable);
    }
}
