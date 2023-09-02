package ru.vixtor.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.vixtor.moneytransferservice.data.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<Long, Transaction> activeTransactions = new ConcurrentHashMap<>();
    private final Map<Long, Transaction> endedTransactions = new HashMap<>();



    public void put(Transaction transaction) {
        activeTransactions.put(transaction.getId(), transaction);
    }

    public Transaction get(String id){
        return activeTransactions.get(Long.parseLong(id));
    }

    public void makeTransaction(Transaction transaction){
        activeTransactions.remove(transaction.getId());
        endedTransactions.put(transaction.getId(), transaction);
    }


}
