package com.bressan.relatorio.realatorio.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Report {

    private Long totalClient;
    private Long totalSalesman;
    private Long mostExpensiveSaleId;
    private String worstSalesman;

}
