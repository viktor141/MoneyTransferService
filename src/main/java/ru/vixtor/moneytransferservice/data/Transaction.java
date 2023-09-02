package ru.vixtor.moneytransferservice.data;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@NonNull
public class Transaction {
    private enum TransactionStatus{
        WAITING,
        SUCCESS,
        FAILED;
    }

    private final long id;
    private final LocalDateTime timeStamp;
    private final String from;
    private final String to;
    private final Amount amount;
    private final float fee;
    private TransactionStatus status;

    public Transaction(long id, String from, String to, Amount amount) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.from = from;
        this.to = to;
        this.amount = amount;
        fee = (float) (amount.getValue() * 0.01);
        status = TransactionStatus.WAITING;
    }

    public void transactionSucceed(){
        status = TransactionStatus.SUCCESS;
    }
    public void transactionFailed(){
        status = TransactionStatus.FAILED;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", date=" + timeStamp.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) +
                ", fromCardNumber=" + from +
                ", toCardNumber=" + to +
                ", amount=" + amount +
                ", fee=" + fee +
                ", status=" + status;
    }
}
