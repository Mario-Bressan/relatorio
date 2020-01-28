package com.bressan.relatorio.realatorio.service;

import com.bressan.relatorio.realatorio.domain.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class DataWriterService {

    public void writeFile(Report report) {
        try {
            FileWriter fileWriter = new FileWriter("C:"+System.getenv("HOMEPATH")+"\\data\\out\\report" + System.currentTimeMillis()+".done.dat");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Total clients: " + report.getTotalClient());
            printWriter.println("Total salesman " + report.getTotalSalesman());
            printWriter.println("Most expansive sale id: " + report.getMostExpensiveSaleId());
            printWriter.println("Worst salesman: " + report.getWorstSalesman());
            printWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
