package com.ardwiinoo.csvappfuture.services.impl;

import com.ardwiinoo.csvappfuture.mappers.CountryMapper;
import com.ardwiinoo.csvappfuture.models.dto.CountryDto;
import com.ardwiinoo.csvappfuture.models.dto.request.CountryRequest;
import com.ardwiinoo.csvappfuture.models.entity.Country;
import com.ardwiinoo.csvappfuture.repositories.CountryRepository;
import com.ardwiinoo.csvappfuture.services.CountryService;
import com.ardwiinoo.csvappfuture.utils.FileUtil;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Override
    public CompletableFuture<List<CountryDto>> upload(CountryRequest request) {
        FileUtil.hasCSVFormat(request.getCsvFile());

        // 1. Read CSV Async
        CompletableFuture<List<Country>> readCsvFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("READING CSV...");

            List<Country> countries = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new InputStreamReader(request.getCsvFile().getInputStream()))) {
                List<String[]> records = reader.readAll();

                for (String[] record : records) {
                    Country country = Country.builder()
                            .code(record[0])
                            .countryName(record[1])
                            .region(record[2])
                            .build();
                    countries.add(country);
                }
            } catch (Exception e) {
                throw new RuntimeException("CSV Error: " + e.getMessage(), e);
            }
            return countries;
        }, executor);

        // 2. Save to DB Async with Batch
        return readCsvFuture.thenCompose(countries -> {
            if (countries.isEmpty()) {
                throw new RuntimeException("CSV Kosong!");
            }

            System.out.println("INSERTING TO DB...");

            List<CompletableFuture<Void>> futures = new ArrayList<>();
            int batchSize = Integer.parseInt(System.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size", "1000"));

            for (int i = 0; i < countries.size(); i += batchSize) {
                final int index = i;
                List<Country> batch = countries.subList(index, Math.min(index + batchSize, countries.size()));
                futures.add(CompletableFuture.runAsync(() -> {
                    countryRepository.saveAll(batch);
                    System.out.println("Batch " + (index / batchSize + 1) + " inserted");
                }, executor));
            }

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> countries.stream()
                            .map(CountryMapper.INSTANCE::toDto)
                            .collect(Collectors.toList()));
        }).exceptionally(ex -> {
            throw new RuntimeException("Gagal: " + ex.getMessage(), ex);
        });
    }
}

