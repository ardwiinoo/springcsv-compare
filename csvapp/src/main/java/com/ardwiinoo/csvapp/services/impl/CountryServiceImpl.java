package com.ardwiinoo.csvapp.services.impl;

import com.ardwiinoo.csvapp.mappers.CountryMapper;
import com.ardwiinoo.csvapp.models.dto.CountryDto;
import com.ardwiinoo.csvapp.models.dto.request.CountryRequest;
import com.ardwiinoo.csvapp.models.entity.Country;
import com.ardwiinoo.csvapp.repositories.CountryRepository;
import com.ardwiinoo.csvapp.services.CountryService;
import com.ardwiinoo.csvapp.utils.FileUtil;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<CountryDto> upload(CountryRequest request) {
        FileUtil.hasCSVFormat(request.getCsvFile());

        List<Country> countries = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(request.getCsvFile().getInputStream()))) {
            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                Country country = new Country();

                country.setCode(record[0]);
                country.setCountryName(record[1]);
                country.setRegion(record[2]);

                countries.add(country);
            }

            countryRepository.saveAll(countries);
        } catch (Exception e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

        return CountryMapper.INSTANCE.toDtoList(countries);
    }
}
