package com.ardwiinoo.csvapp.utils;

import com.ardwiinoo.csvapp.exceptions.InvariantError;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileUtil {

    public static void hasCSVFormat(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvariantError("File is required");
        }

        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new InvariantError("File must be a CSV");
        }
    }
}