package com.ardwiinoo.csvappfuture.mappers;

import com.ardwiinoo.csvappfuture.models.dto.CountryDto;
import com.ardwiinoo.csvappfuture.models.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDto toDto(Country country);
    Country toEntity(CountryDto countryDto);

    List<CountryDto> toDtoList(List<Country> countries);
    List<Country> toEntityList(List<CountryDto> countryDtos);
}