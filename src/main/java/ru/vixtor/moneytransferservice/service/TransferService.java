package ru.vixtor.moneytransferservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vixtor.moneytransferservice.data.ConfirmOperation;
import ru.vixtor.moneytransferservice.data.Response;
import ru.vixtor.moneytransferservice.data.Transaction;
import ru.vixtor.moneytransferservice.data.Transfer;
import ru.vixtor.moneytransferservice.exceptions.UnauthorizedException;
import ru.vixtor.moneytransferservice.repository.TransactionRepository;

@Service
public class TransferService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private AuthBillingService billingService;
    private static int id = 0;

    public Response transfer(Transfer transfer) {
        AuthBillingService.Status status = billingService.authorizeBilling(transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getAmount(), transfer.getCardFromCVV(), transfer.getCardFromValidTill());
        int operationId = id;

        switch (status) {
            case AUTHORISED:
                Transaction transaction = new Transaction(operationId, transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getAmount());
                transactionRepository.put(transaction);
                logService.loggingTransaction(transaction);
                return new Response(String.valueOf(transaction.getId()), status.name());
            case UNAUTHORISED_SENDER:
                throw new UnauthorizedException("Sender card are not vaild", operationId);
            case UNAUTHORIZED_RECEIVER:
                throw new UnauthorizedException("Receiver card are not vaild", operationId);
            case OUT_DATE:
                throw new UnauthorizedException("Sender card are out of date", operationId);
            case INSUFFICIENT:
                throw new UnauthorizedException("Insufficient money", operationId);
            case ERROR:
                throw new UnauthorizedException("Billing service are not available ", operationId);
            default:
                logService.loggingError("Unknown error");
                throw new RuntimeException("Unknown error");
        }
    }

    public Response confirmOperation(ConfirmOperation confirmOperation) {
        Transaction transaction = transactionRepository.get(confirmOperation.getOperationId());
        AuthBillingService.Status status = billingService.verifyCode(transaction, confirmOperation.getCode());

        switch (status) {
            case AUTHORISED:
                transaction.transactionSucceed();
                break;
            case CODE_NOT_VALID:
                transaction.transactionFailed();
                break;
            case ERROR:
                throw new UnauthorizedException("Transaction was not exist", confirmOperation.getOperationId());
        }

        transactionRepository.makeTransaction(transaction);
        logService.loggingTransaction(transaction);
        return new Response(String.valueOf(transaction.getId()), status.name());
    }
}
