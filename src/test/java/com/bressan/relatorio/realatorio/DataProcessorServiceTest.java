package com.bressan.relatorio.realatorio;


import com.bressan.relatorio.realatorio.domain.Report;
import com.bressan.relatorio.realatorio.service.DataProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class DataProcessorServiceTest {

    @InjectMocks
    private DataProcessorService service;

    @Test
    public void whenCallProcessEntries_thenReturnReport() {
        List<String> entries = Arrays.asList("001ç1234567891234çPedroç50000",
                "001ç3245678865434çPauloç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
        Report report = service.processEntries(entries);
        assertEquals(Long.valueOf(2), report.getTotalClient());
        assertEquals(Long.valueOf(2), report.getTotalSalesman());
        assertEquals(Long.valueOf(10), report.getMostExpensiveSaleId());
        assertEquals("Paulo", report.getWorstSalesman());
    }
}
