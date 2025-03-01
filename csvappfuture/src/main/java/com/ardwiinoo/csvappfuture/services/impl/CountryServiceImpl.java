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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

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
        });

        // 2. Save ke DB Async dengan Batch
        return readCsvFuture.thenCompose(countries -> {
            if (countries.isEmpty()) {
                throw new RuntimeException("CSV Kosong!");
            }
            System.out.println("INSERTING TO DB...");

            return CompletableFuture.supplyAsync(() -> batchInsert(countries));
        }).exceptionally(ex -> {
            throw new RuntimeException("Gagal: " + ex.getMessage(), ex);
        });
    }

    @Transactional
    public List<CountryDto> batchInsert(List<Country> countries) {
        int batchSize = 1000;
        List<CountryDto> result = new ArrayList<>();

        for (int i = 0; i < countries.size(); i += batchSize) {
            List<Country> batch = countries.subList(i, Math.min(i + batchSize, countries.size()));
            countryRepository.saveAll(batch);

            result.addAll(batch.stream()
                    .map(CountryMapper.INSTANCE::toDto)
                    .collect(Collectors.toList()));

            System.out.println("Batch " + (i / batchSize + 1) + " inserted");
        }
        return result;
    }
}

