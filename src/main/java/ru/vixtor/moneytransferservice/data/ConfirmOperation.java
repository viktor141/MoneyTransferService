package ru.vixtor.moneytransferservice.data;

import lombok.Data;

@Data
public class ConfirmOperation {
    String operationId;
    String code;
}
