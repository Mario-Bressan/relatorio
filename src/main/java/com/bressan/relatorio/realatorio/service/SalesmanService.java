package com.bressan.relatorio.realatorio.service;


import com.bressan.relatorio.realatorio.domain.Sale;
import com.bressan.relatorio.realatorio.domain.TotalPerSalesman;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SalesmanService {

    private Boolean isSalesman(String input) {
        return "001".equals(input.substring(0,3));
    }

    public Long getTotalSalesman(List<String> entries) {
        return entries.stream()
                .filter(this::isSalesman)
                .count();
    }

    public String getWorstSalesman(List<List<Sale>> saleList) {
        Set<TotalPerSalesman> totalPerSalesmenSet = new HashSet<>();
        List<Sale> sales = saleList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        sales.forEach(sale -> totalPerSalesmenSet.add(TotalPerSalesman.builder().salesman(sale.getSalesMan()).build()));
        totalPerSalesmenSet.stream()
                .forEach(t -> t.setTotal(sales.stream()
                        .filter(sale -> sale.getSalesMan().equals(t.getSalesman()))
                        .map(s -> s.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
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
