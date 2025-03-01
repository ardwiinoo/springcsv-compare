package com.ardwiinoo.csvappfuture.services;


import com.ardwiinoo.csvappfuture.models.dto.CountryDto;
import com.ardwiinoo.csvappfuture.models.dto.request.CountryRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CountryService {
    CompletableFuture<List<CountryDto>> upload(CountryRequest request);
}
