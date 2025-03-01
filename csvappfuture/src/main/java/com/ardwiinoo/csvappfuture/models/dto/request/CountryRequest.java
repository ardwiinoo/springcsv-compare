package com.ardwiinoo.csvappfuture.models.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CountryRequest {

    private MultipartFile csvFile;
}
