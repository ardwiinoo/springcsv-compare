package com.ardwiinoo.csvappfuture.repositories;

import com.ardwiinoo.csvappfuture.models.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
