package com.bressan.relatorio.realatorio.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {

    private Boolean isClient(String input) {
        return "002".equals(input.substring(0,3));
    }

    public Long getTotalClients(List<String> entries) {
        return entries.stream()
                .filter(this::isClient)
                .count();
    }
}
