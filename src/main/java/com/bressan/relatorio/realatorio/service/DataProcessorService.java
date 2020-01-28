package com.bressan.relatorio.realatorio.service;

import com.bressan.relatorio.realatorio.domain.TotalPerSalesman;
import com.bressan.relatorio.realatorio.domain.Report;
import com.bressan.relatorio.realatorio.domain.Sale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataProcessorService {

    public Report processEntries(List<String> entries) {
        Long totalClients = getTotalClients(entries);
        Long totalSalesman = getTotalSalesman(entries);
        List<List<Sale>> saleList = new ArrayList<>();
        entries.stream().filter(this::isSale)
                .forEach(input -> saleList.add(getSales(input)));

        return Report.builder()
                .totalClient(totalClients)
                .totalSalesman(totalSalesman)
                .mostExpensiveSaleId(getIdMostExpensiveSale(saleList))
                .worstSalesman(getWorstSalesman(saleList))
                .build();

    }

    private Boolean isClient(String input) {
        return "002".equals(input.substring(0,3));
    }

    private Boolean isSalesman(String input) {
        return "001".equals(input.substring(0,3));
    }

    private Boolean isSale(String input) {
        return "003".equals(input.substring(0,3));
    }

    private Long getTotalClients(List<String> entries) {
        return entries.stream()
                .filter(this::isClient)
                .count();
    }

    private Long getTotalSalesman(List<String> entries) {
        return entries.stream()
                .filter(this::isSalesman)
                .count();
    }

    private List<Sale> getSales(String input) {
        List<String> values = null;
        List<Sale> sales = new ArrayList<>();
        String saleId = input.substring(input.indexOf("ç")+1, input.indexOf("ç["));
        String salesman = input.substring(input.lastIndexOf("ç")+1);
        String salesData = input.substring(input.indexOf("[")+1, input.indexOf("]"));
        values = Arrays.asList(salesData.split(","));

        for (String value : values) {
            List<String> salesValues = Arrays.asList(value.split("-"));
            Sale sale = Sale.builder()
                    .id(Long.valueOf(saleId))
                    .idItem(Long.valueOf(salesValues.get(0).trim()))
                    .quantity(Integer.valueOf(salesValues.get(1).trim()))
                    .price(new BigDecimal(salesValues.get(2).trim()))
                    .salesMan(salesman)
                    .build();
            sales.add(sale);
        }
        return sales;
    }

    private Long getIdMostExpensiveSale(List<List<Sale>> sales) {
        BigDecimal bigger = BigDecimal.ZERO;
        Long id = null;
        List<Sale> saleList = sales.stream().flatMap(Collection::stream).collect(Collectors.toList());
        for (Sale sale : saleList) {
            if (sale.getPrice().compareTo(bigger) == 1) {
                bigger = sale.getPrice();
                id = sale.getId();
            }
        }
        return id;
    }

    private String getWorstSalesman(List<List<Sale>> saleList) {
        Set<TotalPerSalesman> totalPerSalesmenSet = new HashSet<>();
        List<Sale> sales = saleList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        sales.forEach(sale -> totalPerSalesmenSet.add(TotalPerSalesman.builder().salesman(sale.getSalesMan()).build()));
        totalPerSalesmenSet.stream()
                .forEach(t -> t.setTotal(sales.stream().filter(sale -> sale.getSalesMan().equals(t.getSalesman())).map(s -> s.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        List<TotalPerSalesman> totalPerSalesmanList = totalPerSalesmenSet.stream().collect(Collectors.toList());
        BigDecimal smaller = totalPerSalesmanList.get(0).getTotal();
        String worstSalesman = totalPerSalesmanList.get(0).getSalesman();
        for (TotalPerSalesman total : totalPerSalesmenSet) {
            if (total.getTotal().compareTo(smaller) == -1) {
                smaller = total.getTotal();
                worstSalesman = total.getSalesman();
            }
        }

        return worstSalesman;
    }





}
