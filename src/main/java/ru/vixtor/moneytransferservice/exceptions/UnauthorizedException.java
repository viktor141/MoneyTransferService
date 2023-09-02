package ru.vixtor.moneytransferservice.exceptions;

import lombok.Getter;
import ru.vixtor.moneytransferservice.data.Transfer;

public class UnauthorizedException extends RuntimeException{

    @Getter
    private final String id;
    public UnauthorizedException(String msg, String id) {
        super(msg);
        this.id = id;
    }

    public UnauthorizedException(String msg, int operationId) {
        this(msg, String.valueOf(operationId));
    }
}
