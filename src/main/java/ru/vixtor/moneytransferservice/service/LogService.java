package ru.vixtor.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.vixtor.moneytransferservice.data.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    private List<String> logs = new ArrayList<>();

    public void loggingTransaction(Transaction transaction) {
        String resultLogMessage = transaction.toString();

        logs.add(resultLogMessage);
        loggingIntoLogFile(resultLogMessage);
    }
    public void loggingError(String msg){
        logs.add(msg);
        loggingIntoLogFile(msg);
    }
    private void loggingIntoLogFile(String message) {
        Path path = Paths.get("log.log");

        try {

            File log = new File(path.toString());

            if (!log.exists())log.createNewFile();


            FileWriter fw = new FileWriter(log, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(message);
            bw.newLine();

            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
