package ru.vixtor.moneytransferservice.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import ru.vixtor.moneytransferservice.data.ConfirmOperation;
import ru.vixtor.moneytransferservice.data.ErrorResponse;
import ru.vixtor.moneytransferservice.data.Response;
import ru.vixtor.moneytransferservice.data.Transfer;
import ru.vixtor.moneytransferservice.exceptions.UnauthorizedException;
import ru.vixtor.moneytransferservice.service.LogService;
import ru.vixtor.moneytransferservice.service.TransferService;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class TransferController {

    private final TransferService service;
    private final LogService logService;

    public TransferController(TransferService service, LogService logService) {
        this.service = service;
        this.logService = logService;
    }


    @PostMapping("/transfer")
    public Response transfer(@RequestBody Transfer transfer) {
        return service.transfer(transfer);
    }

    @PostMapping("/confirmOperation")
    public Response confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getId());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInternalServerError(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
