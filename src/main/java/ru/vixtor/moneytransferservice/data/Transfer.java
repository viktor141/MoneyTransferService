package ru.vixtor.moneytransferservice.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Transfer {

    String cardFromNumber;
    String cardToNumber;
    String cardFromCVV;
    String cardFromValidTill;
    Amount amount;

}
