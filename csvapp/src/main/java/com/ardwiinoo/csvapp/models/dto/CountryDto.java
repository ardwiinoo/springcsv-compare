package com.ardwiinoo.csvapp.models.dto;

import lombok.Data;

@Data
public class CountryDto {
    private int id;
    private String code;
    private String countryName;
    private String region;
}
