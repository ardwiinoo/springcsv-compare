package com.ardwiinoo.csvapp.services;

import com.ardwiinoo.csvapp.models.dto.CountryDto;
import com.ardwiinoo.csvapp.models.dto.request.CountryRequest;

import java.util.List;

public interface CountryService {
    List<CountryDto> upload(CountryRequest request);
}
