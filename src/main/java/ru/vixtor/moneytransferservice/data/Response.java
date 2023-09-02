package ru.vixtor.moneytransferservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    String operationId;
    String code;
}
