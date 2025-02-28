package com.ardwiinoo.csvapp.controllers;

import com.ardwiinoo.csvapp.models.dto.CountryDto;
import com.ardwiinoo.csvapp.models.dto.request.CountryRequest;
import com.ardwiinoo.csvapp.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping(
            path = "/upload",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Map<String, Object>> uploadCountryCsv(
            @ModelAttribute CountryRequest countryRequest
    ) {
        List<CountryDto> countries = countryService.upload(countryRequest);

        Map<String, Object> response = new HashMap<>();

        response.put("status", "success");
        response.put("data", countries);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
