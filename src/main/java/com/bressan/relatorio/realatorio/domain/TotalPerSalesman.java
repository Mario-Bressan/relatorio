package com.bressan.relatorio.realatorio.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TotalPerSalesman {
    private String salesman;
    private BigDecimal total;

}
