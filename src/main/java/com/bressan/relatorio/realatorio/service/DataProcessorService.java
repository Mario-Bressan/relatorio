package com.bressan.relatorio.realatorio.service;

import com.bressan.relatorio.realatorio.domain.Report;
import com.bressan.relatorio.realatorio.domain.Sale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class DataProcessorService {

    private final ClientService clientService;

    private final SalesService salesService;

    private final SalesmanService salesmanService;

    @Autowired
    public DataProcessorService(ClientService clientService, SalesService salesService, SalesmanService salesmanService) {
        this.clientService = clientService;
        this.salesService = salesService;
        this.salesmanService = salesmanService;
    }

    public Report processEntries(List<String> entries) {
        Long totalClients = clientService.getTotalClients(entries);
        Long totalSalesman = salesmanService.getTotalSalesman(entries);
        List<List<Sale>> saleList = new ArrayList<>();
        entries.stream().filter(salesService::isSale)
                .forEach(input -> saleList.add(salesService.getSales(input)));

        return Report.builder()
                .totalClient(totalClients)
                .totalSalesman(totalSalesman)
                .mostExpensiveSaleId(salesService.getIdMostExpensiveSale(saleList))
                .worstSalesman(salesmanService.getWorstSalesman(saleList))
                .build();
    }







}
