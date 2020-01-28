package com.bressan.relatorio.realatorio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class ReportService {

    private DataReaderService dataReaderService;

    private DataProcessorService dataProcessorService;

    private DataWriterService dataWriterService;

    @Autowired
    public ReportService(DataReaderService dataReaderService, DataProcessorService dataProcessorService, DataWriterService dataWriterService) {
        this.dataReaderService = dataReaderService;
        this.dataProcessorService = dataProcessorService;
        this.dataWriterService = dataWriterService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void generateReport() throws FileNotFoundException {
        dataWriterService.writeFile(dataProcessorService.processEntries(dataReaderService.readFile()));
    }
}
