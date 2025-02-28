package com.ardwiinoo.csvapp.models.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CountryRequest {

    private MultipartFile csvFile;
}
