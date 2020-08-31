package com.chaka.test.controller;

import com.chaka.test.model.Transaction;
import com.chaka.test.model.TransactionStatisticResponse;
import com.chaka.test.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



import static com.chaka.test.service.TransactionService.DATESTATUS;

@Slf4j
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<Object> addTransaction(@RequestBody @Valid Transaction transaction) {
        DATESTATUS datestatus = transactionService.addTransaction(transaction);
        if(datestatus == DATESTATUS.OLDER_THAN_MINUTE){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else if(datestatus == DATESTATUS.FUTURE){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }else{
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @ResponseStatus(HttpStatus.OK )
    @GetMapping("/statistics")
    public TransactionStatisticResponse getTransactionStatistics() {
        return transactionService.getTransactionStatistic();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT )
    @DeleteMapping(value = "/transactions")
    public void deleteTransaction(){
        transactionService.deleteTransaction();
    }


}
