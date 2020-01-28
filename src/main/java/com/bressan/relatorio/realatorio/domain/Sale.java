package com.bressan.relatorio.realatorio.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Sale {
    private Long id;
    private Long idItem;
    private Integer quantity;
    private BigDecimal price;
    private String salesMan;

}
