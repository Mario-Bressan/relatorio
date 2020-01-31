package com.bressan.relatorio.realatorio.service;

import com.bressan.relatorio.realatorio.domain.Sale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesService {

    public Boolean isSale(String input) {
        return "003".equals(input.substring(0,3));
    }

    public List<Sale> getSales(String input) {
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

    public Long getIdMostExpensiveSale(List<List<Sale>> sales) {
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
}
