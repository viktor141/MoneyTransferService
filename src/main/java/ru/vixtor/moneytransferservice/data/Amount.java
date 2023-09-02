package ru.vixtor.moneytransferservice.data;

import lombok.Data;

@Data
public class Amount {
    int value;
    String currency;
}
