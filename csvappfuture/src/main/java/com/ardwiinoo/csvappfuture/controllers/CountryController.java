package com.ardwiinoo.csvappfuture.controllers;

import com.ardwiinoo.csvappfuture.models.dto.request.CountryRequest;
import com.ardwiinoo.csvappfuture.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/countries")
@EnableAsync
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping(
            path = "/upload",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public CompletableFuture<ResponseEntity<Map<String, Object>>> uploadCountryCsv(
            @ModelAttribute CountryRequest countryRequest
    ) {

        System.out.println("RUNNN");

        return countryService.upload(countryRequest)
                .thenApply(countries -> {

                    Map<String, Object> response = new HashMap<>();

                    response.put("status", "success");
                    response.put("data", countries);

                    return ResponseEntity.ok(response);
                });
    }
}
