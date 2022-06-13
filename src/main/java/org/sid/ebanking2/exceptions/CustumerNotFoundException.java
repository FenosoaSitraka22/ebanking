package org.sid.ebanking2.exceptions;

public class CustumerNotFoundException extends Exception {
    public CustumerNotFoundException(String custumer_unfoundable) {
        super(custumer_unfoundable);
    }
}
