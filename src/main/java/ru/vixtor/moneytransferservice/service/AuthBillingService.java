package ru.vixtor.moneytransferservice.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vixtor.moneytransferservice.data.Amount;
import ru.vixtor.moneytransferservice.data.Transaction;
import ru.vixtor.moneytransferservice.data.Transfer;

import java.time.LocalDate;

//Класс заглушка
@Service
public class AuthBillingService {
    public enum Status {
        AUTHORISED,
        UNAUTHORISED_SENDER,
        UNAUTHORIZED_RECEIVER,
        OUT_DATE,
        INSUFFICIENT,
        CODE_NOT_VALID,
        ERROR;
    }

    public Status authorizeBilling(String cardSenderNumber, String cardReceiverNumber, Amount amount, String cvv, String expire){
        Status status = cardSenderAuth(cardSenderNumber, amount, cvv, new DateOfExpire(expire));

        if(status.equals(Status.AUTHORISED)){
            return cardReceiverAuth(cardReceiverNumber);
        }

        return status;
    }

    //Заглушка на проверку карты отправителя
    private Status cardSenderAuth(String cardSenderNumber, Amount amount, String cvv, DateOfExpire expire){

        //Предстааавим что номер карты и cvv код верные
        Status status = Status.AUTHORISED;

        LocalDate localDate = LocalDate.now();

        if(localDate.getYear() % 100 > expire.getYear() ||
                (localDate.getYear() % 100 == expire.getYear()) && (localDate.getMonth().getValue() > expire.getMonth())){
            status = Status.OUT_DATE;
            return status;
        }


        if(amount.getValue()/100 > 540_345){
            status = Status.INSUFFICIENT;
            return status;
        }

        return status;
    }

    //Заглушка на проверку существования карты
    private Status cardReceiverAuth(String cardReceiverNumber){
        return Status.AUTHORISED;
    }

    public Status verifyCode(Transaction transaction, String code){
        return Status.AUTHORISED;
    }

    @Getter
    private class DateOfExpire{

        private final byte month;
        private final int year;

        private DateOfExpire(String string){
            month = Byte.parseByte(string.substring(0, 2));
            year = Integer.parseInt(string.substring(3, 5));
        }
    }

}
