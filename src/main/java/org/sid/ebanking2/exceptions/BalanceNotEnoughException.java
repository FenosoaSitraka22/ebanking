package org.sid.ebanking2.exceptions;

public class BalanceNotEnoughException extends Exception {
    public BalanceNotEnoughException(String balance_not_enough) {
        super(balance_not_enough);
    }
}
