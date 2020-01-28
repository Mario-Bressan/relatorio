package com.bressan.relatorio.realatorio.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class DataReaderService {

    @Autowired
    DataProcessorService dataProcessorService;


    public List<String> readFile() throws FileNotFoundException {
        String homePath = System.getenv("HOMEPATH");
        File file = new File("C:\\"+homePath+"\\data\\in\\dados.dat");
        Scanner scanner = new Scanner(file);
        List<String> entries = new ArrayList<>();
        while (scanner.hasNextLine()) {
            entries.add(scanner.nextLine());
        }
        return entries;
    }
}
